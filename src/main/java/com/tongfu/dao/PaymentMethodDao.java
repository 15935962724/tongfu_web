package com.tongfu.dao;

import com.tongfu.entity.PaymentMethod;

import java.util.List;
import java.util.Map;

public interface PaymentMethodDao {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentMethod record);

    int insertSelective(PaymentMethod record);

    PaymentMethod selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentMethod record);

    int updateByPrimaryKey(PaymentMethod record);

    List<PaymentMethod> getAll(Map<String, Object> query_map);
}