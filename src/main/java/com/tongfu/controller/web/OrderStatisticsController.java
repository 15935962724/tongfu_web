package com.tongfu.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.entity.Company;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.MemberService;
import com.tongfu.service.OrderService;
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

@Controller("weborderStatistics")
@RequestMapping("/web/orderStatistics")
public class OrderStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;


//	@RequestMapping("/statistics")
//	public String statistics(Model model) {
//		Company company = companyService.getCompany();
//		Map query_map = new HashMap();
//		query_map.put("companyId",company.getId());
//		List<Map> yearOrder = orderService.getYearOrder(query_map);
//
//		JSONArray jsonArray = new JSONArray();
//		for (int i = 0; i < yearOrder.size(); i++) {
//			yearOrder.get(i).put("companyId",company.getId());
//			JSONObject jsonObject = new JSONObject();
//			if (i==0){
//				jsonObject.put("name",yearOrder.get(i).get("year"));
//				jsonObject.put("type","column");
//				jsonObject.put("data",orderService.thatYearOrder(yearOrder.get(i)));
//
//			}else {
//				jsonObject.put("name",yearOrder.get(i).get("year"));
//				jsonObject.put("type",i%2==0?"line":"area");
//				jsonObject.put("data",orderService.thatYearOrder(yearOrder.get(i)));
//			}
//			jsonArray.add(jsonObject);
//
//		}
//		System.out.println(jsonArray.toString());
//
//		model.addAttribute("mapList",yearOrder);
//		model.addAttribute("series",jsonArray);
//
//
//		List<Map> amountOrder = orderService.getamountOrder(query_map);
//
//
//		JSONArray systemJsonArray = new JSONArray();
//		JSONArray orderJsonArray = new JSONArray();
//		for (Map map : amountOrder) {
//			map.put("companyId",company.getId());
//			JSONObject jsonObject = new JSONObject();
//			JSONObject orderJsonObject = new JSONObject();
//			jsonObject.put("name",map.get("payment_method_name"));
//			jsonObject.put("data",orderService.getPaymentMethod(map));
//			systemJsonArray.add(jsonObject);
//
//			orderJsonObject.put("name",map.get("payment_method_name"));
//			orderJsonObject.put("data",orderService.getPaymentAmount(map));
//			orderJsonArray.add(orderJsonObject);
//		}
//		model.addAttribute("systemJsonArray",systemJsonArray);
//		model.addAttribute("orderJsonArray",orderJsonArray);
//		model.addAttribute("amountOrder",amountOrder);
//		List<Map> orderSource = orderService.getOrderSource(query_map);
//		model.addAttribute("orderSource", JSON.toJSON(orderSource));
//		List<Map> orderSourcePie = orderService.getOrderSourcePie(query_map);
//		model.addAttribute("orderSourcePie", orderSourcePie);
//		return "web/orderStatistics/statistics";
//	}


	/**
	 * 营销数据统计
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model) {
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		List<Map> orderprovince = orderService.getOrderprovince(query_map);//全国销售地区分析  全国城市销售排名
		model.addAttribute("orderprovince", orderprovince);
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		query_map.put("year",year);
		List<Map> sumOrderAmountPaid = orderService.getSumOrderAmountPaid(query_map);//销售额分析
		model.addAttribute("sumOrderAmountPaid", sumOrderAmountPaid);
		List<Map> comparativeAnalysisOfSales = orderService.getComparativeAnalysisOfSales(query_map);//销售额对比分析
		model.addAttribute("comparativeAnalysisOfSales", comparativeAnalysisOfSales);
		List<Map> countOrders = orderService.getCountOrders(query_map);//订单分析
		model.addAttribute("countOrders", countOrders);
		List<Map> countOrderOfSales = orderService.getCountOrderOfSales(query_map);//订单对比分析
		model.addAttribute("countOrderOfSales", countOrderOfSales);
		List<Map> countRefurnsOrder = orderService.getCountRefurnsOrder(query_map);//退货分析
		model.addAttribute("countRefurnsOrder", countRefurnsOrder);


		return "web/orderStatistics/marketingDataAnalysis";
	}


	/**
	 * 销售分析
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/salesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public String salesAnalysis(Integer type ,Integer year){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		JSONObject jsonObject = new JSONObject();
		query_map.put("companyId",company.getId());
		query_map.put("year",year);
		if (type==1){
			List<Map> sumOrderAmountPaid = orderService.getSumOrderAmountPaid(query_map);//销售额分析
			jsonObject.put("sumOrderAmountPaid", sumOrderAmountPaid);
		}
		if (type==2){
			List<Map> comparativeAnalysisOfSales = orderService.getComparativeAnalysisOfSales(query_map);//销售额对比分析
			jsonObject.put("comparativeAnalysisOfSales", comparativeAnalysisOfSales);
		}
		if (type==3){
			List<Map> countOrders = orderService.getCountOrders(query_map);//订单分析
			jsonObject.put("countOrders", countOrders);
		}
		if (type==4){
			List<Map> countOrderOfSales = orderService.getCountOrderOfSales(query_map);//订单对比分析
			jsonObject.put("countOrderOfSales", countOrderOfSales);
		}
		if (type==5){
			List<Map> countRefurnsOrder = orderService.getCountRefurnsOrder(query_map);//退货分析
			jsonObject.put("countRefurnsOrder", countRefurnsOrder);
		}
		return ResultUtil.success(jsonObject);
	}




	/**
	 * 全国销售地区分析
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/orderprovince", method = RequestMethod.POST)
	@ResponseBody
	public String orderprovince(String startDate ,String endDate){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		List<Map> orderprovince = orderService.getOrderprovince(query_map);
		return ResultUtil.success(orderprovince);
	}




	/**
	 * 按地区 统计
	 * @param type
	 * @return
	 */
   @RequestMapping(value = "/provinceStatisics", method = RequestMethod.POST)
   @ResponseBody
	public String provinceStatisics(Long type ,String startDate ,String endDate){
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

		List<Map> provinces = memberService.getProvince(query_map);
		return ResultUtil.success(provinces);
	}


	/**
     * 根据来源统计用户注册数
	 * @param type
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "/sourcesStatisics", method = RequestMethod.POST)
	@ResponseBody
	public String sourcesStatisics(Long type ,String startDate ,String endDate){
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

		List<Map> sources = memberService.getSource(query_map);
		return ResultUtil.success(sources);
	}


	/**
     * 按支付方式自定义统计
	 * @param type
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "/amountOrder", method = RequestMethod.POST)
	@ResponseBody
	public String amountOrder(Long type ,String startDate ,String endDate){
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

		List<Map> amountOrder = orderService.getamountOrder(query_map);
		return ResultUtil.success(amountOrder);
	}

	/**
	 * 订单来源自定义统计
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/orderSource", method = RequestMethod.POST)
	@ResponseBody
	public String orderSource(Long type ,String startDate ,String endDate){
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

		List<Map> orderSourcePie = orderService.getOrderSourcePie(query_map);
		return ResultUtil.success(orderSourcePie);
	}

}
