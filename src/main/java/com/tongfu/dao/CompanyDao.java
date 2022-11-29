package com.tongfu.dao;

import com.tongfu.entity.Admin;
import com.tongfu.entity.Company;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CompanyDao {
    int deleteByPrimaryKey(Long id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKeyWithBLOBs(Company record);

    int updateByPrimaryKey(Company record);

    Company selectByAdminId(Long adminId);

    List<Map<String,Object>> getAll(Map<String,Object> query_map);

    List<Map> getVisitsRanking(Map query_map);
}