package com.tongfu.service;

import com.tongfu.entity.Returns;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReturnsService {
    int deleteByPrimaryKey(Long id);

    int insert(Returns record);

    int insertSelective(Returns record);

    Returns selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Returns record);

    int updateByPrimaryKey(Returns record);

    List<Map> getYearReturns(Map query_map);//查询退货单单产生的所有年份

    List<Integer> thatYearReturns (Map query_map);//查询当年的退款单数

    List<Map> getReturnsCompany(Map query_map);//查询退货单单产生的所有供应商


}