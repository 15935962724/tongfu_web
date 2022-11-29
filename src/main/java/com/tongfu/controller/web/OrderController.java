package com.tongfu.controller.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("weborder")
@RequestMapping("/web/order")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	ProductCategoryService categoryService;

	@Autowired
	CompanyService companyService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderItemService orderItemService;

	@Autowired
	OrderLogService orderLogService;

	@Autowired
	DeliveryCorpService deliveryCorpService;

	@Autowired
	private OrderInvoiceService orderInvoiceService;

	@Autowired
	private ProductStatisticsService productStatisticsService;

	@Autowired
	private ProductService productService;

	@Value("${category-upload-img}")
	private String category_upload_img;

	/**
	 * 订单列表
	 * @param model
	 * @return
	 */

	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   String sn,String username,
					   Long orderStatus,Long paymentStatus,Long shippingStatus,String startDate,String endDate ) {
		Map<String,Object> query_map = new HashMap<>(	);
		query_map.put("companyId",companyService.getCompanyId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		query_map.put("sn",sn);
		query_map.put("username",username);
		query_map.put("orderStatus",orderStatus);

		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> categoryList = (List<Map<String,Object>>) orderService.getAll(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(categoryList);
		model.addAttribute("page",pageInfo);
		model.addAttribute("sn",sn);
		model.addAttribute("username",username);
		model.addAttribute("orderStatus",orderStatus);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		return "web/order/list";
	}

	/**
	 * 增值服务订单
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param sn
	 * @param username
	 * @param orderStatus
	 * @param paymentStatus
	 * @param shippingStatus
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/addServiceOrderList")
	public String addServiceOrderList(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   String sn,String username,
					   Long orderStatus,Long paymentStatus,Long shippingStatus,String startDate,String endDate ) {
		Map<String,Object> query_map = new HashMap<>(	);
		Subject subject = SecurityUtils.getSubject();
		query_map.put("companyId",companyService.getCompanyId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		query_map.put("sn",sn);
		query_map.put("username",username);
		query_map.put("orderStatus",orderStatus);
		query_map.put("orderType",2);//增值服务订单
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> categoryList = (List<Map<String,Object>>) orderService.getAll(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(categoryList);
		model.addAttribute("page",pageInfo);
		model.addAttribute("sn",sn);
		model.addAttribute("username",username);
		model.addAttribute("orderStatus",orderStatus);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		return "web/order/addServiceOrderList";
	}






	/**
	 * 订单信息
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model model,Long id) {
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		Order order = orderService.selectByPrimaryKey(id);
		query_map.put("orderId",id);
		List<OrderItem> orderItems = orderItemService.getOrderItems(query_map);
		List<OrderLog> orderLogs = orderLogService.getAll(query_map);
		BigDecimal countPrice = orderService.getPrice(query_map);
		BigDecimal amount =  orderService.getAmount(query_map);
		Integer quantity = orderService.getQuantity(query_map);
		List<DeliveryCorp> deliveryCorps = deliveryCorpService.getAll(null);
		OrderInvoice orderInvoice = orderInvoiceService.getOrderInvoice(query_map);

		model.addAttribute("orderInvoice",orderInvoice==null?new OrderInvoice():orderInvoice);//发票信息
		model.addAttribute("deliveryCorps",deliveryCorps);
		model.addAttribute("amount",amount);//订单总价
		model.addAttribute("countPrice",countPrice);//小计
		model.addAttribute("quantity",quantity);
		model.addAttribute("orderLogs",orderLogs);
		model.addAttribute("order",order);
		model.addAttribute("orderItems",orderItems);
		return "web/order/view";
	}


	/**
	 * 增值服务订单详情页
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/addServiceOrderView")
	public String addServiceOrderView(Model model,Long id) {
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		Order order = orderService.selectByPrimaryKey(id);
		query_map.put("orderId",id);
		List<OrderItem> orderItems = orderItemService.getOrderItems(query_map);
		List<OrderLog> orderLogs = orderLogService.getAll(query_map);
		BigDecimal countPrice = orderService.getPrice(query_map);
		BigDecimal amount =  orderService.getAmount(query_map);
		Integer quantity = orderService.getQuantity(query_map);
		List<DeliveryCorp> deliveryCorps = deliveryCorpService.getAll(null);
		OrderInvoice orderInvoice = orderInvoiceService.getOrderInvoice(query_map);

		model.addAttribute("orderInvoice",orderInvoice==null?new OrderInvoice():orderInvoice);//发票信息
		model.addAttribute("deliveryCorps",deliveryCorps);
		model.addAttribute("amount",amount);//订单总价
		model.addAttribute("countPrice",countPrice);//小计
		model.addAttribute("quantity",quantity);
		model.addAttribute("orderLogs",orderLogs);
		model.addAttribute("order",order);
		model.addAttribute("orderItems",orderItems);
		return "web/order/addServiceOrderView";
	}


	/**
	 * 订单统计
//	 * @param model
	 * @return
	 */
//	@RequestMapping("/statistics")
//	public String statistics(Model model) {
//		Map<String,Object> query_map = new HashMap<>(	);
//		query_map.put("companyId",companyService.getCompanyId());
//		List<Map> orderQuantity = orderService.getOrderQuantity(query_map);
//		model.addAttribute("orderQuantity",orderQuantity);//订单量
//		List<Map> orderPrice = orderService.getOrderPrice(query_map);
//		model.addAttribute("orderPrice",orderPrice);//订单金额
//		List<Map> orderPayment = orderService.getOrderPayment(query_map);
//		model.addAttribute("orderPayment",orderPayment);//订单支付方式
//		List<Map> orderShipping = orderService.getOrderShipping(query_map);
//		model.addAttribute("orderShipping",orderShipping);//发货占比
//		String year = DateUtil.getDatetoString("YYYY",new Date());
//		query_map.put("year",year);
//		List<Map> yearOrderSums = orderService.getYearOrderSum(query_map);//产品订单量统计
//		model.addAttribute("yearOrderSums", yearOrderSums);
//
//		List<Map> productOrderProportions = orderService.productOrderProportions(query_map);//产品订单量占比
//		model.addAttribute("productOrderProportions", productOrderProportions);
//
//
//		return "web/order/statistics";
//	}

	/**
	 * 转化率数据统计
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model) {

		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		query_map.put("companyid",companyService.getCompanyId());
		List<Product> products = productService.getAll(query_map);
		model.addAttribute("products",products);
		List<Map> productStatisicDemonstration = productStatisticsService.getProductStatisicDemonstration(query_map);
		model.addAttribute("productStatisicDemonstration", productStatisicDemonstration);//产品浏览数与申请演示转换率分析
		List<Map> productDemonstration = productStatisticsService.getProductDemonstration(query_map);
		model.addAttribute("productDemonstration", productDemonstration);//产品申请演示与申请试用转换率分析
		List<Map> productStatisicOrder = productStatisticsService.getProductStatisicOrder(query_map);
		model.addAttribute("productStatisicOrder", productStatisicOrder);//产品申请演示与下单转换率
		List<Map> productStatisicCompany = productStatisticsService.getProductStatisicCompany(query_map);
		model.addAttribute("productStatisicCompany", productStatisicCompany);//微官网转换率

		return "web/order/orderConversionRate";
	}

	/**
	 * 转换率数据统计 自定义查询
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/queryStatistics", method = RequestMethod.POST)
	@ResponseBody
	public String queryStatistics(Integer type, Long productId, String startDate,String endDate){
		JSONObject jsonObject = new JSONObject();
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		query_map.put("productId",productId);
		if (type==1){
			List<Map> productStatisicDemonstration = productStatisticsService.getProductStatisicDemonstration(query_map);
			jsonObject.put("productStatisicDemonstration", productStatisicDemonstration);//产品浏览数与申请演示转换率分析
		}
		if (type==2){
			List<Map> productDemonstration = productStatisticsService.getProductDemonstration(query_map);
			jsonObject.put("productDemonstration", productDemonstration);//产品申请演示与申请试用转换率分析
		}
		if (type==3){
			List<Map> productStatisicOrder = productStatisticsService.getProductStatisicOrder(query_map);
			jsonObject.put("productStatisicOrder", productStatisicOrder);//产品申请演示与下单转换率
		}
		if (type==4){
			List<Map> productStatisicCompany = productStatisticsService.getProductStatisicCompany(query_map);
			jsonObject.put("productStatisicCompany", productStatisicCompany);//微官网转换率
		}
		return ResultUtil.success(jsonObject);
	}



	/**
	 * 全国销售额统计
	 * @param model
	 * @return
	 */
	@RequestMapping("/salesStatistics")
	public String salesStatistics(Model model) {
		return "web/order/salesStatistics";
	}

	@RequestMapping("/salesAnalysis")
	public String salesAnalysis(Model model) {
		return "web/order/salesAnalysis";
	}




	/**
	 * 产品订单量占比
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/productOrderProportions", method = RequestMethod.POST)
	@ResponseBody
	public String productOrderProportions(String startDate,String endDate){
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		List<Map> productOrderProportions = orderService.productOrderProportions(query_map);//产品订单量占比
		return ResultUtil.success(productOrderProportions);
	}



	/**
	 * 订单量按年统计
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/yearOrderSum", method = RequestMethod.POST)
	@ResponseBody
	public String yearOrderSum(Long year){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		query_map.put("year",year);
		List<Map> yearOrderSums = orderService.getYearOrderSum(query_map);
		return ResultUtil.success(yearOrderSums);
	}


	/**
	 * 上传报告
	 * @param files
	 * @param id
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/uploadCaseReport")
	@ResponseBody
	public String uploadCaseReport(@RequestParam("file") MultipartFile[] files, Long id, HttpServletRequest request) throws IOException {
		Admin admin = adminService.getCurrent();
		System.out.println("id:"+id);
		Integer i = 1;
		JSONArray jsonArray = new JSONArray();
		for (MultipartFile file : files) {
			JSONObject jsonObject = new JSONObject();
			//上传到远程服务器
			String path = FileUtils.upload(file,"/caseReport/");
			jsonObject.put("index",i);
			String caseReport_url =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			jsonObject.put("path",caseReport_url);
			jsonObject.put("name",file.getOriginalFilename());
			jsonArray.add(jsonObject);
			i++;

		}
		System.out.println("上传病例结果:"+jsonArray);
		OrderItem orderItem = new OrderItem();
		orderItem.setId(id);
		orderItem.setShippedQuantity(files.length);
		orderItem.setCompanyCaseReport(jsonArray.toString());
		int updateByPrimaryKeySelective = orderItemService.updateByPrimaryKeySelective(orderItem);
		boolean fals = updateByPrimaryKeySelective>0;
		OrderItem orderItem1 = orderItemService.selectByPrimaryKey(id);
		if (fals){
			Order order = orderService.selectByPrimaryKey(orderItem1.getOrders());
			order.setShippingStatus(2);
			order.setShippingMethodName("线上发货");
			order.setShippingMethod(1L);
			int byPrimaryKeySelective = orderService.updateByPrimaryKeySelective(order);
			fals = byPrimaryKeySelective>0;
		}
		if (fals){
			OrderLog orderLog = new OrderLog();
			orderLog.setCreateDate(new Date());
			orderLog.setModifyDate(new Date());
			orderLog.setContent(admin.getName()+"上传病例报告");
			orderLog.setOperator(admin.getUsername());
			orderLog.setType(1);
			orderLog.setOrders(orderItem1.getOrders());
			orderLog.setIsDeleted(false);
			orderLog.setOperatorName(admin.getName());
			int insertSelective = orderLogService.insertSelective(orderLog);//订单日志表完成
			fals = insertSelective>0;
		}
		if (fals){
			return ResultUtil.success();
		}
		return ResultUtil.error("上传病例失败!");

	}


}
