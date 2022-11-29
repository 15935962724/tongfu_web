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
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/demonsStatistics")
public class DemonsStatisticsController {

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



	@RequestMapping("/statistics")
	public String list(Model model) {
		Map query_map = new HashMap();
		query_map.put("dtype",0);
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
		query_map.put("dtype",0);
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
		return "admin/demons/statistics";
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
		query_map.put("dtype",0);
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
		query_map.put("dtype",0);
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
