package com.tongfu.service;

import com.tongfu.entity.KnowhowCode;

import java.util.List;
import java.util.Map;

public interface KnowhowCodeService {
    int deleteByPrimaryKey(Long id);

    int insert(KnowhowCode record);

    int insertSelective(KnowhowCode record);

    int insertMap(Map<String,Object> map);

    KnowhowCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KnowhowCode record);

    int updateByPrimaryKey(KnowhowCode record);

    List<KnowhowCode> getAll(Map<String,Object> query_map);

    List<Map<String,Object>> getAllMap(Map<String,Object> query_map);


}