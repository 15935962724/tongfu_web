package com.tongfu.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.entity.Company;
import com.tongfu.entity.Product;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller("weborderSalesStatistics")
@RequestMapping("/web/orderSalesStatistics")
public class OrderSalesStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductStatisticsService productStatisticsService;

	@Autowired
	private DemonstrationStatisticsService demonstrationStatisticsService;

//	@RequestMapping("/statistics")
//	public String statistics(Model model) {
//
//		Company company = companyService.getCompany();
//		Map query_map = new HashMap();
//		query_map.put("companyId",company.getId());
//		List<Map> productSales = orderItemService.getProductSales(query_map);
//		model.addAttribute("productSales", productSales);
////		List<Map> companySales = orderItemService.companySales(null);
////		model.addAttribute("companySales", companySales);
//		JSONArray companySalesJsonArray = new JSONArray();
//		for (Map product : productSales) {
//			product.put("companyId",company.getId());
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("name",product.get("name"));
//			jsonObject.put("data",orderItemService.companySalesByProductName(product));
//			companySalesJsonArray.add(jsonObject);
//		}
//		model.addAttribute("companySalesJsonArray",companySalesJsonArray);
//
//		List<Map> years = orderItemService.getPaymentYear(query_map);
//		model.addAttribute("years",years);
//		return "web/orderSalesStatistics/statistics";
//	}


	/**
	 * 产品数据统计
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model) {

		Company company = companyService.getCompany();
		Map query_map = new HashMap();
		query_map.put("companyid",company.getId());
		query_map.put("companyId",company.getId());
		List<Product> products = productService.getAll(query_map);
		model.addAttribute("products",products);
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		query_map.put("year",year);
		List<Map> productStatisic = productStatisticsService.getProductStatisic(query_map);//产品访问量分析
		model.addAttribute("productStatisic",productStatisic);
		List<Map> productStatisicProportion = productStatisticsService.getProductStatisicProportion(query_map);//产品访问量占比
		model.addAttribute("productStatisicProportion",productStatisicProportion);
		query_map.put("type",0);
		List<Map> demonstrationStatisics0 = demonstrationStatisticsService.getDemonstrationStatisics(query_map);//产品申请演示分析
		model.addAttribute("demonstrationStatisics0",demonstrationStatisics0);
		query_map.put("type",1);
		List<Map> demonstrationStatisics1 = demonstrationStatisticsService.getDemonstrationStatisics(query_map);//产品申请试用分析
		model.addAttribute("demonstrationStatisics1",demonstrationStatisics1);
		query_map.put("type",2);
		List<Map> demonstrationStatisics2 = demonstrationStatisticsService.getDemonstrationStatisics(query_map);//产品下载分析
		model.addAttribute("demonstrationStatisics2",demonstrationStatisics2);
		List<Map> countProductStatistics = orderItemService.getCountProductStatistics(query_map);//产品销量数量分析
		model.addAttribute("countProductStatistics",countProductStatistics);

		List<Map> countProductPruchaseNames = orderItemService.getCountProductPruchaseNames(query_map);//产品购买方式分析
		model.addAttribute("countProductPruchaseNames",countProductPruchaseNames);


		return "web/orderSalesStatistics/productDataAnalysis";
	}


	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/productSalesData", method = RequestMethod.POST)
	@ResponseBody
	public String companySales(Long productId,Integer type, Integer year ,String startDate ,String endDate){
		Company company = companyService.getCompany();
		JSONObject jsonObject = new JSONObject();
		Map query_map = new HashMap();
		query_map.put("companyid",company.getId());
		query_map.put("companyId",company.getId());
		query_map.put("productId",productId);
		query_map.put("year",year);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		if (type==1){
			List<Map> productStatisic = productStatisticsService.getProductStatisic(query_map);//产品访问量分析
			jsonObject.put("productStatisic",productStatisic);
		}
		if (type==2){
			List<Map> productStatisicProportion = productStatisticsService.getProductStatisicProportion(query_map);//产品访问量占比
			jsonObject.put("productStatisicProportion",productStatisicProportion);
		}
		if (type==3){
			query_map.put("type",0);
			List<Map> demonstrationStatisics0 = demonstrationStatisticsService.getDemonstrationStatisics(query_map);//产品申请演示分析
			jsonObject.put("demonstrationStatisics0",demonstrationStatisics0);
		}
		if (type==4){
			query_map.put("type",1);
			List<Map> demonstrationStatisics1 = demonstrationStatisticsService.getDemonstrationStatisics(query_map);//产品申请试用分析
			jsonObject.put("demonstrationStatisics1",demonstrationStatisics1);
		}
		if (type==5){
			query_map.put("type",2);
			List<Map> demonstrationStatisics2 = demonstrationStatisticsService.getDemonstrationStatisics(query_map);//产品下载分析
			jsonObject.put("demonstrationStatisics2",demonstrationStatisics2);
		}
		if (type==6){
			List<Map> countProductStatistics = orderItemService.getCountProductStatistics(query_map);//产品销量数量分析
			jsonObject.put("countProductStatistics",countProductStatistics);
		}
		if (type==7){
			List<Map> countProductPruchaseNames = orderItemService.getCountProductPruchaseNames(query_map);//产品购买方式分析
			jsonObject.put("countProductPruchaseNames",countProductPruchaseNames);
		}

		return ResultUtil.success(jsonObject);
	}









	/**
     * 供应商销量统计
	 * @param type
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "/companySales", method = RequestMethod.POST)
	@ResponseBody
	public String companySales(Long type ,String startDate ,String endDate){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		query_map.put("type",type);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}

		List<Map> companySales = orderItemService.companySales(query_map);
		return ResultUtil.success(companySales);
	}


}
