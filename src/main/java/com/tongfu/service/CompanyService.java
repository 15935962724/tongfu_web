package com.tongfu.service;

import com.tongfu.entity.Admin;
import com.tongfu.entity.Company;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CompanyService {

    int deleteByPrimaryKey(Long id);

    int insert(Company record);

    int insertSelective(Map<String,Object>query_map);

    Company selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    Long getCompanyId();

    Company selectByAdminId(Long adminId);

    Company getCompany();

    List<Map<String,Object>> getAll(Map<String,Object> query_map);

    List<Map> getVisitsRanking(Map query_map);




}