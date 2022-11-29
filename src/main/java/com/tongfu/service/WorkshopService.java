package com.tongfu.service;

import com.tongfu.entity.Ad;
import com.tongfu.entity.Workshop;

import java.util.List;
import java.util.Map;

public interface WorkshopService {

	int deleteByPrimaryKey(Long id);

	int insert(Workshop record);

	int insertSelective(Workshop record);

	Workshop selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Workshop record);

	int updateByPrimaryKeyWithBLOBs(Workshop record);

	int updateByPrimaryKey(Workshop record);

	List<Workshop> getAll(Map<String,Object> query_map);

}