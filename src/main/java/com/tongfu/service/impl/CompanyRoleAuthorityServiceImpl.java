package com.tongfu.service.impl;

import com.tongfu.dao.AboutusDao;
import com.tongfu.dao.CompanyRoleAuthorityDao;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.CompanyRoleAuthority;
import com.tongfu.service.AboutusService;
import com.tongfu.service.CompanyRoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CompanyRoleAuthorityServiceImpl implements CompanyRoleAuthorityService {

	@Autowired
	private CompanyRoleAuthorityDao companyRoleAuthorityDao;

	@Override
	public int insert(CompanyRoleAuthority record) {
		return companyRoleAuthorityDao.insert(record);
	}

	@Override
	public int insertSelective(CompanyRoleAuthority record) {
		return 0;
	}

	@Override
	public int insertMap(Map<String, Object> query_map) {
		return companyRoleAuthorityDao.insertMap(query_map);
	}

	@Override
	public List<String> getAuthorities(String username) {
		return null;
	}

	@Override
	public List<String> getCompanyAuthorities(Map query_map) {
		return companyRoleAuthorityDao.getCompanyAuthorities(query_map);
	}
}
