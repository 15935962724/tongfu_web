package com.tongfu.controller.web;

import com.alibaba.fastjson.JSON;
import com.tongfu.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("webmain")
@RequestMapping("/web")
public class MainController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductStatisticsService productStatisticsService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DemonstrationStatisticsService demonstrationStatisticsService;

	@Autowired
	private DownloadStatisticsService downloadStatisticsService;

	@Autowired
	private AdService adService;

	@Autowired
	private JournalismService journalismService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private LearningService learningService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private ExhibitionService exhibitionService;

	@Autowired
	private ProductPurchaseService productPurchaseService;

	@Autowired
	private CompanyStatisticsService companyStatisticsService;

	/**
	 * 供应商主页
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/main")
	public String list(Model model,@RequestParam(defaultValue = "月") String date) {
		Map<String,Object> query_map = new HashMap<>(	);

		Integer productCount = 0;//商品总数
		Integer statusProductCount = 0;//待审核产品
		Integer marketableProductCount = 0;//下架商品总数
		Integer upperShelfProductCount = 0;//上架产品总数

		Integer adCouont = 0;//广告总数
		Integer toExamineAdCouont = 0;//广告待审核
		Integer toShowAdCouont = 0;//广告在展示
		Integer toOverdueadCouont = 0;//广告已过期


        Integer journalismCount = 0;//新闻
		Integer toExamineJournalismCouont = 0;//新闻待审核
		Integer toShowJournalismCouont = 0;//新闻在展示
		Integer hitsJournalismCouont = 0;//新闻点击量


		Integer countProduct = 0; //访问量
		Float riseOrFallProduct = 0f;//访问量上涨或下跌

		Integer countOrder = 0; //订单量
		Float riseOrFallOrder = 0f;//订单量上涨或下跌

		Integer countDemonstrationStatistics0 = 0; //申请演示
		Float riseOrFallDemonstrationStatistics0 = 0f;//申请演示上涨或下跌

		Integer countDemonstrationStatistics1 = 0; //申请试用
		Float riseOrFallDemonstrationStatistics1 = 0f;//申请试用上涨或下跌


		Integer productMessageCount = 0; //咨询
		Float riseOrFallproductMessageCount = 0f;//咨询上涨或下跌

		Integer countDownloadStatistics = 0; //下载次数
		Float riseOrFallDownloadStatistics = 0f;//申请试用上涨或下跌


			query_map.put("companyId",companyService.getCompany().getId());

			productCount = productService.getCountProduct(query_map);//商品总数

            adCouont = adService.getAll(query_map).size();//广告
			query_map.put("status",1);
			toExamineAdCouont = adService.toExamineAdCouont(query_map);//广告待审核
			query_map.put("status",3);
			toShowAdCouont = adService.toShowAdCouont(query_map);//广告在展示
			toOverdueadCouont = adService.toOverdueadCouont(query_map);//广告已过期




		journalismCount = journalismService.getAll(query_map).size();//新闻
		query_map.put("status",0);
		toExamineJournalismCouont = journalismService.toExamineJournalismCouont(query_map);//新闻待审核
		query_map.put("status",1);
		toShowJournalismCouont = journalismService.toShowJournalismCouont(query_map);//新闻在展示
		hitsJournalismCouont = journalismService.hitsJournalismCouont(query_map);//新闻点击量

		query_map.put("status",1);
		statusProductCount = productService.getCountProduct(query_map);//待审核产品
		query_map.put("status",null);

		query_map.put("isMarketable",false);
		 marketableProductCount = productService.getCountProduct(query_map);//下架商品总数

		query_map.put("isMarketable",true);
		upperShelfProductCount = productService.getCountProduct(query_map);//上架商品总数

		Map learngingMap = learningService.getLearngingMap(query_map);
		model.addAttribute("learngingMap",learngingMap);//学术活动相关信息

		Map mediaMap = mediaService.getMediaMap(query_map);
		model.addAttribute("mediaMap",mediaMap);//媒体报道

		Map exhibitionMap = exhibitionService.getExhibitionMap(query_map);
		model.addAttribute("exhibitionMap",exhibitionMap);//展会展览

		query_map.put("type",0);//0申请演示
		List<Map<String, Object>> demonstrationStatisticList0 = demonstrationStatisticsService.demonstrationStatisticList(query_map);//申请演示柱状图
		model.addAttribute("demonstrationStatisticList0",demonstrationStatisticList0);//申请演示柱状图

		query_map.put("type",1);//1申请试用
		List<Map<String,Object>> demonstrationStatisticList1 = demonstrationStatisticsService.demonstrationStatisticList(query_map);//申请试用柱状图
		model.addAttribute("demonstrationStatisticList1",demonstrationStatisticList1);//申请试用柱状图


		List<Map<String,Object>> messageStatisticList = messageService.messageStatisticList(query_map);//咨询柱状图
		model.addAttribute("messageStatisticList",messageStatisticList);//咨询柱状图


		List<Map<String,Object>> productstatisicHistogram = productStatisticsService.getProductstatisicHistogram(query_map);//访问量柱状图
		model.addAttribute("productstatisicHistogram",productstatisicHistogram);//访问量柱状图

		List<Map<String,Object>> orderHistogramStatistics = orderService.getHistogramStatistics(query_map);//订单柱状图
		model.addAttribute("orderHistogramStatistics",orderHistogramStatistics);//订单柱状图


		List<Map<String,Object>> downloadHistogramStatistics = downloadStatisticsService.getDownloadHistogramStatistics(query_map);//下载量柱状图
		model.addAttribute("downloadHistogramStatistics",downloadHistogramStatistics);//下载量柱状图


		    if(date.equals("月")){
				*//*本月或上月 0代表本月 1代表上月*//*
				query_map.put("month",0);
				Integer thisMonthCountProduct = productStatisticsService.getMonthCountProduct(query_map);
				countProduct = thisMonthCountProduct;
				model.addAttribute("thisMonthCountProduct",thisMonthCountProduct);//本月访问量

				Integer thisMonthCountOrder = orderService.getMonthCountOrder(query_map);
				countOrder = thisMonthCountOrder;
				model.addAttribute("thisMonthCountOrder",thisMonthCountOrder);//本月订单量

				query_map.put("type",0);//0申请演示
				Integer thisMonthCountDemonstrationStatistics0 = demonstrationStatisticsService.getMonthCountDemonstrationStatistics(query_map);
				countDemonstrationStatistics0 = thisMonthCountDemonstrationStatistics0;
				model.addAttribute("thisMonthCountDemonstrationStatistics0",thisMonthCountDemonstrationStatistics0);//本月申请演示

				query_map.put("type",1);//1申请试用

				Integer thisMonthCountDemonstrationStatistics1 = demonstrationStatisticsService.getMonthCountDemonstrationStatistics(query_map);
				countDemonstrationStatistics1 = thisMonthCountDemonstrationStatistics1;
				model.addAttribute("thisMonthCountDemonstrationStatistics1",thisMonthCountDemonstrationStatistics1);//本月申请试用

				Integer thisMonthCountDownloadStatistics = downloadStatisticsService.getMonthCountDownloadStatistics(query_map);
				countDownloadStatistics = thisMonthCountDownloadStatistics;
				model.addAttribute("thisMonthCountDownloadStatistics",thisMonthCountDownloadStatistics);//本月下载次数

				productMessageCount = messageService.getMonthCountProductMessage(query_map);
			    model.addAttribute("productMessageCount",productMessageCount);//本月咨询量

				*//**================上面为本月以下为上月====================**//*

				query_map.put("month",1);
				Integer lastMonthCountProduct = productStatisticsService.getMonthCountProduct(query_map);//上月访问量
				 riseOrFallProduct = FloatUtil.getFloatFormat(thisMonthCountProduct,lastMonthCountProduct);
				model.addAttribute("riseOrFallProduct",riseOrFallProduct);//本月访问量上涨或下跌

				query_map.put("type",0);//0申请演示
				Integer lastMonthCountDemonstrationStatistics0 = demonstrationStatisticsService.getMonthCountDemonstrationStatistics(query_map);//上月访申请演示
				 riseOrFallDemonstrationStatistics0 = FloatUtil.getFloatFormat(thisMonthCountDemonstrationStatistics0,lastMonthCountDemonstrationStatistics0);

				model.addAttribute("riseOrFallDemonstrationStatistics0",riseOrFallDemonstrationStatistics0);//本月申请演示上涨或下跌

				query_map.put("type",1);//1申请试用
				Integer lastMonthCountDemonstrationStatistics1 = demonstrationStatisticsService.getMonthCountDemonstrationStatistics(query_map);//上月访申请试用
				 riseOrFallDemonstrationStatistics1 = FloatUtil.getFloatFormat(thisMonthCountDemonstrationStatistics1,lastMonthCountDemonstrationStatistics1);
				model.addAttribute("riseOrFallDemonstrationStatistics1",riseOrFallDemonstrationStatistics1);//本月申请试用上涨或下跌

				Integer lastMonthCountOrder = orderService.getMonthCountOrder(query_map);//上月订单量
				 riseOrFallOrder = FloatUtil.getFloatFormat(thisMonthCountOrder,lastMonthCountOrder);
				model.addAttribute("lastMonthCountOrder",lastMonthCountOrder);//本月订单上涨或下跌


				Integer lastMonthCountDownloadStatistics = downloadStatisticsService.getMonthCountDownloadStatistics(query_map);//上月下载
				 riseOrFallDownloadStatistics = FloatUtil.getFloatFormat(thisMonthCountDownloadStatistics,lastMonthCountDownloadStatistics);
				model.addAttribute("riseOrFallDownloadStatistics",riseOrFallDownloadStatistics);//本月下载上涨或下跌

				Integer lastMonthCountProductMessage = messageService.getMonthCountProductMessage(query_map);
				riseOrFallproductMessageCount = FloatUtil.getFloatFormat(productMessageCount,lastMonthCountProductMessage);

				model.addAttribute("riseOrFallproductMessageCount",riseOrFallproductMessageCount);//本月咨询上涨或下跌


		}
			if(date.equals("周")){
				 *//*<!--本周或上周0代表本周 1 代表上周-->*//*

				query_map.put("week",0);
				Integer thisWeekCountProduct = productStatisticsService.getWeekCountProduct(query_map);
				countProduct = thisWeekCountProduct;
				model.addAttribute("thisWeekCountProduct",thisWeekCountProduct);//本周访问量

				Integer thisWeekCountOrder = orderService.getWeekCountOrder(query_map);
				countOrder = thisWeekCountOrder;
				model.addAttribute("thisWeekCountOrder",thisWeekCountOrder);//本周订单量

				query_map.put("type",0);//0申请演示
				Integer thisWeekCountDemonstrationStatistics0 = demonstrationStatisticsService.getWeekCountDemonstrationStatistics(query_map);
				countDemonstrationStatistics0  = thisWeekCountDemonstrationStatistics0;
				model.addAttribute("thisWeekCountDemonstrationStatistics0",thisWeekCountDemonstrationStatistics0);//本周申请演示

				query_map.put("type",1);//1申请试用
				Integer thisWeekCountDemonstrationStatistics1 = demonstrationStatisticsService.getWeekCountDemonstrationStatistics(query_map);
				countDemonstrationStatistics1 = thisWeekCountDemonstrationStatistics1;
				model.addAttribute("thisWeekCountDemonstrationStatistics1",thisWeekCountDemonstrationStatistics1);//本周申请试用

				Integer thisWeekCountDownloadStatistics = downloadStatisticsService.getWeekCountDownloadStatistics(query_map);
				countDownloadStatistics = thisWeekCountDownloadStatistics;
				model.addAttribute("thisWeekCountDownloadStatistics",thisWeekCountDownloadStatistics);//本周下载次数

				productMessageCount = messageService.getWeekCountProductMessage(query_map);
				model.addAttribute("productMessageCount",productMessageCount);//本周咨询量

				*//**============上面为本周以下为上周===============**//*
				query_map.put("week",1);
				Integer lastWeekhCountProduct = productStatisticsService.getWeekCountProduct(query_map);//上周访问量
				riseOrFallProduct = FloatUtil.getFloatFormat(thisWeekCountProduct,lastWeekhCountProduct);
				model.addAttribute("riseOrFallProduct",riseOrFallProduct);//本周访问量上涨或下跌

				query_map.put("type",0);//0申请演示
				Integer lastWeekCountDemonstrationStatistics0 = demonstrationStatisticsService.getWeekCountDemonstrationStatistics(query_map);//上周访申请试用
				 riseOrFallDemonstrationStatistics0 = FloatUtil.getFloatFormat(thisWeekCountDemonstrationStatistics0,lastWeekCountDemonstrationStatistics0);
				model.addAttribute("riseOrFallDemonstrationStatistics0",riseOrFallDemonstrationStatistics0);//本周申请演示上涨或下跌

				query_map.put("type",1);//1申请试用
				Integer lastMonthCountDemonstrationStatistics1 = demonstrationStatisticsService.getWeekCountDemonstrationStatistics(query_map);//上周访申请试用
				 riseOrFallDemonstrationStatistics1 = FloatUtil.getFloatFormat(thisWeekCountDemonstrationStatistics1,lastMonthCountDemonstrationStatistics1);
				model.addAttribute("riseOrFallDemonstrationStatistics1",riseOrFallDemonstrationStatistics1);//本周申请试用上涨或下跌

				Integer lastWeekCountOrder = orderService.getWeekCountOrder(query_map);//上周订单量
				 riseOrFallOrder = FloatUtil.getFloatFormat(thisWeekCountOrder,lastWeekCountOrder);
				model.addAttribute("riseOrFallOrder",riseOrFallOrder);//本周订单量上涨或下跌


				Integer lastMonthCountDownloadStatistics = downloadStatisticsService.getWeekCountDownloadStatistics(query_map);//上周下载量
				 riseOrFallDownloadStatistics = FloatUtil.getFloatFormat(thisWeekCountDownloadStatistics,lastMonthCountDownloadStatistics);
				model.addAttribute("riseOrFallDownloadStatistics",riseOrFallDownloadStatistics);//本周下载量上涨或下跌

				Integer lastWeekCountProductMessage = messageService.getWeekCountProductMessage(query_map);
				riseOrFallproductMessageCount = FloatUtil.getFloatFormat(productMessageCount,lastWeekCountProductMessage);
				model.addAttribute("riseOrFallproductMessageCount",riseOrFallproductMessageCount);//本周咨询上涨或下跌


			}
			if(date.equals("日")){
				 *//*<!--今天或昨天 0代表今天 1代表昨天 -->*//*

				query_map.put("day",0);
				Integer thisDayCountProduct = productStatisticsService.getDayCountProduct(query_map);
				countProduct = thisDayCountProduct;
				model.addAttribute("thisDayCountProduct",thisDayCountProduct);//当天访问量

				Integer thisDayCountOrder = orderService.getDayCountOrder(query_map);
				countOrder = thisDayCountOrder;
				model.addAttribute("thisDayCountOrder",thisDayCountOrder);//当天订单量

				query_map.put("type",0);//0申请演示
				Integer thisDayCountDemonstrationStatistics0 = demonstrationStatisticsService.getDayCountDemonstrationStatistics(query_map);
				countDemonstrationStatistics0 = thisDayCountDemonstrationStatistics0;
				model.addAttribute("thisDayCountDemonstrationStatistics0",thisDayCountDemonstrationStatistics0);//当天申请演示

				query_map.put("type",1);//1申请试用
				Integer thisDayCountDemonstrationStatistics1 = demonstrationStatisticsService.getDayCountDemonstrationStatistics(query_map);
				countDemonstrationStatistics1 = thisDayCountDemonstrationStatistics1;
				model.addAttribute("thisDayCountDemonstrationStatistics1",thisDayCountDemonstrationStatistics1);//当天申请试用

				Integer thisDayCountDownloadStatistics = downloadStatisticsService.getDayCountDownloadStatistics(query_map);
				countDownloadStatistics = thisDayCountDownloadStatistics;
				model.addAttribute("thisDayCountDownloadStatistics",thisDayCountDownloadStatistics);//当天下载次数

				productMessageCount = messageService.getDayCountProductMessage(query_map);
				model.addAttribute("productMessageCount",productMessageCount);//当天咨询量
				*//**=======以上为当天的数据以下为昨天的数据========**//*

				query_map.put("day",1);
				Integer lastDayCountProduct = productStatisticsService.getDayCountProduct(query_map);//前一天访问量
				 riseOrFallProduct = FloatUtil.getFloatFormat(thisDayCountProduct,lastDayCountProduct);
				model.addAttribute("riseOrFallProduct",riseOrFallProduct);//前一天访问量上涨或下跌

				query_map.put("type",0);//0申请演示
				Integer lastDayCountDemonstrationStatistics0 = demonstrationStatisticsService.getDayCountDemonstrationStatistics(query_map);//前一天访申请试用
				 riseOrFallDemonstrationStatistics0 = FloatUtil.getFloatFormat(thisDayCountDemonstrationStatistics0,lastDayCountDemonstrationStatistics0);
				model.addAttribute("riseOrFallDemonstrationStatistics0",riseOrFallDemonstrationStatistics0);//前一天申请演示上涨或下跌



				query_map.put("type",1);//1申请试用
				Integer lastDayCountDemonstrationStatistics1 = demonstrationStatisticsService.getDayCountDemonstrationStatistics(query_map);//上周访申请试用
				 riseOrFallDemonstrationStatistics1 = FloatUtil.getFloatFormat(thisDayCountDemonstrationStatistics1,lastDayCountDemonstrationStatistics1);
				model.addAttribute("riseOrFallDemonstrationStatistics1",riseOrFallDemonstrationStatistics1);//本周申请试用上涨或下跌

				Integer lastDayCountOrder = orderService.getDayCountOrder(query_map);//昨天订单量
				 riseOrFallOrder = FloatUtil.getFloatFormat(thisDayCountOrder,lastDayCountOrder);
				model.addAttribute("lastDayCountOrder",lastDayCountOrder);//本周订单访上涨或下跌


				Integer lastDayCountDownloadStatistics = downloadStatisticsService.getDayCountDownloadStatistics(query_map);//昨天下载问量
				 riseOrFallDownloadStatistics = FloatUtil.getFloatFormat(thisDayCountDownloadStatistics,lastDayCountDownloadStatistics);
				model.addAttribute("riseOrFallDownloadStatistics",riseOrFallDownloadStatistics);//今天下载量上涨或下跌


				Integer lastDayCountProductMessage = messageService.getDayCountProductMessage(query_map);
				riseOrFallproductMessageCount = FloatUtil.getFloatFormat(productMessageCount,lastDayCountProductMessage);
				model.addAttribute("riseOrFallproductMessageCount",riseOrFallproductMessageCount);//今天咨询上涨或下跌



			}


		model.addAttribute("productCount",productCount);//商品总数
		model.addAttribute("marketableProductCount",marketableProductCount);//下架商品总数
        model.addAttribute("statusProductCount",statusProductCount);//待审核产品
		model.addAttribute("upperShelfProductCount",upperShelfProductCount);//待审核产品

        model.addAttribute("adCouont",adCouont);//广告
		model.addAttribute("toExamineAdCouont",toExamineAdCouont);//广告待审核
		model.addAttribute("toShowAdCouont",toShowAdCouont);//广告在展示
		model.addAttribute("toOverdueadCouont",toOverdueadCouont);//广告已过期

        model.addAttribute("journalismCount",journalismCount);//新闻
		model.addAttribute("toExamineJournalismCouont",toExamineJournalismCouont);//新闻待审核
		model.addAttribute("toShowJournalismCouont",toShowJournalismCouont); //新闻在展示
		model.addAttribute("hitsJournalismCouont",hitsJournalismCouont); //新闻点击量



		model.addAttribute("countProduct",countProduct);//访问量
		model.addAttribute("riseOrFallProduct",riseOrFallProduct);//访问量上涨或下跌
		model.addAttribute("countOrder",countOrder);//订单量
		model.addAttribute("riseOrFallOrder",riseOrFallOrder);//订单量上涨或下跌
		model.addAttribute("countDemonstrationStatistics0",countDemonstrationStatistics0); //申请演示
		model.addAttribute("riseOrFallDemonstrationStatistics0",riseOrFallDemonstrationStatistics0);//申请演示上涨或下跌

		model.addAttribute("countDemonstrationStatistics1",countDemonstrationStatistics1); //申请试用
		model.addAttribute("riseOrFallDemonstrationStatistics1",riseOrFallDemonstrationStatistics1);//申请试用上涨或下跌
		model.addAttribute("countDownloadStatistics",countDownloadStatistics);//下载次数
		model.addAttribute("riseOrFallDownloadStatistics",riseOrFallDownloadStatistics);//申请试用上涨或下跌

		model.addAttribute("date",date);

		List<Long> purchasesLackStock = productPurchaseService.getPurchasesLackStock(query_map);
		model.addAttribute("lackStockCount",purchasesLackStock.size());
		return "web/main";
	}*/


	/**
	 * 供应商主页
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String list(Model model,@RequestParam(defaultValue = "月") String date) {
		Map<String,Object> query_map = new HashMap<>(	);

		Integer productCount = 0;//商品总数
		Integer statusProductCount = 0;//待审核产品
		Integer marketableProductCount = 0;//下架商品总数
		Integer upperShelfProductCount = 0;//上架产品总数

		Integer adCouont = 0;//广告总数
		Integer toExamineAdCouont = 0;//广告待审核
		Integer toShowAdCouont = 0;//广告在展示
		Integer toOverdueadCouont = 0;//广告已过期


		Integer journalismCount = 0;//新闻
		Integer toExamineJournalismCouont = 0;//新闻待审核
		Integer toShowJournalismCouont = 0;//新闻在展示
		Integer hitsJournalismCouont = 0;//新闻点击量

		query_map.put("companyId",companyService.getCompany().getId());

		productCount = productService.getCountProduct(query_map);//商品总数

		adCouont = adService.getAll(query_map).size();//广告
		query_map.put("status",1);
		toExamineAdCouont = adService.toExamineAdCouont(query_map);//广告待审核
		query_map.put("status",3);
		toShowAdCouont = adService.toShowAdCouont(query_map);//广告在展示
		toOverdueadCouont = adService.toOverdueadCouont(query_map);//广告已过期

		journalismCount = journalismService.getAll(query_map).size();//新闻
		query_map.put("status",0);
		toExamineJournalismCouont = journalismService.toExamineJournalismCouont(query_map);//新闻待审核
		query_map.put("status",1);
		toShowJournalismCouont = journalismService.toShowJournalismCouont(query_map);//新闻在展示
		hitsJournalismCouont = journalismService.hitsJournalismCouont(query_map);//新闻点击量

		query_map.put("status",1);
		statusProductCount = productService.getCountProduct(query_map);//待审核产品
		query_map.put("status",null);

		query_map.put("isMarketable",false);
		marketableProductCount = productService.getCountProduct(query_map);//下架商品总数

		query_map.put("isMarketable",true);
		upperShelfProductCount = productService.getCountProduct(query_map);//上架商品总数

		Map learngingMap = learningService.getLearngingMap(query_map);
		model.addAttribute("learngingMap",learngingMap);//学术活动相关信息

		Map mediaMap = mediaService.getMediaMap(query_map);
		model.addAttribute("mediaMap",mediaMap);//媒体报道

		Map exhibitionMap = exhibitionService.getExhibitionMap(query_map);
		model.addAttribute("exhibitionMap",exhibitionMap);//展会展览

		model.addAttribute("productCount",productCount);//商品总数
		model.addAttribute("marketableProductCount",marketableProductCount);//下架商品总数
		model.addAttribute("statusProductCount",statusProductCount);//待审核产品
		model.addAttribute("upperShelfProductCount",upperShelfProductCount);//待审核产品

		model.addAttribute("adCouont",adCouont);//广告
		model.addAttribute("toExamineAdCouont",toExamineAdCouont);//广告待审核
		model.addAttribute("toShowAdCouont",toShowAdCouont);//广告在展示
		model.addAttribute("toOverdueadCouont",toOverdueadCouont);//广告已过期

		model.addAttribute("journalismCount",journalismCount);//新闻
		model.addAttribute("toExamineJournalismCouont",toExamineJournalismCouont);//新闻待审核
		model.addAttribute("toShowJournalismCouont",toShowJournalismCouont); //新闻在展示
		model.addAttribute("hitsJournalismCouont",hitsJournalismCouont); //新闻点击量
		query_map.put("type",0);//申请演示
		Map applyDemonstration = demonstrationStatisticsService.getApplyDemonstration(query_map);
		model.addAttribute("applyDemonstration",applyDemonstration);
		query_map.put("type",1);//申请试用
		Map applyOnTrial = demonstrationStatisticsService.getApplyDemonstration(query_map);
		model.addAttribute("applyOnTrial",applyOnTrial);

		Map advice = messageService.getAdvice(query_map);
		model.addAttribute("advice",advice);

		List<Map> orderSource = orderService.getOrderSourceByCity(query_map);//地区展示
		model.addAttribute("orderSource", JSON.toJSON(orderSource));

		List<Map> orderSourceCityOrderByDesc = orderService.getOrderSourceCityOrderByDesc(query_map);//地区占比
		model.addAttribute("orderSourceCityOrderByDesc", JSON.toJSON(orderSourceCityOrderByDesc));

		List<Map> productQuantityOrderByDesc = orderService.getProductQuantityOrderByDesc(query_map);//销量排行
		model.addAttribute("productQuantityOrderByDesc", JSON.toJSON(productQuantityOrderByDesc));

		List<Map> thisWeekCompanyFollows = companyStatisticsService.getThisWeekCompanyFollows(query_map);//本周品牌关注度
		model.addAttribute("thisWeekCompanyFollows", JSON.toJSON(thisWeekCompanyFollows));

		List<Map> lastWeekCompanyFollows = companyStatisticsService.getLastWeekCompanyFollows(query_map);//上周品牌关注度
		model.addAttribute("lastWeekCompanyFollows",lastWeekCompanyFollows);


		List<Map> thisWeek = productStatisticsService.getThisWeek(query_map);//本周产品访问量
		model.addAttribute("thisWeek", JSON.toJSON(thisWeek));
		List<Map> lastWeek = productStatisticsService.getLastWeek(query_map);//上周产品访问量
		model.addAttribute("lastWeek", JSON.toJSON(lastWeek));

		List<Map> thisWeekOrder = orderService.getThisWeek(query_map);//本周订单量+销售额
		model.addAttribute("thisWeekOrder",JSON.toJSON(thisWeekOrder));

		List<Map> lastWeekOrder = orderService.getLastWeek(query_map);//上周订单量+销售额
		model.addAttribute("lastWeekOrder", JSON.toJSON(lastWeekOrder));

		List<Map> thisWeekConversionRate = orderService.getThisWeekConversionRate(query_map);//本周订单转化率
		model.addAttribute("thisWeekConversionRate", JSON.toJSON(thisWeekConversionRate));
		List<Map> lastWeekConversionRate = orderService.getLastWeekConversionRate(query_map);//上周订单转化率
		model.addAttribute("lastWeekConversionRate", JSON.toJSON(lastWeekConversionRate));

		List<Map> hospitalOrderBy = orderService.getHospitalOrderBy(query_map);//医院使用排名
		model.addAttribute("hospitalOrderBy", JSON.toJSON(hospitalOrderBy));




		return "web/main";
	}






}
