package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.dao.ProductPurchaseDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.ProductPurchase;
import com.tongfu.service.AdminService;
import com.tongfu.service.ProductPurchaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

	@Autowired
	ProductPurchaseDao productPurchaseDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return productPurchaseDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProductPurchase record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductPurchase record) {
		return productPurchaseDao.insertSelective(record);
	}

	@Override
	public ProductPurchase selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ProductPurchase record) {
		return productPurchaseDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductPurchase record) {
		return 0;
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return productPurchaseDao.insertMap(map);
	}

	@Override
	public List<ProductPurchase> getAll(Map<String, Object> query_map) {
		return productPurchaseDao.getAll(query_map);
	}

	@Override
	public int deleteByProductId(Long productId) {
		return productPurchaseDao.deleteByProductId(productId);
	}

	@Override
	public List<Map<String, Object>> getPurchases(Map<String, Object> query_map) {
		return productPurchaseDao.getPurchases(query_map);
	}

	@Override
	public List<Long> getPurchasesLackStock(Map query_map) {
		return productPurchaseDao.getPurchasesLackStock(query_map);
	}

	@Override
	public Long getProductPurchaseId(Map query_map) {
		return productPurchaseDao.getProductPurchaseId(query_map);
	}


}
