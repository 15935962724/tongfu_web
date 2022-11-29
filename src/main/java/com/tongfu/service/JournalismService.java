package com.tongfu.service;

import com.tongfu.entity.Journalism;

import java.util.List;
import java.util.Map;

public interface JournalismService {

	int deleteByPrimaryKey(Long id);

	int insert(Journalism record);

	int insertSelective(Journalism record);

	Journalism selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Journalism record);

	int updateByPrimaryKeyWithBLOBs(Journalism record);

	int updateByPrimaryKey(Journalism record);

	List<Map> getAll(Map<String,Object> query_map);

	int toExamineJournalismCouont(Map<String,Object> query_map);

	int toShowJournalismCouont(Map<String,Object> query_map);

	int hitsJournalismCouont(Map<String,Object> query_map);

	List<Map> getArticle(Map query_map);

	List<Map> getbrandConunt(Map query_map);

	List<Map> getArticleRanking(Map query_map);


}