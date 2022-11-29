package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller("webspecifications")
@RequestMapping("/web/specifications")
public class SpecificationsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private SpecificationsService specificationsService;

	@Autowired
	private ProductService productService;


	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,Long productId) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("productId",productId);
		Page<Specifications> page  = PageHelper.startPage(pageNum,pageSize);
		List<Specifications> all = specificationsService.getAll(map);
		PageInfo<Specifications> pageInfo = new PageInfo<Specifications>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("productId",productId);
		return "web/specifications/list";
	}


	/**
	 * 添加规格
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model,Long productId) {
//		Collection<Role> roles = roleService.getAll(null);
		model.addAttribute("productId",productId);
		return "web/specifications/add";
	}

	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
//		Collection<Role> roles = roleService.getAll(null);
		Specifications specifications = specificationsService.selectByPrimaryKey(id);
		model.addAttribute("specifications",specifications);
		return "web/specifications/edit";
	}

	/**
	 * 添加规格
	 * @param model
	 * @param specifications
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Specifications specifications) {
		Integer conunt = specificationsService.insertSelective(specifications);
		Product product  =  productService.selectByPrimaryKey(specifications.getProduct());
		product.setStatus(1l);
		productService.updateByPrimaryKeySelective(product);
		logger.info("添加结果:"+conunt);
		return "redirect:list?productId="+specifications.getProduct();
	}

	@PostMapping("/update")
	public String update(Model model,Specifications specifications ,String content) {
		Integer conunt = specificationsService.updateByPrimaryKeySelective(specifications);
		Product product  =  productService.selectByPrimaryKey(specifications.getProduct());
		product.setStatus(1l);
		productService.updateByPrimaryKeySelective(product);
		logger.info("结果:"+conunt);
		return "redirect:list?productId="+specifications.getProduct();
	}

	@RequestMapping("/delete")
	public String delete(Model model,Long id,Long product) {
//		Collection<Role> roles = roleService.getAll(null);
		Integer deleteByPrimaryKey = specificationsService.deleteByPrimaryKey(id);
//		model.addAttribute("specifications",specifications);
		return "redirect:list?productId="+product;
	}


}
