package com.tongfu.dao;

import com.tongfu.entity.AdminCompanyRole;

import java.util.List;
import java.util.Map;

public interface AdminCompanyRoleDao{
    int insert(AdminCompanyRole record);

    int insertSelective(AdminCompanyRole record);

    List<AdminCompanyRole> getAll(Map query_map);

    int deleteByPrimaryAdminId(Long adminId);
}