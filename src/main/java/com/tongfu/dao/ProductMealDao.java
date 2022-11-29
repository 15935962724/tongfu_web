package com.tongfu.dao;

import com.tongfu.entity.ProductMeal;

import java.util.List;
import java.util.Map;

public interface ProductMealDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductMeal record);

    int insertSelective(ProductMeal record);

    ProductMeal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductMeal record);

    int updateByPrimaryKey(ProductMeal record);

    List<Map<String,Object>> getAll(Map<String,Object> query_map);

    int updateProductMeal(Map query_map);
}