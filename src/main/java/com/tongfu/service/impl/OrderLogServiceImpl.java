package com.tongfu.service.impl;

import com.tongfu.dao.OrderDao;
import com.tongfu.dao.OrderItemDao;
import com.tongfu.dao.OrderLogDao;
import com.tongfu.entity.Order;
import com.tongfu.entity.OrderItem;
import com.tongfu.entity.OrderLog;
import com.tongfu.service.OrderLogService;
import com.tongfu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class OrderLogServiceImpl implements OrderLogService {


	@Autowired
	OrderLogDao orderLogDao;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(OrderLog record) {
		return 0;
	}

	@Override
	public int insertSelective(OrderLog record) {
		return orderLogDao.insertSelective(record);
	}

	@Override
	public OrderLog selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(OrderLog record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(OrderLog record) {
		return 0;
	}

	@Override
	public List<OrderLog> getAll(Map<String, Object> query_map) {
		return orderLogDao.getAll(query_map);
	}
}
