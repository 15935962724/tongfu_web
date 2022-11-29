package com.tongfu.service;

import com.tongfu.entity.MemberPointWorkshop;

import java.util.List;
import java.util.Map;

public interface MemberPointWorkshopService {
    int deleteByPrimaryKey(Long id);

    int insert(MemberPointWorkshop record);

    int insertSelective(MemberPointWorkshop record);

    MemberPointWorkshop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberPointWorkshop record);

    int updateByPrimaryKey(MemberPointWorkshop record);

    List<Map<String,Object>> getAll(Map<String, Object> query_map);
}