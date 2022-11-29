package com.tongfu.service;

import com.tongfu.entity.Purchase;

import java.util.List;
import java.util.Map;

public interface PurchaseService {
    int deleteByPrimaryKey(Long id);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);
    
    List<Purchase> getAll(Map<String, Object> query_map) ;

    List<Purchase> getParentPurchase(Map<String, Object> query_map) ;

    List<Map<String,Object>> selectGuige(Map<String,Object> map);

}