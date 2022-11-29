package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.dao.RoleAuthorityDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.RoleAuthority;
import com.tongfu.service.AdminService;
import com.tongfu.service.RoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

	@Autowired
	RoleAuthorityDao roleAuthorityDao;

	@Override
	public int insert(RoleAuthority record) {
		return 0;
	}

	@Override
	public int insertSelective(RoleAuthority record) {
		return 0;
	}

	@Override
	public List<String> getAuthorities(String username) {
		return roleAuthorityDao.getAuthorities(username);
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return roleAuthorityDao.insertMap(map);
	}

	@Override
	public List<String> getAuthorities(Map<String, Object> map) {
		return roleAuthorityDao.getAll(map);
	}
}
