package com.tongfu.service.impl;

import com.tongfu.dao.ArticleStatisticsMapper;
import com.tongfu.entity.ArticleStatistics;
import com.tongfu.service.ArticleStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ArticleStatisticsServiceImpl implements ArticleStatisticsService {

	@Autowired
	private ArticleStatisticsMapper articleStatisticsMapper;

	@Override
	public int insert(ArticleStatistics record) {
		return 0;
	}

	@Override
	public int insertSelective(ArticleStatistics record) {
		return 0;
	}

	@Override
	public List<Map> getMemberSearch(Map query_map) {
		return articleStatisticsMapper.getMemberSearch(query_map);
	}
}
