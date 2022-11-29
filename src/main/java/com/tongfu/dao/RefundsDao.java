package com.tongfu.dao;

import com.tongfu.entity.Refunds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RefundsDao {
    int deleteByPrimaryKey(Long id);

    int insert(Refunds record);

    int insertSelective(Refunds record);

    Refunds selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Refunds record);

    int updateByPrimaryKey(Refunds record);

    List<BigDecimal> thatYearReturnsAmount (Map query_map);//查询当年的退款额

    List<Map> getYearReturnsAmount(Map query_map);//查询退款额单产生的所有年份

    List<Map> getRefundsCompany(Map query_map);//查询退货单单产生的所有供应商
}