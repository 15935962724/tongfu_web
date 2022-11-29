package com.tongfu.service.impl;

import com.tongfu.dao.ProductStatisticsDao;
import com.tongfu.entity.ProductStatistics;
import com.tongfu.service.ProductStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductStatisticsServiceImpl implements ProductStatisticsService {

	@Autowired
	ProductStatisticsDao productStatisticsDao;

	@Override
	public int insert(ProductStatistics record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductStatistics record) {
		return 0;
	}

	@Override
	public int getMonthCountProduct(Map<String, Object> query_map) {
		return productStatisticsDao.getMonthCountProduct(query_map);
	}

	@Override
	public int getWeekCountProduct(Map<String, Object> query_map) {
		return productStatisticsDao.getWeekCountProduct(query_map);
	}

	@Override
	public int getDayCountProduct(Map<String, Object> query_map) {
		return productStatisticsDao.getDayCountProduct(query_map);
	}

	@Override
	public List<Map<String, Object>> getProductStatisics(Map<String, Object> query_map) {
		return productStatisticsDao.getProductStatisics(query_map);
	}

	@Override
	public List<Map<String, Object>> getProductCategoryStatistics(Map<String, Object> query_map) {
		return productStatisticsDao.getProductCategoryStatistics(query_map);
	}

	@Override
	public List<Map<String, Object>> getProductCompanyStatistics(Map<String, Object> query_map) {
		return productStatisticsDao.getProductCompanyStatistics(query_map);
	}

	@Override
	public List<Map<String, Object>> getProductProvinceStatistics(Map<String, Object> query_map) {
		return productStatisticsDao.getProductProvinceStatistics(query_map);
	}

	@Override
	public List<Map<String, Object>> getAll(Map<String, Object> query_map) {
		return productStatisticsDao.getAll(query_map);
	}

	@Override
	public List<Map<String, Object>> getProductstatisicHistogram(Map<String, Object> query_map) {
		return productStatisticsDao.getProductstatisicHistogram(query_map);
	}

	@Override
	public List<Map> getProductstatisicMap(Map query_map) {
		return productStatisticsDao.getProductstatisicMap(query_map);
	}

	@Override
	public List<Map> getIndexProductstatisicMap(Map query_map) {
		return productStatisticsDao.getIndexProductstatisicMap(query_map);
	}

	@Override
	public List<Map> getThisWeek(Map query_map) {
		return productStatisticsDao.getThisWeek(query_map);
	}

	@Override
	public List<Map> getLastWeek(Map query_map) {
		return productStatisticsDao.getLastWeek(query_map);
	}

	@Override
	public List<Map> getProductStatisic(Map query_map) {
		return productStatisticsDao.getProductStatisic(query_map);
	}

	@Override
	public List<Map> getProductStatisicProportion(Map query_map) {
		return productStatisticsDao.getProductStatisicProportion(query_map);
	}

	@Override
	public List<Map> getProductStatisicDemonstration(Map query_map) {
		return productStatisticsDao.getProductStatisicDemonstration(query_map);
	}

	@Override
	public List<Map> getProductDemonstration(Map query_map) {
		return productStatisticsDao.getProductDemonstration(query_map);
	}

	@Override
	public List<Map> getProductStatisicOrder(Map query_map) {
		return productStatisticsDao.getProductStatisicOrder(query_map);
	}

	@Override
	public List<Map> getProductStatisicCompany(Map query_map) {
		return productStatisticsDao.getProductStatisicCompany(query_map);
	}
}
