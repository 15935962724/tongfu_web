package com.tongfu.service;

import com.tongfu.entity.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

    int getMonthCountProductMessage(Map<String,Object> query_map);

    int getWeekCountProductMessage(Map<String,Object> query_map);

    int getDayCountProductMessage(Map<String,Object> query_map);

    List<Map<String,Object>> getAll(Map<String,Object> query_map);

    List<Map<String,Object>> messageStatisticList(Map<String,Object> query_map);

    Map getAdvice(Map query_map);


}