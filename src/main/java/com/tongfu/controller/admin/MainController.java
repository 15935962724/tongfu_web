package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.Admin;
import com.tongfu.entity.AdminRole;
import com.tongfu.entity.Company;
import com.tongfu.entity.Role;
import com.tongfu.service.*;
import com.tongfu.util.FloatUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class MainController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private ProductService productService;

	@Autowired
	CompanyService companyService;

	@Autowired
	ProductStatisticsService productStatisticsService;

	@Autowired
	OrderService orderService;

	@Autowired
	private AdIncomeService adIncomeService;

	@Autowired
    AdService adService;

	@Autowired
	MemberService memberService;


	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String list(Model model,@RequestParam(defaultValue = "月") String date) {
		Map<String,Object> query_map = new HashMap<>(	);
		Integer countProduct = 0; //访问量
		Float riseOrFallProduct = 0f;//访问量上涨或下跌
		Integer countOrder = 0; //订单量
		Float riseOrFallOrder = 0f;//订单量上涨或下跌

		Integer countMember = 0; //会员
		Float riseOrFallMember = 0f;//会员上涨或下跌

		Map map_ad = new HashMap();

		if(date.equals("月")){
				/*本月或上月 0代表本月 1代表上月*/
				query_map.put("month",0);
				Integer thisMonthCountProduct = productStatisticsService.getMonthCountProduct(query_map);
				countProduct = thisMonthCountProduct;
				model.addAttribute("thisMonthCountProduct",thisMonthCountProduct);//本月访问量

				Integer thisMonthCountMember = memberService.getMonthCountMember(query_map);
				countMember = thisMonthCountMember;
				model.addAttribute("thisMonthCountMember",thisMonthCountMember);//本月会员注册量

				Integer thisMonthCountOrder = orderService.getMonthCountOrder(query_map);
				countOrder = thisMonthCountOrder;
				model.addAttribute("thisMonthCountOrder",thisMonthCountOrder);//本月订单量

				query_map.put("month",1);
				Integer lastMonthCountProduct = productStatisticsService.getMonthCountProduct(query_map);//上月访问量
				riseOrFallProduct = FloatUtil.getFloatFormat(thisMonthCountProduct,lastMonthCountProduct);
				model.addAttribute("riseOrFallProduct",riseOrFallProduct);//本月访问量上涨或下跌

				Integer lastMonthCountMember = productStatisticsService.getMonthCountProduct(query_map);//上月会员
				riseOrFallMember = FloatUtil.getFloatFormat(thisMonthCountMember,lastMonthCountMember);
				model.addAttribute("riseOrFallMember",riseOrFallMember);//本月会员量上涨或下跌


				Integer lastMonthCountOrder = productStatisticsService.getMonthCountProduct(query_map);//上月订单量
				riseOrFallOrder = FloatUtil.getFloatFormat(thisMonthCountOrder,lastMonthCountOrder);
				model.addAttribute("riseOrFallOrder",riseOrFallOrder);//本月订单上涨或下跌


				map_ad= adIncomeService.getMonthCountAdPrice(null);//广告收入情况



			}
		if(date.equals("周")){
			/*<!--本周或上周0代表本周 1 代表上周-->*/

			query_map.put("week",0);
			Integer thisWeekCountProduct = productStatisticsService.getWeekCountProduct(query_map);
			countProduct = thisWeekCountProduct;
			model.addAttribute("thisWeekCountProduct",thisWeekCountProduct);//本周访问量

			Integer thisWeekCountOrder = orderService.getWeekCountOrder(query_map);
			countOrder = thisWeekCountOrder;
			model.addAttribute("thisWeekCountOrder",thisWeekCountOrder);//本周订单量

			Integer thisWeekCountMember = memberService.getWeekCountMember(query_map);
			countMember = thisWeekCountMember;
			model.addAttribute("thisWeekCountMember",thisWeekCountMember);//本周会员量

			query_map.put("week",1);
			Integer lastWeekhCountProduct = productStatisticsService.getWeekCountProduct(query_map);//上周访问量
			riseOrFallProduct = FloatUtil.getFloatFormat(thisWeekCountProduct,lastWeekhCountProduct);
			model.addAttribute("riseOrFallProduct",riseOrFallProduct);//本周访问量上涨或下跌

			Integer lastWeekCountOrder = productStatisticsService.getWeekCountProduct(query_map);//上周订单量
			riseOrFallOrder = FloatUtil.getFloatFormat(thisWeekCountOrder,lastWeekCountOrder);
			model.addAttribute("riseOrFallOrder",riseOrFallOrder);//本周订单量上涨或下跌

			Integer lastWeekCountMember = memberService.getWeekCountMember(query_map);//上周订单量
			riseOrFallMember = FloatUtil.getFloatFormat(thisWeekCountMember,lastWeekCountMember);
			model.addAttribute("riseOrFallMember",riseOrFallMember);//本周订单量上涨或下跌

			map_ad= adIncomeService.getWeekCountAdPrice(null);//广告收入情况

		}
		if(date.equals("日")){
				/*<!--今天或昨天 0代表今天 1代表昨天 -->*/
				query_map.put("day",0);
				Integer thisDayCountProduct = productStatisticsService.getDayCountProduct(query_map);
				countProduct = thisDayCountProduct;
				model.addAttribute("thisDayCountProduct",thisDayCountProduct);//当天访问量

				Integer thisDayCountOrder = orderService.getDayCountOrder(query_map);
				countOrder = thisDayCountOrder;
				model.addAttribute("thisDayCountOrder",thisDayCountOrder);//当天订单量

				Integer thisDayCountMember = memberService.getDayCountMember(query_map);
				countMember = thisDayCountMember;
				model.addAttribute("thisDayCountMember",thisDayCountMember);//当天会员量

				query_map.put("day",1);
				Integer lastDayCountProduct = productStatisticsService.getDayCountProduct(query_map);//前一天访问量
				riseOrFallProduct = FloatUtil.getFloatFormat(thisDayCountProduct,lastDayCountProduct);
				model.addAttribute("riseOrFallProduct",riseOrFallProduct);//前一天访问量上涨或下跌

				Integer lastDayCountOrder = orderService.getDayCountOrder(query_map);//前一天订单量
				riseOrFallOrder = FloatUtil.getFloatFormat(thisDayCountOrder,lastDayCountOrder);
				model.addAttribute("lastDayCountOrder",lastDayCountOrder);//本周订单访上涨或下跌

				Integer lastDayCountMember = orderService.getDayCountOrder(query_map);//前一天会员量
				riseOrFallMember = FloatUtil.getFloatFormat(thisDayCountMember,lastDayCountMember);
				model.addAttribute("lastDayCountMember",lastDayCountMember);//本天订单访上涨或下跌

				map_ad= adIncomeService.getDayCountAdPrice(null);//广告收入情况

			}

		List<Map> adAndProductData = adIncomeService.getMonthData(null);
		model.addAttribute("adAndProductData",adAndProductData);//最近11个月广告收入

//			List<Map> orderPriceData = orderService.getMonthData(null);
//			model.addAttribute("orderPriceData",orderPriceData);//最近11个月订单收入

		List<Map> productstatisicMap  = productStatisticsService.getProductstatisicMap(null);//首页地图坐标
		model.addAttribute("productstatisicMap", JSON.toJSON(productstatisicMap));//访问量地图
		List<Map> indexProductstatisicMap = productStatisticsService.getIndexProductstatisicMap(null);

		model.addAttribute("indexProductstatisicMap",indexProductstatisicMap);//首页地图数据

		query_map.put("week",1);
		List<Map> thisWeekData = adIncomeService.getWeekData(query_map);
		model.addAttribute("thisWeekData",thisWeekData);//上周广告收入
		query_map.put("week",2);
		List<Map> upperWeekData = adIncomeService.getWeekData(query_map);
		model.addAttribute("upperWeekData",upperWeekData);//上上周广告收入

		Map adIncomeCountData = adIncomeService.getWeekCountData(null);//本周 上周总数

		model.addAttribute("adIncomeCountData",adIncomeCountData);//本周 上周总数

		model.addAttribute("countProduct",countProduct);//访问量
		model.addAttribute("riseOrFallProduct",riseOrFallProduct);//访问量上涨或下跌
		model.addAttribute("countOrder",countOrder);//订单量
		model.addAttribute("map_ad",map_ad);//广告收入情况
		model.addAttribute("riseOrFallOrder",riseOrFallOrder);//订单量上涨或下跌
		model.addAttribute("countMember",countMember);//会员量
		model.addAttribute("riseOrFallMember",riseOrFallMember);//会员量上涨或下跌

	   return "admin/main";
	}



}
