package com.tongfu.service;

import com.tongfu.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderItemService {

	int deleteByPrimaryKey(Long id);

	int insert(OrderItem record);

	int insertSelective(OrderItem record);

	OrderItem selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderItem record);

	int updateByPrimaryKey(OrderItem record);

	List<OrderItem> getOrderItems(Map<String,Object> query_map);

	List<Map> companySales(Map query_map);

	List<Map> companyVolume(Map query_map);

	List<Integer> companySalesByCompanyName(Map query_map);

	List<BigDecimal> companyVolumeByCompanyName(Map query_map);

	List<Map> getYear(Map query_map);

	List<Map> getProductSales(Map query_map);

	List<Map> getPaymentYear(Map query_map);

	List<Integer> companySalesByProductName(Map query_map);

	List<Map> getCountProductStatistics(Map query_map);

	List<Map> getCountProductPruchaseNames(Map query_map);


}