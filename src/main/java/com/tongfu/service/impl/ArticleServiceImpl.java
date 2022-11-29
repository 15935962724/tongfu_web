package com.tongfu.service.impl;

import com.tongfu.dao.ArticleDao;
import com.tongfu.entity.Article;
import com.tongfu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return articleDao.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteMap(Map query_map) {
		return articleDao.deleteMap(query_map);
	}

	@Override
	public int insert(Article record) {
		return 0;
	}

	@Override
	public int insertMap(Map query_map) {
		return articleDao.insertMap(query_map);
	}

	@Override
	public int insertSelective(Article record) {
		return articleDao.insertSelective(record);
	}

	@Override
	public Article selectByPrimaryKey(Long id) {
		return articleDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Article record) {
		return articleDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Article record) {
		return articleDao.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(Article record) {
		return 0;
	}

	@Override
	public List<Article> getAll(Map<String, Object> query_map) {
		return articleDao.getAll(query_map);
	}


}
