package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.dao.AdminDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.AdminRole;
import com.tongfu.entity.Product;
import com.tongfu.entity.Role;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.AdminService;
import com.tongfu.service.RoleAuthorityService;
import com.tongfu.service.RoleService;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/role")
public class RoleController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	RoleAuthorityService roleAuthorityService;

	@Autowired
	AdminRoleService adminRoleService;

	/**
     * 会员列表
	 * @param model
     * @return
     */
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, String name) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		Page<Role> page = PageHelper.startPage(pageNum, pageSize);
		List<Role> all = (List<Role>) roleService.getAll(map);
		PageInfo<Role> pageInfo = new PageInfo<Role>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page", pageInfo);
		model.addAttribute("name", name);
		return "admin/role/list";

	}


	/**
	 * 添加角色页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		return "admin/role/add";
	}

	@RequestMapping("/edit")
	public String add(Model model ,Long id) {
		Map<String, Object> query_map = new HashMap<>();
		query_map.put("role",id);
		List<String> roleAuthoritys = roleAuthorityService.getAuthorities(query_map);
		Role role = roleService.selectByPrimaryKey(id);
		model.addAttribute("roleAuthoritys", roleAuthoritys);
		model.addAttribute("role", role);
		return "admin/role/edit";
	}

	/**
	 * 添加角色权限
	 * @param model
	 * @param role
	 * @param authorities
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Role role,String[]authorities) {
		role.setModifyDate(new Date());
		role.setCreateDate(new Date());
		role.setIsDeleted(false);
		role.setIsSystem(false);
		Integer count = roleService.insertSelective(role);
		if (count>0){
			System.out.println(role.getId());
		}

		Map<String, Object> map = new HashMap<>();
		map.put("role", role.getId());
		map.put("authorities", authorities);
		if (authorities.length>0){
			Integer roleAuthorityCount = roleAuthorityService.insertMap(map);
		}

		return "redirect:list";
	}

	@PostMapping("/update")
	public String update(Model model,Role role,String[]authorities) {

		role.setCreateDate(new Date());
		Integer count = roleService.updateByPrimaryKeySelective(role,authorities);




		return "redirect:list";
	}


	//删除角色
	@PostMapping("/delete")
	@ResponseBody
	public Object updateProduct(@RequestBody String params) {



		System.out.println("》》》"+params);
		Map<String, Object> query_map = new HashMap<>();
		Role role = JSONObject.parseObject(params,Role.class);
		query_map.put("role",role.getId());
		List<AdminRole> adminRoles = adminRoleService.getAll(query_map);
		if (adminRoles.size()>0){
			return ResultUtil.error("该角色有管理员正在使用");
		}
		roleService.deleteByPrimaryKey(role.getId());
		return  ResultUtil.success("操作成功");

	}



}
