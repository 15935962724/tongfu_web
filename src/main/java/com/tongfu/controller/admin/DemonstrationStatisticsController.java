package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.EmailSend;
import com.tongfu.entity.Admin;
import com.tongfu.entity.DemonstrationStatistics;
import com.tongfu.entity.Product;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.DemonstrationStatisticsService;
import com.tongfu.service.ProductService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/demonstrationStatistics")
public class DemonstrationStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private DemonstrationStatisticsService demonstrationStatisticsService;

	@Autowired
	private ProductService productService;

	//#邮箱账号
	@Value("${emailUserName}")
	private String emailUserName;

	//#发送者密码
	@Value("${emailPassword}")
	private String emailPassword;

	//#邮箱服务器地址
	@Value("${emailHost}")
	private String emailHost;

	//#主机端口
	@Value("${emailPort}")
	private String emailPort;

	//#发送者邮箱
	@Value("${emailUserName}")
	private String emailFromAddress;
	/**
	 * 附件上传路径
	 */
	@Value("${file-web-path}")
	private String fileWebPath;
	/**
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @param status
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "8")Integer pageSize,Integer type,Long status) {
		Map<String,Object> map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		Subject subject = SecurityUtils.getSubject();
//		map.put("adminid",admin.getId());
		if (!subject.hasRole("超级管理员")){
			map.put("companyid",companyService.getCompanyId());
		}
		map.put("type",type);
		map.put("status",status);
		Page<Map<String, Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String, Object>> all = demonstrationStatisticsService.getAll(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("content",type==0?"申请演示":"申请试用");
		model.addAttribute("type",type);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("status",status);
		return "admin/demonstration/list";
	}


	//	修改商品状态
	@PostMapping("/updateDemonstration")
	@ResponseBody
	public Object updateProduct(String params,Long id) {
		Admin admin = adminService.getCurrent();

		DemonstrationStatistics demonstrationStatistics = demonstrationStatisticsService.selectByPrimaryKey(id);
		Product product = productService.selectByPrimaryKey(demonstrationStatistics.getProduct());

		EmailEntity email = new EmailEntity();
		email.setUserName(emailUserName);
		email.setPassword(emailPassword);
		email.setHost(emailHost);
		email.setPort(Integer.valueOf(emailPort));
		email.setFromAddress(emailFromAddress);
		email.setToAddress(demonstrationStatistics.getEmail());
		Long type = demonstrationStatistics.getType();
		String subject = "";

		if (type==0){
			subject = "<"+product.getName()+">申请演示反馈结果";
//			申请演示待处理
		}
		if (type==1){
			subject = "<"+product.getName()+">申请试用反馈结果";
			if (product.getOntrialPackage()!=null&&!product.getOntrialPackage().equals("")){
				email.setContext(params+"\n以下为试用包请自行下载试用");
				email.attachFile(fileWebPath+product.getOntrialPackage()); // 往邮件中添加附件
			}else {
				email.setContext(params+"\n请前往 "+product.getOntrialPackageUrl()+" 去下载试用包");
			}
		}
		email.setSubject(subject);
		email.setContextType("text/html;charset=utf-8");
//        email.attachFile("往邮件中添加附件");
		boolean flag = EmailSend.EmailSendTest(email);
		System.err.println("邮件发送结果=="+flag);

		if (flag){
			demonstrationStatistics.setStatus(2l);
			demonstrationStatistics.setAdminId(admin.getId());
			Integer updateDemonstrationCount = demonstrationStatisticsService.updateByPrimaryKeySelective(demonstrationStatistics);
			if (updateDemonstrationCount>0){
				return  ResultUtil.success("处理成功");
			}else {
				return ResultUtil.error("处理失败");
			}

		}
		return ResultUtil.error("处理失败");
	}



	@RequestMapping("/statistics")
	public String list(Model model) {
		Map query_map = new HashMap();
		query_map.put("dtype",1);
		List<Map> years = demonstrationStatisticsService.getYears(query_map);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < years.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			if (i==0){
				jsonObject.put("name",years.get(i).get("year"));
				jsonObject.put("type","column");
				jsonObject.put("data",demonstrationStatisticsService.thatYear(years.get(i)));
			}else {
				jsonObject.put("name",years.get(i).get("year"));
				jsonObject.put("type",i%2==0?"line":"area");
				jsonObject.put("data",demonstrationStatisticsService.thatYear(years.get(i)));
			}
			jsonArray.add(jsonObject);
		}
		System.out.println(jsonArray.toString());

		model.addAttribute("years",years);
		model.addAttribute("series",jsonArray);

//		{name: "Product A", data: [44, 55, 41, 67, 22, 43, 21, 49]},
//		{name: "Product B",data: [13, 23, 20, 8, 13, 27, 33, 12]},
//		{name: "Product C", data: [11, 17, 15, 15, 21, 14, 15, 13]}
		query_map.put("dtype",1);
		query_map.put("type",0);
		List<Map> productNames = demonstrationStatisticsService.getProductName(query_map);
		JSONArray productNameJsonArray = new JSONArray();
		for (Map map : productNames) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",map.get("name"));
			jsonObject.put("data",demonstrationStatisticsService.getProductNameApplyTrial(map));
			productNameJsonArray.add(jsonObject);
		}
		model.addAttribute("productNameJsonArray",productNameJsonArray);
		model.addAttribute("productNames",productNames);

		List<Map> provinces = demonstrationStatisticsService.getProvince(query_map);

//		List<Map> provinces = memberService.getProvince(null);
		JSONArray provincesJsonArray = new JSONArray();
		for (Map map : provinces) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",map.get("name"));
			jsonObject.put("data",demonstrationStatisticsService.getProvinceApplyTrial(map));
			provincesJsonArray.add(jsonObject);
		}
		model.addAttribute("provincesJsonArray",provincesJsonArray);
		model.addAttribute("provinces",provinces);
		return "admin/demonstration/statistics";
	}


	/**
	 * 根据产品分组申请试用 自定义查询
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/productNames", method = RequestMethod.POST)
	@ResponseBody
	public String productNames(Long type ,String startDate ,String endDate){
		Map query_map = new HashMap();
		query_map.put("dtype",1);
		query_map.put("type",type);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}

		List<Map> productNames = demonstrationStatisticsService.getProductName(query_map);
		return ResultUtil.success(productNames);
	}

	/**
	 * 申请试用地区分布
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/getProvince", method = RequestMethod.POST)
	@ResponseBody
	public String getProvince(Long type ,String startDate ,String endDate){
		Map query_map = new HashMap();
		query_map.put("dtype",1);
		query_map.put("type",type);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}

		List<Map> provinces = demonstrationStatisticsService.getProvince(query_map);
		return ResultUtil.success(provinces);
	}


}
