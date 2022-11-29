package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.dao.ProductActivationCodeDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.ProductActivationCode;
import com.tongfu.service.AdminService;
import com.tongfu.service.ProductActivationCodeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service
public class ProductActivationCodeServiceImpl implements ProductActivationCodeService {

	@Autowired
	ProductActivationCodeDao productActivationCodeDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(ProductActivationCode record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductActivationCode record) {
		return productActivationCodeDao.insertSelective(record);
	}

	@Override
	public ProductActivationCode selectByPrimaryKey(Long id) {
		return productActivationCodeDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductActivationCode record) {
		return productActivationCodeDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductActivationCode record) {
		return 0;
	}

	@Override
	public int insertSelectiveMap(Map<String, Object> map) {
		return productActivationCodeDao.insertSelectiveMap(map);
	}

	@Override
	public List<Map<String, Object>> replenishmentStatus(Map<String, Object> map) {
		return productActivationCodeDao.replenishmentStatus(map);
	}

	@Override
	public int deleteProductActivationCode(Long prouctPurchaseId) {
		return productActivationCodeDao.deleteProductActivationCode(prouctPurchaseId);
	}
}
