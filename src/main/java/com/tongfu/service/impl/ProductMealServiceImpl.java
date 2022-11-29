package com.tongfu.service.impl;

import com.tongfu.dao.AreaDao;
import com.tongfu.dao.ProductMealDao;
import com.tongfu.entity.Area;
import com.tongfu.entity.ProductMeal;
import com.tongfu.service.AreaService;
import com.tongfu.service.ProductMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductMealServiceImpl implements ProductMealService {


	@Autowired
	ProductMealDao productMealDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return productMealDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProductMeal record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductMeal record) {
		return productMealDao.insertSelective(record);
	}

	@Override
	public ProductMeal selectByPrimaryKey(Long id) {
		return productMealDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductMeal record) {
		return productMealDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductMeal record) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getAll(Map<String, Object> query_map) {
		return productMealDao.getAll(query_map);
	}

	@Override
	public int updateProductMeal(Map query_map) {
		return productMealDao.updateProductMeal(query_map);
	}
}
