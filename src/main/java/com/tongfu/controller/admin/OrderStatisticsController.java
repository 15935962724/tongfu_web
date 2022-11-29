package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.service.AdminService;
import com.tongfu.service.MemberService;
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
@RequestMapping("/admin/orderStatistics")
public class OrderStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${map-key}")
	private String mapKey;

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;


	@RequestMapping("/statistics")
	public String list(Model model) {
		List<Map> yearOrder = orderService.getYearOrder(null);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < yearOrder.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			if (i==0){
				jsonObject.put("name",yearOrder.get(i).get("year"));
				jsonObject.put("type","column");
				jsonObject.put("data",orderService.thatYearOrder(yearOrder.get(i)));

			}else {
				jsonObject.put("name",yearOrder.get(i).get("year"));
				jsonObject.put("type",i%2==0?"line":"area");
				jsonObject.put("data",orderService.thatYearOrder(yearOrder.get(i)));
			}
			jsonArray.add(jsonObject);

		}
		System.out.println(jsonArray.toString());

		model.addAttribute("mapList",yearOrder);
		model.addAttribute("series",jsonArray);


		List<Map> amountOrder = orderService.getamountOrder(null);


		JSONArray systemJsonArray = new JSONArray();
		JSONArray orderJsonArray = new JSONArray();
		for (Map map : amountOrder) {
			JSONObject jsonObject = new JSONObject();
			JSONObject orderJsonObject = new JSONObject();
			jsonObject.put("name",map.get("payment_method_name"));
			jsonObject.put("data",orderService.getPaymentMethod(map));
			systemJsonArray.add(jsonObject);

			orderJsonObject.put("name",map.get("payment_method_name"));
			orderJsonObject.put("data",orderService.getPaymentAmount(map));
			orderJsonArray.add(orderJsonObject);
		}
		model.addAttribute("systemJsonArray",systemJsonArray);
		model.addAttribute("orderJsonArray",orderJsonArray);
		model.addAttribute("amountOrder",amountOrder);
		List<Map> orderSource = orderService.getOrderSource(null);
		model.addAttribute("orderSource", JSON.toJSON(orderSource));
		List<Map> orderSourcePie = orderService.getOrderSourcePie(null);
		model.addAttribute("orderSourcePie", orderSourcePie);

		return "admin/orderStatistics/statistics";
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
