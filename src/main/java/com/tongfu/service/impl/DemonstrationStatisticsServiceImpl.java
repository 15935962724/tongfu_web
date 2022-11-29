package com.tongfu.service.impl;

import com.tongfu.dao.DemonstrationStatisticsDao;
import com.tongfu.entity.DemonstrationStatistics;
import com.tongfu.service.DemonstrationStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class DemonstrationStatisticsServiceImpl implements DemonstrationStatisticsService {

	@Autowired
	DemonstrationStatisticsDao demonstrationStatisticsDao;

	@Override
	public int insert(DemonstrationStatistics record) {
		return 0;
	}

	@Override
	public int insertSelective(DemonstrationStatistics record) {
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public DemonstrationStatistics selectByPrimaryKey(Long id) {
		return demonstrationStatisticsDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DemonstrationStatistics record) {
		return demonstrationStatisticsDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DemonstrationStatistics record) {
		return 0;
	}

	@Override
	public int getMonthCountDemonstrationStatistics(Map<String, Object> query_map) {
		return demonstrationStatisticsDao.getMonthCountDemonstrationStatistics(query_map);
	}

	@Override
	public int getWeekCountDemonstrationStatistics(Map<String, Object> query_map) {
		return demonstrationStatisticsDao.getWeekCountDemonstrationStatistics(query_map);
	}

	@Override
	public int getDayCountDemonstrationStatistics(Map<String, Object> query_map) {
		return demonstrationStatisticsDao.getDayCountDemonstrationStatistics(query_map);
	}

	@Override
	public List<Map<String,Object>> getAll(Map<String, Object> query_map) {
		return demonstrationStatisticsDao.getAll(query_map);
	}

	@Override
	public List<Map<String, Object>> demonstrationStatisticList(Map<String, Object> query_map) {
		return demonstrationStatisticsDao.demonstrationStatisticList(query_map);
	}

	@Override
	public List<Map> getYears(Map query_map) {
		return demonstrationStatisticsDao.getYears(query_map);
	}

	@Override
	public List<Integer> thatYear(Map query_map) {
		return demonstrationStatisticsDao.thatYear(query_map);
	}

	@Override
	public List<Map> getProductName(Map query_map) {
		return demonstrationStatisticsDao.getProductName(query_map);
	}

	@Override
	public List<Integer> getProductNameApplyTrial(Map query_map) {
		return demonstrationStatisticsDao.getProductNameApplyTrial(query_map);
	}

	@Override
	public List<Map> getProvince(Map query_map) {
		return demonstrationStatisticsDao.getProvince(query_map);
	}

	@Override
	public List<Integer> getProvinceApplyTrial(Map query_map) {
		return demonstrationStatisticsDao.getProvinceApplyTrial(query_map);
	}

	@Override
	public List<Map> getDemonstrationStatisticStatisics(Map query_map) {
		return demonstrationStatisticsDao.getDemonstrationStatisticStatisics(query_map);
	}

	@Override
	public Map getApplyDemonstration(Map query_map) {
		return demonstrationStatisticsDao.getApplyDemonstration(query_map);
	}

	@Override
	public List<Map> getDemonstrationStatisics(Map query_map) {
		return demonstrationStatisticsDao.getDemonstrationStatisics(query_map);
	}
}
