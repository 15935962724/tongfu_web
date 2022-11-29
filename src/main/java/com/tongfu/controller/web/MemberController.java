package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Member;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("webmember")
@RequestMapping("/web/member")
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberService memberService;

	@Autowired
	CompanyService companyService;

	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}

		if (adminService.selectByUserName(username)==null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,String username,String name,String phone) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("username",username);
		map.put("name",name);
		map.put("phone",phone);
		Page<Member> page  = PageHelper.startPage(pageNum,pageSize);
		List<Member> all = (List<Member>) memberService.getAll(map);
		PageInfo<Member> pageInfo = new PageInfo<Member>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("username",username);
		model.addAttribute("name",name);
		model.addAttribute("phone",phone);
		return "member/list";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
//		Collection<Role> roles = roleService.getAll(null);
//		model.addAttribute("roles",roles);
		return "member/add";
	}



}
