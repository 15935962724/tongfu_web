package com.tongfu.dao;

import com.tongfu.entity.HospitalCategory;

import java.util.List;
import java.util.Map;

public interface HospitalCategoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(HospitalCategory record);

    int insertSelective(HospitalCategory record);

    HospitalCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HospitalCategory record);

    int updateByPrimaryKey(HospitalCategory record);

    List<HospitalCategory> getAll(Map<String,Object> query_map);
}