package com.tongfu.dao;

import com.tongfu.entity.Media;

import java.util.List;
import java.util.Map;

public interface MediaDao {

    int deleteByPrimaryKey(Long id);

    int insert(Media record);

    int insertSelective(Media record);

    Media selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Media record);

    int updateByPrimaryKeyWithBLOBs(Media record);

    int updateByPrimaryKey(Media record);

    List<Map> getAll(Map<String,Object> query_map);

    Map getMediaMap(Map query_map);

    Integer hitsMediaCouont(Map query_map);

}