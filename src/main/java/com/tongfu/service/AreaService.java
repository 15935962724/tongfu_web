package com.tongfu.service;

import com.tongfu.entity.Area;

import java.util.List;
import java.util.Map;

public interface AreaService {
    int deleteByPrimaryKey(Long id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKeyWithBLOBs(Area record);

    int updateByPrimaryKey(Area record);

    List<Area> getAll(Map<String,Object> query_map);

    Map parentArea(Long id);



}