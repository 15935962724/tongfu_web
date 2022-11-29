package com.tongfu.dao;

import com.tongfu.entity.Agent;

import java.util.List;
import java.util.Map;

public interface AgentMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Agent record);

    int insertSelective(Agent record);

    Agent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    List<Agent> getList(Map query_map);
}