package com.tongfu.dao;

import com.tongfu.entity.ShippingItem;

import java.util.Map;

public interface ShippingItemDao {
    int insert(ShippingItem record);

    int insertSelective(ShippingItem record);

    int insertSelectiveMap(Map<String,Object> map);

}