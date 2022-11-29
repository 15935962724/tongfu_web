package com.tongfu.dao;

import com.tongfu.entity.KnowhowArticle;

import java.util.List;
import java.util.Map;

public interface KnowhowArticleDao {
    int deleteByPrimaryKey(Long id);

    int insert(KnowhowArticle record);

    int insertSelective(KnowhowArticle record);

    KnowhowArticle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KnowhowArticle record);

    int updateByPrimaryKeyWithBLOBs(KnowhowArticle record);

    int updateByPrimaryKey(KnowhowArticle record);

    List<KnowhowArticle> getAll(Map query_map);
}