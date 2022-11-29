package com.tongfu.service;

import com.tongfu.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("amountService")
public class AmountService {

	@Autowired
	OrderService orderService;

	public  BigDecimal getAmounts(Long id){
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("orderId",id);
		BigDecimal amount = orderService.getAmount(query_map);
		return amount;
	}


}