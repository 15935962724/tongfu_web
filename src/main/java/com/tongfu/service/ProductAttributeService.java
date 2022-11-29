package com.tongfu.service;

import com.tongfu.dao.ProductDao;
import com.tongfu.dao.ProductVerifyDao;
import com.tongfu.entity.Product;
import com.tongfu.entity.ProductPurchase;
import com.tongfu.entity.ProductVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productAttributeService")
public class ProductAttributeService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductPurchaseService productPurchaseService;

	@Autowired
	private ProductVerifyDao productVerifyDao;

	@Autowired
	private ProductDao productDao;

	public  Map<String,Object> getProductAttribute(Long id){
		Map<String,Object> return_map = new HashMap<>();
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("productId",id);
		Product product = productDao.selectByPrimaryKey(id);
		List<ProductPurchase> productPurchases =  productPurchaseService.getAll(query_map);

		BigDecimal price = product.getPrice();
		if (productPurchases.size()>0){
			price = productPurchases.get(0).getPrice();
		}
		query_map.put("status",2);//被拒绝的
		List<ProductVerify> productVerifies = productVerifyDao.getAll(query_map);
		String contetn = "已通过";
		if (productVerifies.size()>0){
		   contetn  = 	productVerifies.get(0).getContent();
		}
		return_map.put("price",price);
		return_map.put("content",contetn);
		return return_map;
	}


}