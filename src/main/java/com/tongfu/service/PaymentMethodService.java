package com.tongfu.service;

import com.tongfu.entity.Ad;
import com.tongfu.entity.PaymentMethod;

import java.util.List;
import java.util.Map;

public interface PaymentMethodService {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentMethod record);

    int insertSelective(PaymentMethod record);

    PaymentMethod selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentMethod record);

    int updateByPrimaryKey(PaymentMethod record);

    List<PaymentMethod> getAll(Map<String,Object> query_map);

}