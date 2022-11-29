package com.tongfu.dao;

import com.tongfu.entity.RoleAuthority;
import com.tongfu.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RoleAuthorityDao {
    int insert(RoleAuthority record);

    int insertSelective(RoleAuthority record);

    List<String> getAuthorities(String username);

    int insertMap(Map<String, Object> map);

    List<String> getAll(Map<String, Object> map);

    int deleteAuthoritie(Map<String, Object> map);


}