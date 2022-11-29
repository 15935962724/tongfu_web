package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.dao.KnowHowDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.KnowHow;
import com.tongfu.service.AdminService;
import com.tongfu.service.KnowHowService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service
public class KnowHowServiceImpl implements KnowHowService {

	@Autowired
	KnowHowDao knowHowDao;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return knowHowDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(KnowHow record) {
		return 0;
	}

	@Override
	public int insertSelective(KnowHow record) {
		return knowHowDao.insertSelective(record);
	}

	@Override
	public KnowHow selectByPrimaryKey(Long id) {
		return knowHowDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(KnowHow record) {
		return knowHowDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(KnowHow record) {
		return 0;
	}

	@Override
	public List<KnowHow> getAll(Map<String, Object> query_map) {
		return knowHowDao.getAll(query_map);
	}
}
