package com.tongfu.service.impl;

import com.tongfu.config.ShiroConfig;
import com.tongfu.dao.*;
import com.tongfu.entity.*;
import com.tongfu.service.CompanyService;
import com.tongfu.service.ExhibitionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ExhibitionServiceImpl implements ExhibitionService {

	@Autowired
	ExhibitionDao exhibitionDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return exhibitionDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Exhibition record) {
		return 0;
	}

	@Override
	public int insertSelective(Exhibition record) {
		return exhibitionDao.insertSelective(record);
	}

	@Override
	public Exhibition selectByPrimaryKey(Long id) {
		return exhibitionDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Exhibition record) {
		return exhibitionDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Exhibition record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Exhibition record) {
		return 0;
	}

	@Override
	public List<Map> getAll(Map<String, Object> query_map) {
		return exhibitionDao.getAll(query_map);
	}

	@Override
	public Map getExhibitionMap(Map query_map) {
		return exhibitionDao.getExhibitionMap(query_map);
	}

	@Override
	public Integer hitsExhibitionCouont(Map query_map) {
		return exhibitionDao.hitsExhibitionCouont(query_map);
	}
}
