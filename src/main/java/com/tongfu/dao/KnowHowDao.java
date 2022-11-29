package com.tongfu.dao;

import com.tongfu.entity.KnowHow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface KnowHowDao {
    int deleteByPrimaryKey(Long id);

    int insert(KnowHow record);

    int insertSelective(KnowHow record);

    KnowHow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KnowHow record);

    int updateByPrimaryKey(KnowHow record);

    List<KnowHow> getAll(Map<String,Object> query_map);
}