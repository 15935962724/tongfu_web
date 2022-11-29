package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/admin/companyApply")
public class CompanyApplyController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CompanyApplyService companyApplyService;

	/**
	 * 入驻申请
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String cname,String adminName,String adminEmail,Long status,String mobile) {

		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<CompanyApply> all = companyApplyService.getAll(null);
		PageInfo<CompanyApply> pageInfo = new PageInfo<CompanyApply>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("cname",cname);
		model.addAttribute("adminName",adminName);
		model.addAttribute("adminEmail",adminEmail);
		model.addAttribute("status",status);
		model.addAttribute("mobile",mobile);
		return "admin/companyApply/list";
	}




}
