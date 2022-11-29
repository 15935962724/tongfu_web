package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.email.*;
import com.tongfu.entity.Member;
import com.tongfu.entity.Message;
import com.tongfu.entity.Product;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.*;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller("webmessage")
@RequestMapping("/web/message")
public class MessageController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private LearningService learningService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

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


	@Value("${learning-logo}")
	private String learningLogo;

	//阿里云发件人账户
	@Value("${aliyunSendEmailAccount}")
	private String aliyunSendEmailAccount;

	//阿里云发件人密码
	@Value("${aliyunSendEmailPassword}")
	private String aliyunSendEmailPassword;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "9")Integer pageSize,
					   Long productCategory,Boolean isHandle,String name) {

		Map<String,Object> query_map = new HashMap<>();
		query_map.put("isHandle",isHandle);
		query_map.put("isForwardReceiver",true);
		query_map.put("productCategory",productCategory);
		query_map.put("companyId",companyService.getCompanyId());
		query_map.put("name",name);
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> all = (List<Map<String,Object>>) messageService.getAll(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		query_map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(query_map);
		model.addAttribute("productCategories",productCategories);//商品分类
		model.addAttribute("name",name);
		model.addAttribute("productCategory",productCategory);
		model.addAttribute("page",pageInfo);
		return "web/message/list";
	}


	@RequestMapping("/replyMessage")
	@ResponseBody
	public Object replyMessage(Long id,String content) {

		Message message = messageService.selectByPrimaryKey(id);
		message.setIsHandle(true);
		Product product  = productService.selectByPrimaryKey(message.getProductId());
		String subject = product.getName()+"咨询反馈";
		Member member = memberService.selectByPrimaryKey(message.getSender());
		MailInfo mailInfo = new MailInfo(aliyunSendEmailAccount,
				aliyunSendEmailPassword,
				message.getEmail(), "surgi-plan",
				member.getName(), subject, content,null);
		boolean flag = MailSendUtils.sendEmail(mailInfo);

		if(flag){
			Integer updateMessage = messageService.updateByPrimaryKeySelective(message);
			return ResultUtil.success(updateMessage>0?"操作成功":"操作失败");
		}
		return ResultUtil.error("操作失败");
	}
}
