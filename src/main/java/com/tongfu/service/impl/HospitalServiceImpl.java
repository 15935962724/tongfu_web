package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.HospitalDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Hospital;
import com.tongfu.service.AdService;
import com.tongfu.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class HospitalServiceImpl implements HospitalService {


	@Autowired
	HospitalDao hospitalDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return hospitalDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Hospital record) {
		return 0;
	}

	@Override
	public int insertSelective(Hospital record) {
		return hospitalDao.insertSelective(record);
	}

	@Override
	public Hospital selectByPrimaryKey(Long id) {
		return hospitalDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Hospital record) {
		return hospitalDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Hospital record) {
		return 0;
	}

	@Override
	public List<Map<String,Object>> getAll(Map<String, Object> query_map) {
		return hospitalDao.getAll(query_map);
	}
}
