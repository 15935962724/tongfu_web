package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.DogDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Dog;
import com.tongfu.service.AdService;
import com.tongfu.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdServiceImpl implements AdService {


	@Autowired
	AdDao adDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return adDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Ad record) {
		return 0;
	}

	@Override
	public int insertSelective(Ad record) {
		return adDao.insertSelective(record);
	}

	@Override
	public Ad selectByPrimaryKey(Long id) {
		return adDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Ad record) {
		return adDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Ad record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Ad record) {
		return adDao.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String,Object>> getAll(Map<String, Object> query_map) {
		return adDao.getAll(query_map);
	}

	@Override
	public int toExamineAdCouont(Map<String, Object> query_map) {
		return adDao.toExamineAdCouont(query_map);
	}

	@Override
	public int toOverdueadCouont(Map<String, Object> query_map) {
		return adDao.toOverdueadCouont(query_map);
	}

	@Override
	public int toShowAdCouont(Map<String, Object> query_map) {
		return adDao.toShowAdCouont(query_map);
	}
}
