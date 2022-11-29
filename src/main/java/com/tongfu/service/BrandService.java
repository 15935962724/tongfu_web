package com.tongfu.service;

import com.tongfu.entity.Ad;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Brand;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BrandService {

	int deleteByPrimaryKey(Long id);

	int insert(Brand record);

	int insertSelective(Brand record);

	Brand selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Brand record);

	int updateByPrimaryKeyWithBLOBs(Brand record);

	int updateByPrimaryKey(Brand record);

	List<Brand> getAll(Map<String,Object> query_map);


}