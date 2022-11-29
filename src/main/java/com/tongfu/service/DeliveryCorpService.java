package com.tongfu.service;

import com.tongfu.entity.DeliveryCorp;

import java.util.List;
import java.util.Map;

public interface DeliveryCorpService {

    int deleteByPrimaryKey(Long id);

    int insert(DeliveryCorp record);

    int insertSelective(DeliveryCorp record);

    DeliveryCorp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeliveryCorp record);

    int updateByPrimaryKey(DeliveryCorp record);

    List<DeliveryCorp> getAll(Map<String, Object> query_map);


}