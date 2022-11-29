package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.dao.CompanyDao;
import com.tongfu.dao.ProductDao;
import com.tongfu.dao.ProductVerifyDao;
import com.tongfu.entity.*;
import com.tongfu.service.AdminService;
import com.tongfu.service.ProductService;
import com.tongfu.util.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductVerifyDao productVerifyDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return productDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Product record) {
		return 0;
	}

	@Override
	public int insertSelective(Product record) {
		return  productDao.insertSelective(record);
	}

	@Override
	public Product selectByPrimaryKey(Long id) {
		return productDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Product record) {
		return productDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Product record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Product record) {
		return 0;
	}

	@Override
	public List<Product> getAll(Map<String, Object> query_map) {
		return productDao.getAll(query_map);
	}

	@Override
	public List<Map<String, Object>> getAllMap(Map<String, Object> query_map) {
		return productDao.getAllMap(query_map);
	}

	@Override
	public int updateByPrimaryKeySelective(Product record, String introduction) {
		record.setMessage(introduction);
		Integer updateProductStatus = productDao.updateByPrimaryKeySelective(record);
		ProductVerify productVerify = new ProductVerify();
		productVerify.setCreateDate(new Date());
		productVerify.setModifyDate(new Date());
		productVerify.setProductId(record.getId());
		System.out.println(SecurityUtils.getSubject().getPrincipal());
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
		productVerify.setAdminId(admin.getId());
		productVerify.setStatus(Integer.parseInt(String.valueOf(record.getStatus())));
		productVerify.setContent(introduction);
		productVerifyDao.insertSelective(productVerify);
		return updateProductStatus;
	}

	@Override
	public int getCountProduct(Map<String, Object> query_map) {
		return productDao.getCountProduct(query_map);
	}

	@Override
	public List<Map<String, Object>> getProductStatistics(Map<String, Object> query_map) {
		return productDao.getProductStatistics(query_map);
	}

	@Override
	public int nextTableId(String tableName) {
		return productDao.nextTableId(tableName);
	}

	@Override
	public List<Map<String, Object>> getProductSalesStatistics(Map<String, Object> query_map) {
		return productDao.getProductSalesStatistics(query_map);
	}

	@Override
	public List<Map> getAuthenticationStatistics(Map query_map) {
		return productDao.getAuthenticationStatistics(query_map);
	}

}
