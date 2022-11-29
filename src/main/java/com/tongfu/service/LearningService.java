package com.tongfu.service;

import com.tongfu.entity.Learning;

import java.util.List;
import java.util.Map;

public interface LearningService {
    int deleteByPrimaryKey(Long id);

    int insert(Learning record);

    int insertSelective(Learning record);

    Learning selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Learning record);

    int updateByPrimaryKeyWithBLOBs(Learning record);

    int updateByPrimaryKey(Learning record);

    List<Map> getAll(Map<String, Object> query_map);

    Map getLearngingMap(Map query_map);

    Integer hitsLearningCouont(Map query_map);
}