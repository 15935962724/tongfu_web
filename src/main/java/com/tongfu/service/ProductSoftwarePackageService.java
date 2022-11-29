package com.tongfu.service;

import com.tongfu.entity.ProductSoftwarePackage;

import java.util.List;
import java.util.Map;

public interface ProductSoftwarePackageService {
    int insert(ProductSoftwarePackage record);

    int insertSelective(ProductSoftwarePackage record);

    int insertSelectiveMap(Map map);

    List<ProductSoftwarePackage> getProductSoftwarePackages(Map map);

    int deleteProductSoftwarePackage(Long productId);

}