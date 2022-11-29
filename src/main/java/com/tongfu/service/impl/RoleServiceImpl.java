package com.tongfu.service.impl;

import com.tongfu.dao.RoleAuthorityDao;
import com.tongfu.dao.RoleDao;
import com.tongfu.entity.AdminRole;
import com.tongfu.entity.Role;
import com.tongfu.entity.RoleAuthority;
import com.tongfu.service.AdminRoleService;
import com.tongfu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Autowired
	RoleAuthorityDao roleAuthorityDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return roleDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Role record) {
		return 0;
	}

	@Override
	public int insertSelective(Role record) {
		return roleDao.insertSelective(record);
	}

	@Override
	public Role selectByPrimaryKey(Long id) {
		return roleDao.selectByPrimaryKey(id);
	}

	@Override
	@Transient
	public int updateByPrimaryKeySelective(Role record,String[]authorities) {
		Integer roleAuthorityCount = roleDao.updateByPrimaryKeySelective(record);
		Map<String, Object> map = new HashMap<>();
		map.put("role", record.getId());
		map.put("authorities", authorities);
		roleAuthorityDao.deleteAuthoritie(map);
		if (authorities.length>0){
			 roleAuthorityCount = roleAuthorityDao.insertMap(map);
		}
		return roleAuthorityCount;
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return 0;
	}

	@Override
	public List<String> getRoleName (String  username){
		return roleDao.getRoleName(username);
	}

	@Override
	public Collection<Role> getAll(Map<String, Object> query_map) {
		return roleDao.getAll(query_map);
	}
}
