package com.tongfu.service.impl;

import com.tongfu.dao.ProductDao;
import com.tongfu.dao.ProductImgDao;
import com.tongfu.entity.Product;
import com.tongfu.entity.ProductImg;
import com.tongfu.service.ProductImgService;
import com.tongfu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImgImpl implements ProductImgService {

	@Autowired
	ProductImgDao productImgDao;

	@Override
	public int insert(ProductImg record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductImg record) {
		return 0;
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return productImgDao.insertMap(map);
	}

	@Override
	public ProductImg selectByProductAndOrders(Map<String, Object> map) {
		return productImgDao.selectByProductAndOrders(map);
	}

	@Override
	public int updateByProductId(Map<String, Object> map) {
		return productImgDao.updateByProductId(map);
	}

	@Override
	public List<ProductImg> getProductImgs(Long id) {
		return productImgDao.getProductImgs(id);
	}


}
