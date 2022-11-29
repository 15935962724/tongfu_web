package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.MediaDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Media;
import com.tongfu.service.AdService;
import com.tongfu.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MediaServiceImpl implements MediaService {


	@Autowired
	MediaDao mediaDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return mediaDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Media record) {
		return 0;
	}

	@Override
	public int insertSelective(Media record) {
		return mediaDao.insertSelective(record);
	}

	@Override
	public Media selectByPrimaryKey(Long id) {
		return mediaDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Media record) {
		return mediaDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Media record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Media record) {
		return 0;
	}

	@Override
	public List<Map> getAll(Map<String, Object> query_map) {
		return mediaDao.getAll(query_map);
	}

	@Override
	public Map getMediaMap(Map query_map) {
		return mediaDao.getMediaMap(query_map);
	}

	@Override
	public Integer hitsMediaCouont(Map query_map) {
		return mediaDao.hitsMediaCouont(query_map);
	}
}
