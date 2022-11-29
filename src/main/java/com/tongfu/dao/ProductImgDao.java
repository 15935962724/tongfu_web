package com.tongfu.dao;

import com.tongfu.entity.ProductImg;

import java.util.List;
import java.util.Map;

public interface ProductImgDao {
    int insert(ProductImg record);

    int insertSelective(ProductImg record);

    int insertMap(Map<String, Object> map);

    ProductImg selectByProductAndOrders(Map<String, Object> map);

    int updateByProductId(Map<String, Object> map);

    List<ProductImg> getProductImgs(Long id);


}