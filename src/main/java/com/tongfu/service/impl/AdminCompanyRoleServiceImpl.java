package com.tongfu.service.impl;


import com.tongfu.dao.AdIncomeDao;
import com.tongfu.dao.AdminCompanyRoleDao;
import com.tongfu.entity.AdIncome;
import com.tongfu.entity.AdminCompanyRole;
import com.tongfu.service.AdIncomeService;
import com.tongfu.service.AdminCompanyRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdminCompanyRoleServiceImpl implements AdminCompanyRoleService {

    @Autowired
    private AdminCompanyRoleDao adminCompanyRoleDao;

    @Override
    public int insert(AdminCompanyRole record) {
        return 0;
    }

    @Override
    public int insertSelective(AdminCompanyRole record) {
        return adminCompanyRoleDao.insertSelective(record);
    }

    @Override
    public List<AdminCompanyRole> getAll(Map query_map) {
        return adminCompanyRoleDao.getAll(query_map);
    }

    @Override
    public int deleteByPrimaryAdminId(Long adminId) {
        return adminCompanyRoleDao.deleteByPrimaryAdminId(adminId);
    }
}
