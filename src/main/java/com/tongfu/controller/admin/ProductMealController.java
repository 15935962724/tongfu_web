package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Product;
import com.tongfu.entity.ProductMeal;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/productMeal")
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
					   String name,Boolean isMarketable,Long status,String startDate,String endDate,Long companyId) {
		Map<String,Object> map = new HashMap<>();
		Admin admin = adminService.getCurrent();
//		Subject subject = SecurityUtils.getSubject();
		map.put("companyId",companyId);
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
		model.addAttribute("companyId",companyId);
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);

		return "admin/productMeal/list";
	}



	/**
	 * 编辑商品
	 * @param model
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,ProductMeal productMeal){
		productMealService.updateByPrimaryKeySelective(productMeal);
		return "redirect:list";
	}


	//	修改商品状态
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


	/**
	 * 审核操作
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updateProductMealStatus")
	@ResponseBody
	public String updateProductMealStatus(@RequestBody String jsonObject) {
		ProductMeal productMeal = JSON.parseObject(jsonObject, ProductMeal.class);
		Integer count = productMealService.updateByPrimaryKeySelective(productMeal);
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}




}
