package com.tongfu.dao;

import com.tongfu.entity.OrderLog;

import java.util.List;
import java.util.Map;

public interface OrderLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderLog record);

    int insertSelective(OrderLog record);

    OrderLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderLog record);

    int updateByPrimaryKey(OrderLog record);

    List<OrderLog> getAll(Map<String,Object> query_map);

}