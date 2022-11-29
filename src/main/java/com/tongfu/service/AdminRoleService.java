package com.tongfu.service;

import com.tongfu.entity.AdminRole;

import java.util.List;
import java.util.Map;

public interface AdminRoleService {
    int deleteByPrimaryKey(AdminRole key);

    int deleteByPrimaryAdminId(Long adminId);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    List<AdminRole> getAll(Map<String,Object> query_map);
}