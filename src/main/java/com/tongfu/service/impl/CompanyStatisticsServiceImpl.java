package com.tongfu.service.impl;

import com.tongfu.dao.AboutusDao;
import com.tongfu.dao.CompanyStatisticsMapper;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.CompanyStatistics;
import com.tongfu.service.AboutusService;
import com.tongfu.service.CompanyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CompanyStatisticsServiceImpl implements CompanyStatisticsService {


	@Autowired
	private CompanyStatisticsMapper companyStatisticsMapper;

	@Override
	public int insert(CompanyStatistics record) {
		return 0;
	}

	@Override
	public int insertSelective(CompanyStatistics record) {
		return 0;
	}

	@Override
	public List<Map> getThisWeekCompanyFollows(Map query_map) {
		return companyStatisticsMapper.getThisWeekCompanyFollows(query_map);
	}

	@Override
	public List<Map> getLastWeekCompanyFollows(Map query_map) {
		return companyStatisticsMapper.getLastWeekCompanyFollows(query_map);
	}

}
