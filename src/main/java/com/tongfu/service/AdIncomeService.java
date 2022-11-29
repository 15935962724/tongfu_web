package com.tongfu.service;

import com.tongfu.entity.AdIncome;

import java.util.List;
import java.util.Map;

public interface AdIncomeService
{
    int deleteByPrimaryKey(Long id);

    int insert(AdIncome record);

    int insertSelective(AdIncome record);

    AdIncome selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdIncome record);

    int updateByPrimaryKey(AdIncome record);

    Map getMonthCountAdPrice(Map<String, Object> query_map);

    Map getWeekCountAdPrice(Map<String, Object> query_map);

    Map getDayCountAdPrice(Map<String, Object> query_map);

    List<Map> getMonthData(Map<String, Object> query_map);

    List<Map> getWeekData(Map query_map);

    Map getWeekCountData(Map query_map);


}