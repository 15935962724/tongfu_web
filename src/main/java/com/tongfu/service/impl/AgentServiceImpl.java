package com.tongfu.service.impl;

import com.tongfu.dao.AgentMapper;
import com.tongfu.dao.AreaDao;
import com.tongfu.entity.Agent;
import com.tongfu.entity.Area;
import com.tongfu.service.AgentService;
import com.tongfu.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private AgentMapper agentMapper;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return agentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Agent record) {
		return agentMapper.insert(record);
	}

	@Override
	public int insertSelective(Agent record) {
		return agentMapper.insertSelective(record);
	}

	@Override
	public Agent selectByPrimaryKey(Long id) {
		return agentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Agent record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Agent record) {
		return 0;
	}

	@Override
	public List<Agent> getList(Map query_map) {
		return agentMapper.getList(query_map);
	}
}
