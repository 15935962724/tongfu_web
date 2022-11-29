package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.dao.AdminRoleDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.AdminRole;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdminRoleServiceImpl implements AdminRoleService {

	@Autowired
	AdminRoleDao adminRoleDao;

	@Override
	public int deleteByPrimaryKey(AdminRole key) {
		return 0;
	}

	@Override
	public int deleteByPrimaryAdminId(Long adminId) {
		return adminRoleDao.deleteByPrimaryAdminId(adminId);
	}

	@Override
	public int insert(AdminRole record) {
		return 0;
	}

	@Override
	public int insertSelective(AdminRole record) {
		return adminRoleDao.insertSelective(record);
	}

	@Override
	public List<AdminRole> getAll(Map<String, Object> query_map) {
		return adminRoleDao.getAll(query_map);
	}
}
