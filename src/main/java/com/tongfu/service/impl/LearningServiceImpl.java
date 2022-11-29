package com.tongfu.service.impl;

import com.tongfu.dao.ExhibitionDao;
import com.tongfu.dao.LearningDao;
import com.tongfu.entity.Exhibition;
import com.tongfu.entity.Learning;
import com.tongfu.service.ExhibitionService;
import com.tongfu.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class LearningServiceImpl implements LearningService {

	@Autowired
	LearningDao learningDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return learningDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Learning record) {
		return 0;
	}

	@Override
	public int insertSelective(Learning record) {
		return learningDao.insertSelective(record);
	}

	@Override
	public Learning selectByPrimaryKey(Long id) {
		return learningDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Learning record) {
		return learningDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Learning record) {
		return learningDao.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(Learning record) {
		return 0;
	}

	@Override
	public List<Map> getAll(Map<String, Object> query_map) {
		return learningDao.getAll(query_map);
	}

	@Override
	public Map getLearngingMap(Map query_map) {
		return learningDao.getLearngingMap(query_map);
	}

	@Override
	public Integer hitsLearningCouont(Map query_map) {
		return learningDao.hitsLearningCouont(query_map);
	}

}
