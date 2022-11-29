package com.tongfu.service.impl;

import com.tongfu.dao.AreaDao;
import com.tongfu.dao.CompanyBillDao;
import com.tongfu.entity.Area;
import com.tongfu.entity.CompanyBill;
import com.tongfu.service.AreaService;
import com.tongfu.service.CompanyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CompanyBillServiceImpl implements CompanyBillService {

	@Autowired
	private CompanyBillDao companyBillDao;


	@Override
	public int insert(CompanyBill record) {
		return 0;
	}

	@Override
	public int insertSelective(CompanyBill record) {
		return 0;
	}

	@Override
	public List<Map> getYearRegister(Map query_map) {
		return companyBillDao.getYearRegister(query_map);
	}

	@Override
	public List<Integer> thatYearRegister(Map query_map) {
		return companyBillDao.thatYearRegister(query_map);
	}
}
