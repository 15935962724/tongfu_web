package com.tongfu.service;

import com.tongfu.entity.Ad;
import com.tongfu.entity.DownloadStatistics;

import java.util.List;
import java.util.Map;

public interface DownloadStatisticsService {

	int insert(DownloadStatistics record);

	int insertSelective(DownloadStatistics record);

	int getMonthCountDownloadStatistics(Map<String,Object> query_map);

	int getWeekCountDownloadStatistics(Map<String,Object> query_map);

	int getDayCountDownloadStatistics(Map<String,Object> query_map);

	List<Map<String,Object>> getDownloadHistogramStatistics(Map<String,Object> query_map);

}