package com.tongfu.service;

import com.tongfu.entity.ProductVerify;

import java.util.List;
import java.util.Map;

public interface ProductVerifyService {
    int insert(ProductVerify record);

    int insertSelective(ProductVerify record);

    List<ProductVerify> getAll(Map<String, Object> query_map);

    List<Map<String, Object>> getProductVisit(Map<String, Object> query_map);
    
}