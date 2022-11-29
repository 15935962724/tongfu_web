package com.tongfu.service;

import com.tongfu.entity.Admin;
import com.tongfu.entity.ProductPurchase;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductPurchaseService {


	int deleteByPrimaryKey(Long id);

	int insert(ProductPurchase record);

	int insertSelective(ProductPurchase record);

	ProductPurchase selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProductPurchase record);

	int updateByPrimaryKey(ProductPurchase record);

	int insertMap(Map<String, Object> map);

	List<ProductPurchase> getAll(Map<String,Object> query_map);

	int deleteByProductId(Long productId);

	List<Map<String,Object>> getPurchases(Map<String,Object> query_map);

	List<Long> getPurchasesLackStock(Map query_map);

	Long  getProductPurchaseId(Map query_map);

}