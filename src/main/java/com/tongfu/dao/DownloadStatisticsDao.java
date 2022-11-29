package com.tongfu.dao;

import com.tongfu.entity.DownloadStatistics;

import java.util.List;
import java.util.Map;

public interface DownloadStatisticsDao {
    int insert(DownloadStatistics record);

    int insertSelective(DownloadStatistics record);

    int getMonthCountDownloadStatistics(Map<String,Object> query_map);

    int getWeekCountDownloadStatistics(Map<String,Object> query_map);

    int getDayCountDownloadStatistics(Map<String,Object> query_map);

    List<Map<String,Object>> getDownloadHistogramStatistics(Map<String,Object> query_map);


}