package com.tongfu.service.impl;

import com.tongfu.dao.AdminRoleDao;
import com.tongfu.dao.HospitalCategoryDao;
import com.tongfu.entity.AdminRole;
import com.tongfu.entity.HospitalCategory;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.HospitalCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class HospitalCategoryServiceImpl implements HospitalCategoryService {

	@Autowired
	HospitalCategoryDao hospitalCategoryDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(HospitalCategory record) {
		return 0;
	}

	@Override
	public int insertSelective(HospitalCategory record) {
		return 0;
	}

	@Override
	public HospitalCategory selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(HospitalCategory record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(HospitalCategory record) {
		return 0;
	}

	@Override
	public List<HospitalCategory> getAll(Map<String, Object> query_map) {
		return hospitalCategoryDao.getAll(query_map);
	}
}
