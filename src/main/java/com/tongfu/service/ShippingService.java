package com.tongfu.service;

import com.tongfu.entity.DeliveryCorp;
import com.tongfu.entity.Shipping;

import java.util.List;
import java.util.Map;

public interface ShippingService {

    int deleteByPrimaryKey(Long id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    String insertSelective(String params);

    Shipping selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);


}