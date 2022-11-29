package com.tongfu.service.impl;

import com.tongfu.dao.OrderDao;
import com.tongfu.dao.OrderItemDao;
import com.tongfu.entity.OrderItem;
import com.tongfu.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class OrderItemServiceImpl implements OrderItemService {


	@Autowired
	OrderDao orderDao;


	@Autowired
	OrderItemDao orderItemDao;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(OrderItem record) {
		return 0;
	}

	@Override
	public int insertSelective(OrderItem record) {
		return 0;
	}

	@Override
	public OrderItem selectByPrimaryKey(Long id) {
		return orderItemDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderItem record) {
		return orderItemDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(OrderItem record) {
		return 0;
	}

	@Override
	public List<OrderItem> getOrderItems(Map<String, Object> query_map) {
		return orderItemDao.getOrderItems(query_map);
	}

	@Override
	public List<Map> companySales(Map query_map) {
		return orderItemDao.companySales(query_map);
	}

	@Override
	public List<Map> companyVolume(Map query_map) {
		return orderItemDao.companyVolume(query_map);
	}

	@Override
	public List<Integer> companySalesByCompanyName(Map query_map) {
		return orderItemDao.companySalesByCompanyName(query_map);
	}

	@Override
	public List<BigDecimal> companyVolumeByCompanyName(Map query_map) {
		return orderItemDao.companyVolumeByCompanyName(query_map);
	}

	@Override
	public List<Map> getYear(Map query_map) {
		return orderItemDao.getYear(query_map);
	}

	@Override
	public List<Map> getProductSales(Map query_map) {
		return orderItemDao.getProductSales(query_map);
	}

	@Override
	public List<Map> getPaymentYear(Map query_map) {
		return orderItemDao.getPaymentYear(query_map);
	}

	@Override
	public List<Integer> companySalesByProductName(Map query_map) {
		return orderItemDao.companySalesByProductName(query_map);
	}

	@Override
	public List<Map> getCountProductStatistics(Map query_map) {
		return orderItemDao.getCountProductStatistics(query_map);
	}

	@Override
	public List<Map> getCountProductPruchaseNames(Map query_map) {
		return orderItemDao.getCountProductPruchaseNames(query_map);
	}


}
