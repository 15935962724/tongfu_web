package com.tongfu.controller.admin;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MysqlDeallocatePrepareStatement;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.*;
import com.tongfu.exception.UserNotExistException;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.RoleService;
import com.tongfu.shiro.MyShiroRealm;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.env.NamedObjectEnvironment;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/admin/admin")
public class AdminController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	AdminRoleService adminRoleService;

	@Autowired
	CompanyService companyService;

	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUsername(@RequestParam String username) {
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
	 * 管理员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,String username,String name) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("username",username);
		map.put("name",name);
		map.put("type",0);
		Page<Admin> page  = PageHelper.startPage(pageNum,pageSize);
		List<Admin> all = (List<Admin>) adminService.getAll(map);
		PageInfo<Admin> pageInfo = new PageInfo<Admin>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("username",username);
		model.addAttribute("name",name);
		return "admin/admin/list";
	}

	/**
	 * 供应商管理员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/companyAdminList")
	public String companyAdminList(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,String username,String name) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("username",username);
		map.put("name",name);
		map.put("type",1);
		map.put("isSystem",true);
		Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map> all = adminService.getCompanyAdminAll(map);
		PageInfo<Map> pageInfo = new PageInfo<Map>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("username",username);
		model.addAttribute("name",name);
		return "admin/admin/companyAdminList";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Collection<Role> roles = roleService.getAll(null);
		model.addAttribute("roles",roles);
		return "admin/admin/add";
	}


	/**
	 * 编辑
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id ) {
		Admin admin = adminService.selectByPrimaryKey(id);
		if (admin.getPassword()!=null&&!admin.getPassword().equals("")) {
			String encodedPassword = ShiroConfig.shiroEncryption(admin.getPassword(),admin.getUsername());
			System.out.println(admin.getUsername()+"的密码是："+encodedPassword);
			admin.setPassword(encodedPassword);
		}

		Collection<Role> roles = roleService.getAll(null);
		Map query_map = new HashMap();
		query_map.put("adminId",id);
		List adminRoles = adminRoleService.getAll(query_map);
		if (adminRoles.size()>0){
			AdminRole adminRole = (AdminRole) adminRoles.get(0);
			model.addAttribute("adminRole",adminRole);
		}
		model.addAttribute("roles",roles);
		model.addAttribute("admin",admin);
		return "admin/admin/edit";
	}



	/**
	 * 添加管理员
	 * @param model
	 * @param admin
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Admin admin,Long roleId) {
		admin.setModifyDate(new Date());
		admin.setCreateDate(new Date());
		admin.setIsEnabled(true);
		admin.setIsLocked(false);
		admin.setIsDeleted(false);
		admin.setIsSystem(false);
		admin.setType(1l);
		String encodedPassword = ShiroConfig.shiroEncryption("111111",admin.getUsername());
		System.out.println(admin.getUsername()+"的密码是："+encodedPassword);
		admin.setPassword(encodedPassword);
		admin.setLoginFailureCount(0);
		Integer conunt = adminService.insertSelective(admin);
		AdminRole adminRole = new AdminRole();
		adminRole.setAdmins(admin.getId());
		adminRole.setRoles(roleId);
		adminRoleService.insertSelective(adminRole);
		return "redirect:list?type="+0;
	}

	@PostMapping("/update")
	public String update(Model model,Admin admin,Long roleId) {
		admin.setModifyDate(new Date());
		admin.setLoginFailureCount(0);
		String encodedPassword = ShiroConfig.shiroEncryption(admin.getPassword(),admin.getUsername());
		System.out.println("密码："+encodedPassword);
		admin.setPassword(encodedPassword);
		Integer conunt = adminService.updateByPrimaryKeySelective(admin);
		adminRoleService.deleteByPrimaryAdminId(admin.getId());
		if (roleId!=null){
			adminRoleService.deleteByPrimaryAdminId(admin.getId());
			AdminRole adminRole = new AdminRole();
			adminRole.setAdmins(admin.getId());
			adminRole.setRoles(roleId);
			adminRoleService.insertSelective(adminRole);
		}
		logger.info("添加管理员结果:"+conunt);
		admin = adminService.selectByPrimaryKey(admin.getId());
		if (admin.getCompanyId()!=null){
			return "redirect:companyAdminList";
		}
		return "redirect:list";
	}

	//删除角色
	@PostMapping("/delete")
	@ResponseBody
	public Object updateProduct(@RequestBody String params) {
		System.out.println("》》》"+params);
		Admin admin = JSONObject.parseObject(params,Admin.class);
		Integer deleteAdmin = adminService.deleteByPrimaryKey(admin.getId());
		if (deleteAdmin>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}


	//启用or禁用
	@PostMapping("/updateAdmin")
	@ResponseBody
	public Object updateAdmin(@RequestBody String params) {
		System.out.println("》》》"+params);
		Admin admin = JSONObject.parseObject(params,Admin.class);
		Integer updateAdmin =	adminService.updateByPrimaryKeySelective(admin);
		if (updateAdmin>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}







}
