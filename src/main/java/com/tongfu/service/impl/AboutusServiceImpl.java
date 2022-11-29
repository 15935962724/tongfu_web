package com.tongfu.service.impl;

import com.tongfu.dao.AboutusDao;
import com.tongfu.dao.AdDao;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.Ad;
import com.tongfu.service.AboutusService;
import com.tongfu.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AboutusServiceImpl implements AboutusService {

	@Autowired
	private AboutusDao aboutusDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return aboutusDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Aboutus record) {
		return 0;
	}

	@Override
	public int insertSelective(Aboutus record) {
		return aboutusDao.insertSelective(record);
	}

	@Override
	public Aboutus selectByPrimaryKey(Long id) {
		return aboutusDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Aboutus record) {
		return aboutusDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Aboutus record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Aboutus record) {
		return 0;
	}

	@Override
	public List<Aboutus> getAll(Map query_map) {
		return aboutusDao.getAll(query_map);
	}
}
