package com.tongfu.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.dao.AgentProductMapper;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller("webagent")
@RequestMapping("/web/agent")
public class AgentController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentProductService agentProductService;

	@Autowired
	private PlatformTransactionManager txManager;

	@Autowired
	private ProductService productService;

	@Autowired
	private CompanyService companyService;


	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   String companyName,String name,String mobile) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("mobile",mobile);
		map.put("companyName",companyName);
		map.put("name",name);
		map.put("companyId",companyService.getCompanyId());
		Page<Agent> page  = PageHelper.startPage(pageNum,pageSize);
		List<Agent> all =  agentService.getList(map);
		PageInfo<Agent> pageInfo = new PageInfo<Agent>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("companyName",companyName);
		model.addAttribute("mobile",mobile);
		model.addAttribute("name",name);
		return "web/agent/list";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map query_map = new HashMap<>();
		query_map.put("companyid",companyService.getCompanyId());
		List<Product> productList = productService.getAll(query_map);
		model.addAttribute("productList",productList);
		return "web/agent/add";
	}


	/**
	 * 添加代理商
	 * @param model
	 * @param agent
	 * @param productId
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Agent agent,Long []productId) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			Admin adminCurrent = adminService.getCurrent();
			agent.setCreateDate(new Date());
			agent.setModifyDate(new Date());
			agent.setCompanyId(adminCurrent.getCompanyId());
			agentService.insertSelective(agent);
			Map map = new HashMap();
			map.put("agentId",agent.getId());
			map.put("productIds",productId);
			agentProductService.insertMap(map);
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
	 * 编辑
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id ) {
		Admin admin = adminService.selectByPrimaryKey(id);
		Map query_map = new HashMap<>();
		query_map.put("companyid",companyService.getCompanyId());
		List<Product> productList = productService.getAll(query_map);
		model.addAttribute("productList",productList);
		return "web/agent/edit";
	}



	@PostMapping("/update")
	public String update(Model model,Admin admin,Long companyRoleId) {
//		admin.setModifyDate(new Date());
//		admin.setLoginFailureCount(0);
//		if (admin.getPassword()!=null&&!admin.getPassword().equals("")){
//			String encodedPassword = ShiroConfig.shiroEncryption(admin.getPassword(),admin.getUsername());
//			System.out.println("密码："+encodedPassword);
//			admin.setPassword(encodedPassword);
//		}
//		Integer conunt = adminService.updateByPrimaryKeySelective(admin);
//
//		if (companyRoleId!=null){
//			adminCompanyRoleService.deleteByPrimaryAdminId(admin.getId());
//			AdminCompanyRole adminCompanyRole = new AdminCompanyRole();
//			adminCompanyRole.setAdmins(admin.getId());
//			adminCompanyRole.setCompanyRole(companyRoleId);
//			adminCompanyRoleService.insertSelective(adminCompanyRole);
//		}
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

//		adminCompanyRoleService.deleteByPrimaryAdminId(jsonObject.getLong("id"));
//		int deleteByPrimaryKey = adminService.deleteByPrimaryKey(jsonObject.getLong("id"));
//		if (deleteByPrimaryKey>0){
//			return  ResultUtil.success();
//		}
		return  ResultUtil.error("删除失败");
	}




}
