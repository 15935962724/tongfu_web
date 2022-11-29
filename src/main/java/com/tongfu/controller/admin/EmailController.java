package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.EmailSend;
import com.tongfu.email.EmailUtil;
import com.tongfu.entity.*;
import com.tongfu.service.AdminService;
import com.tongfu.service.MemberPointWorkshopService;
import com.tongfu.service.MemberService;
import com.tongfu.util.ResultUtil;
import com.tongfu.util.SettingUtils;
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
@RequestMapping("/admin/email")
public class EmailController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberPointWorkshopService memberPointWorkshopService;

	@Autowired
	private MemberService memberService;

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
	 * setting 设置
	 * @return
	 */
	@RequestMapping("/add")
	public String edit(Model model) {
		Map<String,Object> map = new HashMap<>(	);
		Setting setting = SettingUtils.get();
		model.addAttribute("setting",setting);
		return "admin/email/add";
	}


	@PostMapping("/send")
	public String save(Model model, String title,String content,String sendEmail) {


		String []emails = sendEmail.split(",");
		for (String email : emails) {
			EmailEntity emailEntity = new EmailEntity();
			emailEntity.setUserName(emailUserName);
			emailEntity.setPassword(emailPassword);
			emailEntity.setHost(emailHost);
			emailEntity.setPort(Integer.valueOf(emailPort));
			emailEntity.setFromAddress(emailFromAddress);
			emailEntity.setToAddress(email);
			emailEntity.setContext(content);
			emailEntity.setSubject(title);
			emailEntity.setContextType("text/html;charset=utf-8");
//        email.attachFile("往邮件中添加附件");
			boolean flag = EmailSend.EmailSendTest(emailEntity);
			System.err.println("邮件发送结果=="+flag);
		}


		return "redirect:add";
	}



	@PostMapping("/queryMember")
	@ResponseBody
	public Object queryMember(@RequestBody String params) {
		System.out.println("》》》"+params);
		Map<String,Object> query_map = JSON.parseObject(params);
		List<Member> members =  memberService.getAll(query_map);
		return ResultUtil.success(members);
	}

}
