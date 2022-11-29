package com.tongfu.dao;

import com.tongfu.entity.ArticleStatistics;

import java.util.List;
import java.util.Map;

public interface ArticleStatisticsMapper {
    int insert(ArticleStatistics record);

    int insertSelective(ArticleStatistics record);

    List<Map> getMemberSearch(Map query_map);
}