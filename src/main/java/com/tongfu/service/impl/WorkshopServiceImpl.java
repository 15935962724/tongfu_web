package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.WorkshopDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Workshop;
import com.tongfu.service.AdService;
import com.tongfu.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class WorkshopServiceImpl implements WorkshopService {

	@Autowired
	WorkshopDao workshopDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return workshopDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Workshop record) {
		return workshopDao.insert(record);
	}

	@Override
	public int insertSelective(Workshop record) {
		return workshopDao.insertSelective(record);
	}

	@Override
	public Workshop selectByPrimaryKey(Long id) {
		return workshopDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Workshop record) {
		return workshopDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Workshop record) {
		return workshopDao.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(Workshop record) {
		return workshopDao.updateByPrimaryKey(record);
	}

	@Override
	public List<Workshop> getAll(Map<String, Object> query_map) {
		return workshopDao.getAll(query_map);
	}
}
