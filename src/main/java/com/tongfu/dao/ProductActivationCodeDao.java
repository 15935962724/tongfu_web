package com.tongfu.dao;

import com.tongfu.entity.ProductActivationCode;

import java.util.List;
import java.util.Map;

public interface ProductActivationCodeDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductActivationCode record);

    int insertSelective(ProductActivationCode record);

    ProductActivationCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductActivationCode record);

    int updateByPrimaryKey(ProductActivationCode record);

    int insertSelectiveMap(Map<String,Object> map);

    List<Map<String,Object>> replenishmentStatus(Map<String,Object> map);

    List<ProductActivationCode> getNotSale(Map query_map);

    int deleteProductActivationCode(Long prouctPurchaseId);

}