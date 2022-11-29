package com.tongfu.dao;

import com.tongfu.entity.Exhibition;

import java.util.List;
import java.util.Map;

public interface ExhibitionDao {
    int deleteByPrimaryKey(Long id);

    int insert(Exhibition record);

    int insertSelective(Exhibition record);

    Exhibition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Exhibition record);

    int updateByPrimaryKeyWithBLOBs(Exhibition record);

    int updateByPrimaryKey(Exhibition record);

    List<Map> getAll(Map<String,Object> query_map);

    Map getExhibitionMap (Map query_map);

    Integer hitsExhibitionCouont(Map query_map);
}