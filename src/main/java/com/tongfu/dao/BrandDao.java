package com.tongfu.dao;

import com.tongfu.entity.Brand;

import java.util.List;
import java.util.Map;

public interface BrandDao {
    int deleteByPrimaryKey(Long id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKeyWithBLOBs(Brand record);

    int updateByPrimaryKey(Brand record);

    List<Brand> getAll(Map<String, Object> query_map);

}