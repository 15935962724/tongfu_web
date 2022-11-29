package com.tongfu.service.impl;

import com.tongfu.dao.AdminRoleDao;
import com.tongfu.dao.SpecificationsDao;
import com.tongfu.entity.AdminRole;
import com.tongfu.entity.Specifications;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.SpecificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SpecificationsImpl implements SpecificationsService {

	@Autowired
	SpecificationsDao specificationsDao;

	@Override
	public int insert(Specifications record) {
		return 0;
	}

	@Override
	public int insertSelective(Specifications record) {
		return specificationsDao.insertSelective(record);
	}

	@Override
	public List<Specifications> getAll(Map<String, Object> query_map) {
		return specificationsDao.getAll(query_map);
	}

	@Override
	public Specifications selectByPrimaryKey(Long id) {
		return specificationsDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Specifications record) {
		return specificationsDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return specificationsDao.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByProduct(Long productId) {
		return specificationsDao.deleteByProduct(productId);
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return specificationsDao.insertMap(map);
	}
}
