package com.tongfu.service;

import com.tongfu.entity.Specifications;

import java.util.List;
import java.util.Map;

public interface SpecificationsService {

    int insert(Specifications record);

    int insertSelective(Specifications record);

    List<Specifications> getAll(Map<String,Object> query_map);

    Specifications selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Specifications record);

    int deleteByPrimaryKey(Long id);

    int deleteByProduct(Long productId);

    int insertMap(Map<String, Object> map);


	
}