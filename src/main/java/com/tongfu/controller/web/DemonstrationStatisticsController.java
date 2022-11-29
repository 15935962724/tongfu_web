package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.MailInfo;
import com.tongfu.email.MailSendUtils;
import com.tongfu.entity.Admin;
import com.tongfu.entity.DemonstrationStatistics;
import com.tongfu.entity.Product;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.DemonstrationStatisticsService;
import com.tongfu.service.ProductService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("webdemonstrationStatistics")
@RequestMapping("/web/demonstrationStatistics")
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

	//阿里云发件人账户
	@Value("${aliyunSendEmailAccount}")
	private String aliyunSendEmailAccount;

	//阿里云发件人密码
	@Value("${aliyunSendEmailPassword}")
	private String aliyunSendEmailPassword;
	/**
	 * 附件上传路径
	 */
	@Value("${file-web-path}")
	private String fileWebPath;
	/**
	 * 会员列表
	 * @param model
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
		return "web/demonstration/list";
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
		String content = "";//邮件内容
		if (type==0){
			subject = "<"+product.getName()+">申请演示反馈结果";
			content = params;
//			申请演示待处理
		}
		String file = null;
		if (type==1){
			subject = "<"+product.getName()+">申请试用反馈结果";
			if (product.getOntrialPackage()!=null&&!product.getOntrialPackage().equals("")){
				content = params+"\n以下为试用包请自行下载试用";
				email.setContext(content);
				file = fileWebPath+product.getOntrialPackage();
				email.attachFile(fileWebPath+product.getOntrialPackage()); // 往邮件中添加附件
			}else {
				content = params+"\n请前往 "+product.getOntrialPackageUrl()+" 去下载试用包";
				email.setContext(content);
			}
		}
		email.setSubject(subject);
		email.setContextType("text/html;charset=utf-8");
//        email.attachFile("往邮件中添加附件");

		MailInfo mailInfo = new MailInfo(aliyunSendEmailAccount,
				aliyunSendEmailPassword,
				demonstrationStatistics.getEmail(), "surgi-plan",
				demonstrationStatistics.getName(), subject, content,file);
		boolean flag = MailSendUtils.sendEmail(mailInfo);

//		213231232321211231223123112312312312
//		boolean flag = EmailSend.EmailSendTest(email);
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








}
