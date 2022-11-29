package com.tongfu.service;

import com.tongfu.entity.RoleAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RoleAuthorityService {
    int insert(RoleAuthority record);

    int insertSelective(RoleAuthority record);

    List<String> getAuthorities(String username);

    int insertMap(Map<String, Object> map);

    List<String> getAuthorities(Map<String, Object> map);

}