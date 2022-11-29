package com.tongfu.dao;

import com.tongfu.entity.ProductPurchase;
import com.tongfu.entity.ProductVerify;

import java.util.List;
import java.util.Map;

public interface ProductVerifyDao {
    int insert(ProductVerify record);

    int insertSelective(ProductVerify record);

    List<ProductVerify> getAll(Map<String,Object> query_map);

    List<Map<String, Object>> getProductVisit(Map<String,Object> query_map);

}