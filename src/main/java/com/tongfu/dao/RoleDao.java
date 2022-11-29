package com.tongfu.dao;

import com.tongfu.entity.Admin;
import com.tongfu.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<String> getRoleName (String  username);

    Collection<Role> getAll(Map<String,Object> query_map);

}