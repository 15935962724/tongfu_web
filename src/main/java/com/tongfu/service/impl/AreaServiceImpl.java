package com.tongfu.service.impl;

import com.tongfu.dao.AreaDao;
import com.tongfu.entity.Area;
import com.tongfu.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	AreaDao areaDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Area record) {
		return 0;
	}

	@Override
	public int insertSelective(Area record) {
		return 0;
	}

	@Override
	public Area selectByPrimaryKey(Long id) {
		return areaDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Area record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Area record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Area record) {
		return 0;
	}

	@Override
	public List<Area> getAll(Map<String, Object> query_map) {
		return areaDao.getAll(query_map);
	}

	@Override
	public Map parentArea(Long id) {
		return areaDao.parentArea(id);
	}


}
