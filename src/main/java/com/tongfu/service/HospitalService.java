package com.tongfu.service;

import com.tongfu.entity.Hospital;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    int deleteByPrimaryKey(Long id);

    int insert(Hospital record);

    int insertSelective(Hospital record);

    Hospital selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKey(Hospital record);

    List<Map<String,Object>> getAll(Map<String, Object> query_map);
}