package com.tongfu.service;

import com.tongfu.entity.CompanyBill;

import java.util.List;
import java.util.Map;

public interface CompanyBillService {
    int insert(CompanyBill record);

    int insertSelective(CompanyBill record);

    List<Map> getYearRegister(Map query_map);

    List<Integer> thatYearRegister(Map query_map);
}