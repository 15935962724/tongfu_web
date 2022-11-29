package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.DeliveryCorp;
import com.tongfu.entity.Order;
import com.tongfu.entity.OrderItem;
import com.tongfu.entity.OrderLog;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/order")
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
					   Long orderStatus,Long paymentStatus,Long shippingStatus,
					   String startDate,String endDate ,Long orderType,Long companyId ) {
		Map<String,Object> query_map = new HashMap<>(	);
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
		query_map.put("orderType",orderType);
		query_map.put("companyId",companyId);
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> categoryList = (List<Map<String,Object>>) orderService.getAll(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(categoryList);
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);
		model.addAttribute("page",pageInfo);
		model.addAttribute("sn",sn);
		model.addAttribute("username",username);
		model.addAttribute("orderStatus",orderStatus);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("orderType",orderType);
		model.addAttribute("companyId",companyId);
		return "admin/order/list";
	}

	/**
	 * 订单信息
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model model,Long id) {
		Map<String,Object> query_map = new HashMap<>(	);
		Subject subject = SecurityUtils.getSubject();
		if (!subject.hasRole("超级管理员")){
			query_map.put("companyId",companyService.getCompanyId());
		}

		Order order = orderService.selectByPrimaryKey(id);
		query_map.put("orderId",id);
		List<OrderItem> orderItems = orderItemService.getOrderItems(query_map);
		List<OrderLog> orderLogs = orderLogService.getAll(query_map);
		BigDecimal countPrice = orderService.getPrice(query_map);
		BigDecimal amount =  orderService.getAmount(query_map);
		Integer quantity = orderService.getQuantity(query_map);
		List<DeliveryCorp> deliveryCorps = deliveryCorpService.getAll(null);
		model.addAttribute("deliveryCorps",deliveryCorps);
		model.addAttribute("amount",amount);//订单总价
		model.addAttribute("countPrice",countPrice);//小计
		model.addAttribute("quantity",quantity);
		model.addAttribute("orderLogs",orderLogs);
		model.addAttribute("order",order);
		model.addAttribute("orderItems",orderItems);
		return "admin/order/view";
	}

	/**
	 * 订单统计
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   Long companyId,String orderStatus
							/* String startDate,String endDate*/
	) {
		Map<String,Object> query_map = new HashMap<>(	);
//		if (startDate!=null&&!startDate.equals("")){
//			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
//			query_map.put("startDate",start_Date);
//		}
//		if (endDate!=null&&!endDate.equals("")){
//			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
//			query_map.put("endDate",end_Date);
//		}

		query_map.put("companyId",companyId);
		query_map.put("orderStatus",orderStatus);
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> categoryList = (List<Map<String,Object>>) orderService.getStatistics(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(categoryList);
		model.addAttribute("page",pageInfo);
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);
//		model.addAttribute("startDate",startDate);
//		model.addAttribute("endDate",endDate);
		model.addAttribute("orderStatus",orderStatus);
		model.addAttribute("companyId",companyId);
		return "admin/order/statistics";
	}
}
