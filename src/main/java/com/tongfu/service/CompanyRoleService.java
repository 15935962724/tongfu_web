package com.tongfu.service;

import com.tongfu.entity.CompanyRole;

import java.util.List;
import java.util.Map;

public interface CompanyRoleService {
    int deleteByPrimaryKey(Long id);

    int insert(CompanyRole record);

    int insertSelective(CompanyRole record);

    CompanyRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompanyRole record);

    int updateByPrimaryKey(CompanyRole record);

    List<String> getCompanyRoleName(String username);

    List<CompanyRole> getAll (Map<String,Object> query_map);

}