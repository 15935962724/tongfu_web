package com.tongfu.dao;

import com.tongfu.entity.Hospital;
import com.tongfu.entity.PaymentMethod;

import java.util.List;
import java.util.Map;

public interface HospitalDao {
    int deleteByPrimaryKey(Long id);

    int insert(Hospital record);

    int insertSelective(Hospital record);

    Hospital selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKey(Hospital record);

    List<Map<String,Object>> getAll(Map<String, Object> query_map);
}