package com.tongfu.dao;

import com.tongfu.entity.ProductSoftwarePackage;

import java.util.List;
import java.util.Map;

public interface ProductSoftwarePackageMapper {
    int insert(ProductSoftwarePackage record);

    int insertSelective(ProductSoftwarePackage record);

    int insertSelectiveMap(Map map);

    List<ProductSoftwarePackage> getProductSoftwarePackages(Map map);

    int deleteProductSoftwarePackage(Long productId);


}