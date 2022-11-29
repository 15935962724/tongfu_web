package com.tongfu.controller.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ExcelUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Controller("webproductMeal")
@RequestMapping("/web/productMeal")
public class ProductMealController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductMealService productMealService;

	@Autowired
	private ProductPurchaseService productPurchaseService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductImgService productImgService;

	@Autowired
	private SpecificationsService specificationsService;

	@Autowired
	private ProductVerifyService productVerifyService;

	@Autowired
	private PlatformTransactionManager txManager;

	@Autowired
	private ProductStatisticsService productStatisticsService;

	@Autowired
	private ProductActivationCodeService productActivationCodeService;


	/**
	 * 产品列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String name,Boolean isMarketable,Long status,String startDate,String endDate) {
		Map<String,Object> map = new HashMap<>();
		Admin admin = adminService.getCurrent();
//		Subject subject = SecurityUtils.getSubject();
		map.put("companyId",admin.getCompanyId());
		map.put("name",name);
		map.put("isMarketable",isMarketable);
		map.put("status",status);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		Page<Map<String, Object>> page  = PageHelper.startPage(pageNum,pageSize);

		List<Map<String, Object>> all = productMealService.getAll(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("name",name);
		model.addAttribute("status",status);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("isMarketable",isMarketable);
		return "web/productMeal/list";
	}



	/**
	 * 添加商品
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		product_query_map.put("companyid",admin.getCompanyId());
		product_query_map.put("status",2);
//		product_query_map.put("isTrainingPackage",false);
		product_query_map.put("isAddService",1);
		List<Product> mainProducts = productService.getAll(product_query_map);//主产品
		product_query_map.put("isAddService",3);
		List<Product> trainingPackageProducts = productService.getAll(product_query_map);//培训包产品

		model.addAttribute("mainProducts",mainProducts);
		model.addAttribute("trainingPackageProducts",trainingPackageProducts);
		return "web/productMeal/add";
	}


	/**
	 * 添加套餐
	 * @param model
	 * @param productMeal
	 * @return
	 */
	@PostMapping(value = "/save")
		public String save(Model model,ProductMeal productMeal){

		Admin admin = adminService.getCurrent();
		productMeal.setCompanyId(admin.getCompanyId());
		productMeal.setCreateDate(new Date());
		productMeal.setModifyDate(new Date());
		productMeal.setIsDeleted(false);
		productMeal.setIsMarketable(true);
		productMeal.setSales(0L);
		productMeal.setStatus(1L);
		productMealService.insertSelective(productMeal);
		return "redirect:list";
	}

	/**
	 * 编辑商品
	 * @param model
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,ProductMeal productMeal){
		productMeal.setStatus(1l);
		productMealService.updateByPrimaryKeySelective(productMeal);
		return "redirect:list";
	}

	/**
	 * 编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {

		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		product_query_map.put("companyid",admin.getCompanyId());
		product_query_map.put("status",2);
		product_query_map.put("isTrainingPackage",false);

		List<Product> mainProducts = productService.getAll(product_query_map);//主产品
		product_query_map.put("isTrainingPackage",true);
		List<Product> trainingPackageProducts = productService.getAll(product_query_map);//培训包产品

		ProductMeal productMeal = productMealService.selectByPrimaryKey(id);
		model.addAttribute("mainProducts",mainProducts);
		model.addAttribute("trainingPackageProducts",trainingPackageProducts);
		model.addAttribute("productMeal",productMeal);

		return "web/productMeal/edit";
	}


	/**
	 * 删除
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody JSONObject jsonObject) {
		int deleteByPrimaryKey = productMealService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("删除失败失败");
	}

	//	修改培训包状态
	@PostMapping("/updateProductMeal")
	@ResponseBody
	public Object updateProductMeal(@RequestBody String params) {

		ProductMeal productMeal = JSONObject.parseObject(params,ProductMeal.class);

		Integer updateProductMeal = productMealService.updateByPrimaryKeySelective(productMeal);
		if (updateProductMeal>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}

	// 套餐商品所选价格
	@RequestMapping("/productPurchases")
	@ResponseBody
	public Object productPurchases(String productId) {
		Map<String,Object> map = new HashMap<>();
		map.put("productId",productId);
		List<Map<String,Object>> productPurchases = productPurchaseService.getPurchases(map);
		return productPurchases;
	}




}
