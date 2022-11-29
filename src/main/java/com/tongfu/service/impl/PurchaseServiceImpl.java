package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.PurchaseDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Purchase;
import com.tongfu.service.AdService;
import com.tongfu.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	PurchaseDao purchaseDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Purchase record) {
		return 0;
	}

	@Override
	public int insertSelective(Purchase record) {
		return 0;
	}

	@Override
	public Purchase selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Purchase record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Purchase record) {
		return 0;
	}

	@Override
	public List<Purchase> getAll(Map<String, Object> query_map) {
		return purchaseDao.getAll(query_map);
	}

	@Override
	public List<Purchase> getParentPurchase(Map<String, Object> query_map) {
		return purchaseDao.getParentPurchase(query_map);
	}

	@Override
	public List<Map<String, Object>> selectGuige(Map<String, Object> map) {
		return purchaseDao.selectGuige(map);
	}
}
