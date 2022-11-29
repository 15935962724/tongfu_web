package com.tongfu.dao;

import com.tongfu.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getAll(Map<String,Object> query_map);

    List<Map<String,Object>> getAllMap(Map<String,Object> query_map);

    int getCountProduct(Map<String,Object> query_map);

    List<Map<String,Object>> getProductStatistics(Map<String,Object> query_map);

    int nextTableId(String tableName);

    List<Map<String, Object>> getProductSalesStatistics(Map<String, Object> query_map);

    List<Map> getAuthenticationStatistics(Map query_map);

}