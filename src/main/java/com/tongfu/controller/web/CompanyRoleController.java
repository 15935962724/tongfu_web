package com.tongfu.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.dao.AdminDao;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller("webrole")
@RequestMapping("/web/company_role")
public class CompanyRoleController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyRoleService companyRoleService;

	@Autowired
	private AdminCompanyRoleService adminCompanyRoleService;

	@Autowired
	private CompanyRoleAuthorityService companyRoleAuthorityService;

	@Autowired
	private PlatformTransactionManager txManager;


	/**
     * 会员列表
	 * @param model
     * @return
     */
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, String name) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("companyId", companyService.getCompanyId());
		Page<CompanyRole> page = PageHelper.startPage(pageNum, pageSize);
//		List<Map<String,Object>> all = (List<Map<String,Object>>) companyService.getAll(map);
		List<CompanyRole> all = companyRoleService.getAll(map);
		PageInfo<CompanyRole> pageInfo = new PageInfo<CompanyRole>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page", pageInfo);
		model.addAttribute("name", name);
		return "web/role/list";

	}


	/**
	 * 添加角色页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map query_map = new HashMap();
		query_map.put("is_system",true);
		List<CompanyRole> all = companyRoleService.getAll(query_map);
		for (CompanyRole companyRole : all) {
			CompanyRoleAuthority companyRoleAuthority = new CompanyRoleAuthority();
			companyRoleAuthority.setCompanyRole(companyRole.getId());
			companyRoleAuthority.setAuthorities("agent");
			companyRoleAuthorityService.insert(companyRoleAuthority);
		}
		return "web/role/add";
	}

	/**
	 * 编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String add(Model model,Long id) {
		CompanyRole companyRole = companyRoleService.selectByPrimaryKey(id);
		Map query_map = new HashMap();
		query_map.put("companyRoleId",companyRole.getId());
		List<String> companyAuthorities = companyRoleAuthorityService.getCompanyAuthorities(query_map);
		model.addAttribute("companyRole", companyRole);
		model.addAttribute("companyAuthorities", companyAuthorities);
		return "web/role/edit";
	}


	/**
	 * 添加角色权限
	 * @param
	 * @param authorities
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,CompanyRole companyRole,String[]authorities) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			companyRole.setModifyDate(new Date());
			companyRole.setCreateDate(new Date());
			companyRole.setIsSystem(false);
			companyRole.setCompanyId(companyService.getCompanyId());
			Integer count = companyRoleService.insertSelective(companyRole);
			Map<String, Object> map = new HashMap<>();
			map.put("companyRoleId", companyRole.getId());
			map.put("companyRoleAuthoritys", authorities);
			if (authorities.length>0){
				int insertMap = companyRoleAuthorityService.insertMap(map);
			}
			txManager.commit(status);
		}catch (Exception e){
			System.out.println("事务回滚了");
			//强制手动事务回滚
			txManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}



		return "redirect:list";
	}


	/**
	 * 删除供应商角色
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody JSONObject jsonObject ){
		Map query_map = new HashMap();
		query_map.put("companyRoleId",jsonObject.getLong("id"));
		List<AdminCompanyRole> adminCompanyRoles = adminCompanyRoleService.getAll(query_map);
		if (adminCompanyRoles.size()>0){
			return  ResultUtil.error("该角色有用户使用不可删除!");
		}
		Integer deleteByPrimaryKey = companyRoleService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}

}
