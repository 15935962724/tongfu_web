package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.PaymentMethodDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.PaymentMethod;
import com.tongfu.service.AdService;
import com.tongfu.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {


	@Autowired
	PaymentMethodDao paymentMethodDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return paymentMethodDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PaymentMethod record) {
		return 0;
	}

	@Override
	public int insertSelective(PaymentMethod record) {
		return paymentMethodDao.insertSelective(record);
	}

	@Override
	public PaymentMethod selectByPrimaryKey(Long id) {
		return paymentMethodDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PaymentMethod record) {
		return paymentMethodDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PaymentMethod record) {
		return paymentMethodDao.updateByPrimaryKey(record);
	}

	@Override
	public List<PaymentMethod> getAll(Map<String, Object> query_map) {
		return paymentMethodDao.getAll(query_map);
	}
}
