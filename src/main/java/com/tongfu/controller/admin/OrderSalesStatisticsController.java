package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.service.AdminService;
import com.tongfu.service.MemberService;
import com.tongfu.service.OrderItemService;
import com.tongfu.service.OrderService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/orderSalesStatistics")
public class OrderSalesStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderItemService orderItemService;

	@RequestMapping("/statistics")
	public String statistics(Model model) {


		List<Map> years = orderItemService.getYear(null);
		model.addAttribute("years",years);
		List<Map> companySales = orderItemService.companySales(null);
		model.addAttribute("companySales", companySales);
		JSONArray companySalesJsonArray = new JSONArray();
		for (Map companySale : companySales) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",companySale.get("name"));
			jsonObject.put("data",orderItemService.companySalesByCompanyName(companySale));
			companySalesJsonArray.add(jsonObject);
		}
		model.addAttribute("companySalesJsonArray",companySalesJsonArray);

		List<Map> companyVolume = orderItemService.companyVolume(null);
		model.addAttribute("companyVolume", companyVolume);
		JSONArray companyVolumeJsonArray = new JSONArray();
		for (Map map : companyVolume) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",map.get("name"));
			jsonObject.put("data",orderItemService.companyVolumeByCompanyName(map));
			companyVolumeJsonArray.add(jsonObject);
		}
		model.addAttribute("companyVolumeJsonArray",companyVolumeJsonArray);
		return "admin/orderSalesStatistics/statistics";
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

	/**
	 * 供应商销售额统计
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/companyVolume", method = RequestMethod.POST)
	@ResponseBody
	public String companyVolume(Long type ,String startDate ,String endDate){
		Map query_map = new HashMap();
		query_map.put("type",type);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}

		List<Map> companyVolume = orderItemService.companyVolume(query_map);
		return ResultUtil.success(companyVolume);
	}

}
