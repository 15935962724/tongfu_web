package com.tongfu.dao;

import com.tongfu.entity.CompanyRoleAuthority;

import java.util.List;
import java.util.Map;

public interface CompanyRoleAuthorityDao{
    int insert(CompanyRoleAuthority record);

    int insertSelective(CompanyRoleAuthority record);

    int insertMap(Map<String,Object>query_map);

    List<String> getAuthorities(String username);

    List<String> getCompanyAuthorities(Map query_map);
}