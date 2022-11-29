package com.tongfu.dao;

import com.tongfu.entity.Aboutus;

import java.util.List;
import java.util.Map;

public interface AboutusDao {
    int deleteByPrimaryKey(Long id);

    int insert(Aboutus record);

    int insertSelective(Aboutus record);

    Aboutus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Aboutus record);

    int updateByPrimaryKeyWithBLOBs(Aboutus record);

    int updateByPrimaryKey(Aboutus record);

    List<Aboutus> getAll(Map query_map);
}