package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.RecommendProductDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.RecommendProduct;
import com.tongfu.service.AdService;
import com.tongfu.service.RecommendProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class RecommendProductServiceImpl implements RecommendProductService {


	@Autowired
	private RecommendProductDao recommendProductDao;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return recommendProductDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RecommendProduct record) {
		return 0;
	}

	@Override
	public int insertSelective(RecommendProduct record) {
		return recommendProductDao.insertSelective(record);
	}

	@Override
	public RecommendProduct selectByPrimaryKey(Long id) {
		return recommendProductDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RecommendProduct record) {
		return recommendProductDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(RecommendProduct record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(RecommendProduct record) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getAll(Map<String, Object> query_map) {
		return recommendProductDao.getAll(query_map);
	}

	@Override
	public List<Map> getIsRecommendProducts(Map query_map) {
		return recommendProductDao.getIsRecommendProducts(query_map);
	}
}
