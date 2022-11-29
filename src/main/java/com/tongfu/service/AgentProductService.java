package com.tongfu.service;

import com.tongfu.entity.Agent;
import com.tongfu.entity.AgentProduct;

import java.util.List;
import java.util.Map;

public interface AgentProductService {

	int insert(AgentProduct record);

	int insertSelective(AgentProduct record);

	int insertMap(Map map);

}