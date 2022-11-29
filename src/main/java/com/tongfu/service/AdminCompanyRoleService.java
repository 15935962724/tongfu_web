package com.tongfu.service;

import com.tongfu.entity.AdminCompanyRole;
import com.tongfu.entity.CompanyRole;

import java.util.List;
import java.util.Map;

public interface AdminCompanyRoleService {

    int insert(AdminCompanyRole record);

    int insertSelective(AdminCompanyRole record);

    List<AdminCompanyRole> getAll(Map query_map);

    int deleteByPrimaryAdminId(Long adminId);



}