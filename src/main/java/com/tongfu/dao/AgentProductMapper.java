package com.tongfu.dao;

import com.tongfu.entity.AgentProduct;

import java.util.Map;

public interface AgentProductMapper {
    int insert(AgentProduct record);

    int insertSelective(AgentProduct record);

    int insertMap(Map map);


}