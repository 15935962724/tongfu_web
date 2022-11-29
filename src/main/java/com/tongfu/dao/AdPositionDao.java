package com.tongfu.dao;

import com.tongfu.entity.AdPosition;

import java.util.List;
import java.util.Map;

public interface AdPositionDao {
    int deleteByPrimaryKey(Long id);

    int insert(AdPosition record);

    int insertSelective(AdPosition record);

    AdPosition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdPosition record);

    int updateByPrimaryKey(AdPosition record);

    List<AdPosition> getAll(Map<String,Object> query_map);
}