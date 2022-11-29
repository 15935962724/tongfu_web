package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.AdPositionDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.AdPosition;
import com.tongfu.service.AdPositionService;
import com.tongfu.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdPositionServiceImpl implements AdPositionService {

	@Autowired
	AdPositionDao adPositionDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return adPositionDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AdPosition record) {
		return 0;
	}

	@Override
	public int insertSelective(AdPosition record) {
		return adPositionDao.insertSelective(record);
	}

	@Override
	public AdPosition selectByPrimaryKey(Long id) {
		return adPositionDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AdPosition record) {
		return adPositionDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AdPosition record) {
		return 0;
	}

	@Override
	public List<AdPosition> getAll(Map<String, Object> query_map) {
		return adPositionDao.getAll(query_map);
	}
}
