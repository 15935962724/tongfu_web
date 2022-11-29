package com.tongfu.dao;

import com.tongfu.entity.CompanyBill;

import java.util.List;
import java.util.Map;

public interface CompanyBillDao {
    int insert(CompanyBill record);

    int insertSelective(CompanyBill record);

    List<Map> getYearRegister(Map query_map);

    List<Integer> thatYearRegister(Map query_map);
}