package com.tongfu.service;

import com.tongfu.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

	int insert(Member record);

	int insertSelective(Member record);

	int updateByPrimaryKeySelective(Member record);

	int updateByPrimaryKey(Member record);

	Member selectByPrimaryKey(Long id);

	int getMonthCountMember(Map<String, Object> query_map);

	int getWeekCountMember(Map<String, Object> query_map);

	int getDayCountMember(Map<String, Object> query_map);

	List<Member> getAll(Map<String,Object> query_map);

	List<Integer> thatyearRegister(Map query_map);

	List<Map> getYearRegister(Map query_map);

	List<Integer> getSystemRegister(Map query_map);

	List<Integer> getProvinceRegister(Map query_map);

	List<Integer> getSourceRegister(Map query_map);

	List<Map> getSystems(Map query_map);

	List<Map> getProvince(Map query_map);

	List<Map> getSource(Map query_map);

	List<Map> getCountMemberSource(Map query_map);//用户来源分析

}