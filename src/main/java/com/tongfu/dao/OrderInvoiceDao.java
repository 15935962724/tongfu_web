package com.tongfu.dao;

import com.tongfu.entity.OrderInvoice;

import java.util.List;
import java.util.Map;

public interface OrderInvoiceDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInvoice record);

    int insertSelective(OrderInvoice record);

    OrderInvoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInvoice record);

//    int updateByPrimaryKeyWithBLOBs(OrderInvoice record);

    int updateByPrimaryKey(OrderInvoice record);

    List<Map<String,Object>> getOrderInvoices(Map<String,Object> query_map);

    OrderInvoice getOrderInvoice(Map query_map);
}