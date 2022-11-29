package com.tongfu.service;

import com.tongfu.entity.CompanyStatistics;

import java.util.List;
import java.util.Map;

public interface CompanyStatisticsService {

    int insert(CompanyStatistics record);

    int insertSelective(CompanyStatistics record);

    List<Map> getThisWeekCompanyFollows(Map query_map);//本周品牌关注度

    List<Map> getLastWeekCompanyFollows(Map query_map);//上周品牌关注度


}