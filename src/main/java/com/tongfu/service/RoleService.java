package com.tongfu.service;

import com.tongfu.entity.Ad;
import com.tongfu.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RoleService {

	int deleteByPrimaryKey(Long id);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Role record,String[]authorities);

	int updateByPrimaryKey(Role record);

	List<String > getRoleName(String  username);

	Collection<Role> getAll(Map<String,Object> query_map);

}