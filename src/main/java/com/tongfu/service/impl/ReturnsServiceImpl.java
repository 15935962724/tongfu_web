package com.tongfu.service.impl;

import com.tongfu.dao.AboutusDao;
import com.tongfu.dao.ReturnsDao;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.Returns;
import com.tongfu.service.AboutusService;
import com.tongfu.service.ReturnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class ReturnsServiceImpl implements ReturnsService {

	@Autowired
	private ReturnsDao returnsDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Returns record) {
		return 0;
	}

	@Override
	public int insertSelective(Returns record) {
		return 0;
	}

	@Override
	public Returns selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Returns record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Returns record) {
		return 0;
	}

	@Override
	public List<Map> getYearReturns(Map query_map) {
		return returnsDao.getYearReturns(query_map);
	}

	@Override
	public List<Integer> thatYearReturns(Map query_map) {
		return returnsDao.thatYearReturns(query_map);
	}

	@Override
	public List<Map> getReturnsCompany(Map query_map) {
		return returnsDao.getReturnsCompany(query_map);
	}


}
