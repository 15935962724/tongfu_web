package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.ProductCategoryDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Admin;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.AdService;
import com.tongfu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;


@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	ProductCategoryDao categoryDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return categoryDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProductCategory record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductCategory record) {
		return categoryDao.insertSelective(record);
	}

	@Override
	public ProductCategory selectByPrimaryKey(Long id) {
		return categoryDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductCategory record) {
		return categoryDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductCategory record) {
		return 0;
	}

	@Override
	public Collection<ProductCategory> getAll(Map<String, Object> query_map) {
		return categoryDao.getAll(query_map);
	}

	@Override
	public ProductCategory getPrentProductCategory(Long id) {
		ProductCategory productCategory = categoryDao.selectByPrimaryKey(id);
		if (productCategory.getParent()!=null){
			return getPrentProductCategory(productCategory.getParent());
		}
		return productCategory;
	}
}
