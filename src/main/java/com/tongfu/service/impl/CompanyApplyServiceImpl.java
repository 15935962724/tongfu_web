package com.tongfu.service.impl;

import com.tongfu.dao.CompanyApplyMapper;
import com.tongfu.dao.CompanyBillDao;
import com.tongfu.entity.CompanyApply;
import com.tongfu.entity.CompanyBill;
import com.tongfu.service.CompanyApplyService;
import com.tongfu.service.CompanyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CompanyApplyServiceImpl implements CompanyApplyService {

	@Autowired
	private CompanyApplyMapper companyApplyMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(CompanyApply record) {
		return 0;
	}

	@Override
	public int insertSelective(CompanyApply record) {
		return 0;
	}

	@Override
	public CompanyApply selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(CompanyApply record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(CompanyApply record) {
		return 0;
	}

	@Override
	public List<CompanyApply> getAll(Map query_map) {
		return companyApplyMapper.getAll(query_map);
	}


}
