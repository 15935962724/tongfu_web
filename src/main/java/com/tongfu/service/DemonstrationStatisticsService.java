package com.tongfu.service;

import com.tongfu.entity.DemonstrationStatistics;

import java.util.List;
import java.util.Map;

public interface DemonstrationStatisticsService {

	int insert(DemonstrationStatistics record);

	int insertSelective(DemonstrationStatistics record);

	int deleteByPrimaryKey(Long id);

	DemonstrationStatistics selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(DemonstrationStatistics record);

	int updateByPrimaryKey(DemonstrationStatistics record);

	int getMonthCountDemonstrationStatistics(Map<String,Object> query_map);

	int getWeekCountDemonstrationStatistics(Map<String,Object> query_map);

	int getDayCountDemonstrationStatistics(Map<String,Object> query_map);

	List<Map<String,Object>> getAll(Map<String,Object> query_map);

	List<Map<String,Object>> demonstrationStatisticList(Map<String,Object> query_map);

	List<Map> getYears(Map query_map);

	List<Integer> thatYear(Map query_map);

	List<Map> getProductName(Map query_map);

	List<Integer> getProductNameApplyTrial(Map query_map);

	List<Map> getProvince(Map query_map);

	List<Integer> getProvinceApplyTrial(Map query_map);

	List<Map> getDemonstrationStatisticStatisics(Map query_map);

	Map getApplyDemonstration(Map query_map);//申请演示

	List<Map> getDemonstrationStatisics(Map query_map); //产品申请演示分析  产品申请试用分析   产品下载分析


}