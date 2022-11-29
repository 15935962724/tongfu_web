package com.tongfu.controller.web;

import com.tongfu.entity.Order;
import com.tongfu.entity.OrderItem;
import com.tongfu.entity.Shipping;
import com.tongfu.entity.ShippingItem;
import com.tongfu.service.AreaService;
import com.tongfu.service.OrderItemService;
import com.tongfu.service.OrderService;
import com.tongfu.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("webshipping")
@RequestMapping("/web/shipping")
public class ShippingController {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	private AreaService areaService;

	@Autowired
	private ShippingService shippingService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;




	//发货
	@PostMapping("/save")
	@ResponseBody
	public Object updateProduct(@RequestBody String params) {
		return  shippingService.insertSelective(params);
	}

//
//	/**
//	 * 发货操作
//	 * @param shipping
//	 * @param logistics
//	 * @return
//	 */
//	@PostMapping("/save")
//	public String save(Shipping shipping,Boolean logistics) {
//		if (logistics){
//			shipping.setModifyDate(new Date());
//			shipping.setCreateDate(new Date());
//			shipping.setIsDeleted(false);
//			String sn = "FHD"+System.currentTimeMillis();
//			shippingService.insertSelective(shipping);
//
//		}
//		Order order = orderService.selectByPrimaryKey(shipping.getOrders());
//		order.setShippingStatus(2);
//		orderService.updateByPrimaryKeySelective(order);
//		Map query_map = new HashMap();
//		query_map.put("orderId",order.getId());
//		List<OrderItem>  orderItems = orderItemService.getOrderItems(query_map);
//		for (OrderItem orderItem : orderItems) {
//			ShippingItem shippingItem = new ShippingItem();
//			shippingItem.setCreateDate(new Date());
//			shippingItem.setModifyDate(new Date());
//			shippingItem.setIsDeleted(false);
//			shippingItem.setName("");
//			shippingItem.setSn("");
//			shippingItem.setQuantity(orderItem.getQuantity());
//			shippingItem.setShipping(shipping.getId());
//
//		}
//
//
//		return "redirect:web/order/list";
//	}
//
//




}
