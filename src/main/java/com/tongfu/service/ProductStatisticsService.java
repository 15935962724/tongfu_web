package com.tongfu.service;

import com.tongfu.entity.ProductStatistics;

import java.util.List;
import java.util.Map;

public interface ProductStatisticsService {

	int insert(ProductStatistics record);

	int insertSelective(ProductStatistics record);

	int getMonthCountProduct(Map<String,Object> query_map);

	int getWeekCountProduct(Map<String,Object> query_map);

	int getDayCountProduct(Map<String,Object> query_map);

	List<Map<String,Object>> getProductStatisics(Map<String,Object> query_map);

	List<Map<String,Object>> getProductCategoryStatistics(Map<String,Object> query_map);

	List<Map<String,Object>> getProductCompanyStatistics(Map<String,Object> query_map);

	List<Map<String,Object>> getProductProvinceStatistics(Map<String,Object> query_map);

	List<Map<String,Object>> getAll(Map<String,Object> query_map);

	List<Map<String,Object>> getProductstatisicHistogram(Map<String,Object> query_map);

	List<Map> getProductstatisicMap(Map query_map);

	List<Map> getIndexProductstatisicMap(Map query_map);

	List<Map> getThisWeek(Map query_map);//查询本周访问量

	List<Map> getLastWeek(Map query_map);//查询上周访问量

	List<Map> getProductStatisic(Map query_map);//产品访问量

	List<Map> getProductStatisicProportion(Map query_map);//产品访问量占比

	List<Map> getProductStatisicDemonstration(Map query_map);//产品浏览量数与申请演示转换率分析

	List<Map> getProductDemonstration(Map query_map);//产品申请演示与申请试用转换率分析

	List<Map> getProductStatisicOrder(Map query_map);//产品申请演示与下单转换率

	List<Map> getProductStatisicCompany(Map query_map);//微官网转换率


}