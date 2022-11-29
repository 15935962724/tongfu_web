package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.*;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.AdminService;
import com.tongfu.service.ProductCategoryService;
import com.tongfu.service.RoleService;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller("webcategory")
@RequestMapping("/web/category")
public class ProductCategoryController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	AdminRoleService adminRoleService;

	@Autowired
	ProductCategoryService categoryService;

	@Value("${category-upload-img}")
	private String category_upload_img;




	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,Long parent) {
		Map<String,Object> map = new HashMap<>(	);
//		map.put("parent",0);
		map.put("type",1);
		Page<ProductCategory> page  = PageHelper.startPage(pageNum,pageSize);
		List<ProductCategory> categoryList = (List<ProductCategory>) categoryService.getAll(map);
		PageInfo<ProductCategory> pageInfo = new PageInfo<ProductCategory>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(categoryList);
		model.addAttribute("page",pageInfo);
		model.addAttribute("parent",parent==null?pageInfo.getList().get(0).getId():parent);
		return "category/list";
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
		return "category/add";
	}


	/**
	 * @param model
	 * @param category
	 * @param parent
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,ProductCategory category,Long parent,@RequestParam("categoryImg") MultipartFile file) {

		String categoryImage = FileUtils.upload(file,category_upload_img);
		System.out.println("categoryImage图地址:"+categoryImage);
        category.setImage(categoryImage);
		category.setModifyDate(new Date());
        category.setCreateDate(new Date());
        category.setIsDeleted(false);
		category.setParent(parent);
		category.setGrade(0);
		category.setTreePath(",");
		Integer conunt = categoryService.insertSelective(category);

		logger.info("添加管理员结果:"+conunt);
		if (parent==null){
			return "redirect:list";
		}
		return "redirect:list?parent="+parent;
	}

	/**
	 * 修改分类
	 * @param model
	 * @param category
	 * @param file
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,ProductCategory category,@RequestParam("categoryImg") MultipartFile file) {
		logger.info(String.valueOf(file.getSize()));
		if (file.getSize()>0){

			String categoryImage = FileUtils.upload(file,category_upload_img);
			System.out.println("categoryImage图地址:"+categoryImage);
			System.out.println("categoryImage图地址:"+categoryImage);
			category.setImage(categoryImage);
		}
		Integer conunt = categoryService.updateByPrimaryKeySelective(category);

		Long parent = categoryService.selectByPrimaryKey(category.getId()).getParent();

		logger.info("修改分类结果:"+conunt);
		return "redirect:list?parent="+parent;
	}

	/**
	 * 查询子集
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryCategory")
	@ResponseBody
	public Object queryCategory(Long id) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("parent",id);
		List<ProductCategory> categoryList = (List<ProductCategory>) categoryService.getAll(map);
		return categoryList;
	}


//	删除分类
	@RequestMapping("/deleteCategory")
	@ResponseBody
	public Object deleteCategory(Long id) {
		Integer deleteCategotyCount = categoryService.deleteByPrimaryKey(id);
		if (deleteCategotyCount>0){
         return  ResultUtil.success();
        }
		return ResultUtil.error("删除失败");
	}



	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCategory")
	@ResponseBody
	public Object getCategory(Long id) {
		ProductCategory category =  categoryService.selectByPrimaryKey(id);
		return category;
	}


}
