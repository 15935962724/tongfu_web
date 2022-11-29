package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.DownloadStatisticsDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.DownloadStatistics;
import com.tongfu.service.AdService;
import com.tongfu.service.DownloadStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class DownloadStatisticsServiceImpl implements DownloadStatisticsService {

	@Autowired
	DownloadStatisticsDao downloadStatisticsDao;

	@Override
	public int insert(DownloadStatistics record) {
		return 0;
	}

	@Override
	public int insertSelective(DownloadStatistics record) {
		return 0;
	}

	@Override
	public int getMonthCountDownloadStatistics(Map<String, Object> query_map) {
		return downloadStatisticsDao.getMonthCountDownloadStatistics(query_map);
	}

	@Override
	public int getWeekCountDownloadStatistics(Map<String, Object> query_map) {
		return downloadStatisticsDao.getWeekCountDownloadStatistics(query_map);
	}

	@Override
	public int getDayCountDownloadStatistics(Map<String, Object> query_map) {
		return downloadStatisticsDao.getDayCountDownloadStatistics(query_map);
	}

	@Override
	public List<Map<String, Object>> getDownloadHistogramStatistics(Map<String, Object> query_map) {
		return downloadStatisticsDao.getDownloadHistogramStatistics(query_map);
	}
}
