package com.tongfu.service.impl;

import com.tongfu.dao.CompanyRoleDao;
import com.tongfu.dao.RoleAuthorityDao;
import com.tongfu.dao.RoleDao;
import com.tongfu.entity.CompanyRole;
import com.tongfu.entity.Role;
import com.tongfu.service.CompanyRoleService;
import com.tongfu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CompanyRoleServiceImpl implements CompanyRoleService {

	@Autowired
	private CompanyRoleDao companyRoleDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return companyRoleDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CompanyRole record) {
		return 0;
	}

	@Override
	public int insertSelective(CompanyRole record) {
		return companyRoleDao.insertSelective(record);
	}

	@Override
	public CompanyRole selectByPrimaryKey(Long id) {
		return companyRoleDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CompanyRole record) {
		return companyRoleDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CompanyRole record) {
		return 0;
	}

	@Override
	public List<String> getCompanyRoleName(String username) {
		return companyRoleDao.getCompanyRoleName(username);
	}

	@Override
	public List<CompanyRole> getAll(Map<String, Object> query_map) {
		return companyRoleDao.getAll(query_map);
	}
}
