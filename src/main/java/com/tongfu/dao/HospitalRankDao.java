package com.tongfu.dao;

import com.tongfu.entity.HospitalRank;

import java.util.List;
import java.util.Map;

public interface HospitalRankDao {
    int deleteByPrimaryKey(Long id);

    int insert(HospitalRank record);

    int insertSelective(HospitalRank record);

    HospitalRank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HospitalRank record);

    int updateByPrimaryKey(HospitalRank record);

    List<HospitalRank> getAll(Map<String,Object> query_map);

}