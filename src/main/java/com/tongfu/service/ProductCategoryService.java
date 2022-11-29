package com.tongfu.service;

import com.tongfu.entity.Admin;
import com.tongfu.entity.ProductCategory;

import java.util.Collection;
import java.util.Map;

public interface ProductCategoryService {

	int deleteByPrimaryKey(Long id);

	int insert(ProductCategory record);

	int insertSelective(ProductCategory record);

	ProductCategory selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProductCategory record);

	int updateByPrimaryKey(ProductCategory record);

	Collection<ProductCategory> getAll(Map<String,Object> query_map);

	ProductCategory getPrentProductCategory(Long id);



}