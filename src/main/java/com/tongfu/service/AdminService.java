package com.tongfu.service;

import com.tongfu.entity.Admin;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AdminService {

	int deleteByPrimaryKey(Long id);

	int insert(Admin record);

	int insertSelective(Admin record);

	Admin selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Admin record);

	int updateByPrimaryKey(Admin record);

	int updateAdmin(Admin record);

	Admin selectByUserName(String userName);

	Collection<Admin> getAll(Map<String,Object> query_map);

	Admin getCurrent();

	Admin getCompanyAdmin(Map<String,Object> query_map);

	List<Map> getCompanyAdminAll(Map query_map);
	
}