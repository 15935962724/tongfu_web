package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.OrderInvoiceDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.OrderInvoice;
import com.tongfu.service.AdService;
import com.tongfu.service.OrderInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OrderInvoiceServiceImpl implements OrderInvoiceService {


	@Autowired
	OrderInvoiceDao orderInvoiceDao;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(OrderInvoice record) {
		return 0;
	}

	@Override
	public int insertSelective(OrderInvoice record) {
		return 0;
	}

	@Override
	public OrderInvoice selectByPrimaryKey(Long id) {
		return orderInvoiceDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderInvoice record) {
		return orderInvoiceDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(OrderInvoice record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(OrderInvoice record) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getOrderInvoices(Map<String, Object> query_map) {
		return orderInvoiceDao.getOrderInvoices(query_map);
	}

	@Override
	public OrderInvoice getOrderInvoice(Map query_map) {
		return orderInvoiceDao.getOrderInvoice(query_map);
	}
}
