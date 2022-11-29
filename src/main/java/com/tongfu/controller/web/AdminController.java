package com.tongfu.controller.web;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MysqlDeallocatePrepareStatement;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.dao.CompanyRoleDao;
import com.tongfu.entity.*;
import com.tongfu.exception.UserNotExistException;
import com.tongfu.service.*;
import com.tongfu.shiro.MyShiroRealm;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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

@Controller("webadmin")
@RequestMapping("/web/admin")
public class AdminController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyRoleService companyRoleService;

	@Autowired
	private AdminCompanyRoleService adminCompanyRoleService;

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
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   String username,String name,String email) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("username",username);
		map.put("name",name);
		map.put("email",email);
		map.put("companyId",companyService.getCompanyId());
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
		model.addAttribute("email",email);
		return "web/admin/list";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyService.getCompanyId());
		List<CompanyRole> roles = companyRoleService.getAll(map);
		model.addAttribute("roles",roles);
		return "web/admin/add";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @param admin
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Admin admin,Long roleId) {
		Admin adminCurrent = adminService.getCurrent();
		admin.setModifyDate(new Date());
		admin.setCreateDate(new Date());
		admin.setIsEnabled(false);
		admin.setIsLocked(false);
		admin.setIsDeleted(false);
		admin.setIsSystem(false);
		admin.setType(1l);
		admin.setCompanyId(adminCurrent.getCompanyId());
		String encodedPassword = ShiroConfig.shiroEncryption("111111",admin.getUsername());
		System.out.println(admin.getUsername()+"的密码是："+encodedPassword);
		admin.setPassword(encodedPassword);
		admin.setLoginFailureCount(0);
		Integer conunt = adminService.insertSelective(admin);
		AdminCompanyRole adminCompanyRole = new AdminCompanyRole();
		adminCompanyRole.setAdmins(admin.getId());
		adminCompanyRole.setCompanyRole(roleId);
		adminCompanyRoleService.insertSelective(adminCompanyRole);
		logger.info("添加管理员结果:"+conunt);
		return "redirect:list";
	}

	/**
	 * 编辑
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id ) {
		Admin admin = adminService.selectByPrimaryKey(id);
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyService.getCompanyId());
		List<CompanyRole> companyRoles = companyRoleService.getAll(map);
		model.addAttribute("companyRoles",companyRoles);//供应商自己的角色
		map.put("adminId",id);
		List<AdminCompanyRole> adminCompanyRoles = adminCompanyRoleService.getAll(map);
		if (adminCompanyRoles.size()>0){
			model.addAttribute("adminCompanyRole",adminCompanyRoles.get(0));
		}
		model.addAttribute("admin",admin);
		return "web/admin/edit";
	}



	@PostMapping("/update")
	public String update(Model model,Admin admin,Long companyRoleId) {
		admin.setModifyDate(new Date());
		admin.setLoginFailureCount(0);
		if (admin.getPassword()!=null&&!admin.getPassword().equals("")){
			String encodedPassword = ShiroConfig.shiroEncryption(admin.getPassword(),admin.getUsername());
			System.out.println("密码："+encodedPassword);
			admin.setPassword(encodedPassword);
		}
		Integer conunt = adminService.updateByPrimaryKeySelective(admin);

		if (companyRoleId!=null){
			adminCompanyRoleService.deleteByPrimaryAdminId(admin.getId());
			AdminCompanyRole adminCompanyRole = new AdminCompanyRole();
			adminCompanyRole.setAdmins(admin.getId());
			adminCompanyRole.setCompanyRole(companyRoleId);
			adminCompanyRoleService.insertSelective(adminCompanyRole);
		}
		logger.info("添加管理员结果:"+conunt);
		return "redirect:list";
	}


	/**
	 * 供应商删除自己的管理员
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody JSONObject jsonObject ){

		adminCompanyRoleService.deleteByPrimaryAdminId(jsonObject.getLong("id"));
		int deleteByPrimaryKey = adminService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}




}
