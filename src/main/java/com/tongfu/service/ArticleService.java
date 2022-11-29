package com.tongfu.service;

import com.tongfu.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    int deleteByPrimaryKey(Long id);

    int deleteMap(Map query_map);

    int insert(Article record);

    int insertMap(Map query_map);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> getAll(Map<String,Object> query_map);

}