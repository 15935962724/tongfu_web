package com.tongfu.dao;

import com.tongfu.entity.Ad;

import java.util.List;
import java.util.Map;

public interface AdDao {
    int deleteByPrimaryKey(Long id);

    int insert(Ad record);

    int insertSelective(Ad record);

    Ad selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKeyWithBLOBs(Ad record);

    int updateByPrimaryKey(Ad record);

    List<Map<String,Object>> getAll(Map<String,Object> query_map);

    int toExamineAdCouont(Map<String,Object> query_map);

    int toOverdueadCouont(Map<String,Object> query_map);

    int toShowAdCouont(Map<String,Object> query_map);

}