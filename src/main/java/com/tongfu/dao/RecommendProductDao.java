package com.tongfu.dao;

import com.tongfu.entity.RecommendProduct;

import java.util.List;
import java.util.Map;

public interface RecommendProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(RecommendProduct record);

    int insertSelective(RecommendProduct record);

    RecommendProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecommendProduct record);

    int updateByPrimaryKeyWithBLOBs(RecommendProduct record);

    int updateByPrimaryKey(RecommendProduct record);

    List<Map<String,Object>> getAll(Map<String,Object> query_map);

    List<Map> getIsRecommendProducts(Map query_map);
}