package com.tongfu.service.impl;

import com.tongfu.dao.AdminRoleDao;
import com.tongfu.dao.HospitalRankDao;
import com.tongfu.entity.AdminRole;
import com.tongfu.entity.HospitalRank;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.HospitalRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class HospitalRankServiceImpl implements HospitalRankService {

	@Autowired
	HospitalRankDao hospitalRankDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(HospitalRank record) {
		return 0;
	}

	@Override
	public int insertSelective(HospitalRank record) {
		return 0;
	}

	@Override
	public HospitalRank selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(HospitalRank record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(HospitalRank record) {
		return 0;
	}

	@Override
	public List<HospitalRank> getAll(Map<String, Object> query_map) {
		return hospitalRankDao.getAll(query_map);
	}
}
