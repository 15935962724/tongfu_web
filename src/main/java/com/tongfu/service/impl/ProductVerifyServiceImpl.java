package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.ProductVerifyDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.ProductVerify;
import com.tongfu.service.AdService;
import com.tongfu.service.ProductVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductVerifyServiceImpl implements ProductVerifyService {


	@Autowired
	ProductVerifyDao productVerifyDao;

	@Override
	public int insert(ProductVerify record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductVerify record) {
		return 0;
	}

	@Override
	public List<ProductVerify> getAll(Map<String, Object> query_map) {
		return null;
	}

	@Override
	public List<Map<String, Object>> getProductVisit(Map<String, Object> query_map) {
		return productVerifyDao.getProductVisit(query_map);
	}
}
