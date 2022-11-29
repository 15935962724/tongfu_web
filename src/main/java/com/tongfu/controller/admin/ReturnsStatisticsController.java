package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.service.*;
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
@RequestMapping("/admin/returnsStatistics")
public class ReturnsStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${map-key}")
	private String mapKey;

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReturnsService returnsService; //退货单

	@Autowired
	private RefundsService refundsService;//退款单


	@RequestMapping("/statistics")
	public String list(Model model) {
		List<Map> yearReturns = returnsService.getYearReturns(null);

		JSONArray jsonArray = new JSONArray();
		JSONArray refundsJsonArray = new JSONArray();
		for (int i = 0; i < yearReturns.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			JSONObject refunds = new JSONObject();
			if (i==0){
				jsonObject.put("name",yearReturns.get(i).get("year"));
				jsonObject.put("type","column");
				jsonObject.put("data",returnsService.thatYearReturns(yearReturns.get(i)));

				refunds.put("name",yearReturns.get(i).get("year"));
				refunds.put("type","column");
				refunds.put("data",refundsService.thatYearReturnsAmount(yearReturns.get(i)));

			}else {
				jsonObject.put("name",yearReturns.get(i).get("year"));
				jsonObject.put("type",i%2==0?"line":"area");
				jsonObject.put("data",returnsService.thatYearReturns(yearReturns.get(i)));

				refunds.put("name",yearReturns.get(i).get("year"));
				refunds.put("type",i%2==0?"line":"area");
				refunds.put("data",refundsService.thatYearReturnsAmount(yearReturns.get(i)));
			}
			jsonArray.add(jsonObject);
			refundsJsonArray.add(refunds);

		}
		System.out.println(jsonArray.toString());

		model.addAttribute("mapList",yearReturns);
		model.addAttribute("series",jsonArray);
		model.addAttribute("refundsJsonArray",refundsJsonArray);

		List<Map> yearReturnsAmounts = refundsService.getYearReturnsAmount(null);
		model.addAttribute("yearReturnsAmounts",yearReturnsAmounts);

		//查询被退货的供应商
		List<Map> returnsCompany = returnsService.getReturnsCompany(null);
		model.addAttribute("returnsCompany",returnsCompany);
//		JSONArray returnsCompanyJsonArray = new JSONArray();
//		for (Map map : returnsCompany) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("name",map.get("company"));
//			jsonObject.put("data",orderService.getPaymentMethod(map));
//			returnsCompanyJsonArray.add(jsonObject);
//		}
//		model.addAttribute("returnsCompanyJsonArray",returnsCompanyJsonArray);

		//查询被退款的供应商
		List<Map> refundsCompany = refundsService.getRefundsCompany(null);
		model.addAttribute("refundsCompany",refundsCompany);

		return "admin/returnsStatistics/statistics";
	}


	/**
	 * 按供应商统计退货数
	 * @param type
	 * @return
	 */
   @RequestMapping(value = "/returnsCompany", method = RequestMethod.POST)
   @ResponseBody
	public String returnsCompany(Long type ,String startDate ,String endDate){
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

	   List<Map> returnsCompany = returnsService.getReturnsCompany(query_map);
		return ResultUtil.success(returnsCompany);
	}


	/**
	 * 退款供应商
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/refundsCompany", method = RequestMethod.POST)
	@ResponseBody
	public String refundsCompany(Long type ,String startDate ,String endDate){
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

		List<Map> refundsCompany = refundsService.getRefundsCompany(query_map);
		return ResultUtil.success(refundsCompany);
	}

}
