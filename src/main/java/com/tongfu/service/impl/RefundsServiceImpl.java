package com.tongfu.service.impl;

import com.tongfu.dao.RefundsDao;
import com.tongfu.entity.Refunds;
import com.tongfu.service.RefundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class RefundsServiceImpl implements RefundsService {

	@Autowired
	private RefundsDao refundsDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Refunds record) {
		return 0;
	}

	@Override
	public int insertSelective(Refunds record) {
		return 0;
	}

	@Override
	public Refunds selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Refunds record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Refunds record) {
		return 0;
	}

	@Override
	public List<BigDecimal> thatYearReturnsAmount(Map query_map) {
		return refundsDao.thatYearReturnsAmount(query_map);
	}

	@Override
	public List<Map> getYearReturnsAmount(Map query_map) {
		return refundsDao.getYearReturnsAmount(query_map);
	}

	@Override
	public List<Map> getRefundsCompany(Map query_map) {
		return refundsDao.getRefundsCompany(query_map);
	}
}
