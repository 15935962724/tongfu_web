package com.tongfu.service.impl;

import com.tongfu.dao.MemberDao;
import com.tongfu.entity.Member;
import com.tongfu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberDao;


	@Override
	public int insert(Member record) {
		return 0;
	}

	@Override
	public int insertSelective(Member record) {
		return memberDao.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Member record) {
		return memberDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Member record) {
		return 0;
	}

	@Override
	public Member selectByPrimaryKey(Long id) {
		return memberDao.selectByPrimaryKey(id);
	}

	@Override
	public int getMonthCountMember(Map<String, Object> query_map) {
		return memberDao.getMonthCountMember(query_map);
	}

	@Override
	public int getWeekCountMember(Map<String, Object> query_map) {
		return memberDao.getWeekCountMember(query_map);
	}

	@Override
	public int getDayCountMember(Map<String, Object> query_map) {
		return memberDao.getDayCountMember(query_map);
	}

	@Override
	public List<Member> getAll(Map<String, Object> query_map) {
		return memberDao.getAll(query_map);
	}

	@Override
	public List<Integer> thatyearRegister(Map query_map) {
		return memberDao.thatyearRegister(query_map);
	}

	@Override
	public List<Map> getYearRegister(Map query_map) {
		return memberDao.getYearRegister(query_map);
	}

	@Override
	public List<Integer> getSystemRegister(Map query_map) {
		return memberDao.getSystemRegister(query_map);
	}

	@Override
	public List<Integer> getProvinceRegister(Map query_map) {
		return memberDao.getProvinceRegister(query_map);
	}

	@Override
	public List<Integer> getSourceRegister(Map query_map) {
		return memberDao.getSourceRegister(query_map);
	}

	@Override
	public List<Map> getSystems(Map query_map) {
		return memberDao.getSystems(query_map);
	}

	@Override
	public List<Map> getProvince(Map query_map) {
		return memberDao.getProvince(query_map);
	}

	@Override
	public List<Map> getSource(Map query_map) {
		return memberDao.getSource(query_map);
	}

	@Override
	public List<Map> getCountMemberSource(Map query_map) {
		return memberDao.getCountMemberSource(query_map);
	}
}
