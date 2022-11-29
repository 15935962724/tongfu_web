package com.tongfu.service.impl;

import com.tongfu.dao.JournalismDao;
import com.tongfu.entity.Journalism;
import com.tongfu.service.JournalismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class JournalismServiceImpl implements JournalismService {

	@Autowired
	JournalismDao journalismDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return journalismDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Journalism record) {
		return 0;
	}

	@Override
	public int insertSelective(Journalism record) {
		return journalismDao.insertSelective(record);
	}

	@Override
	public Journalism selectByPrimaryKey(Long id) {
		return journalismDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Journalism record) {
		return journalismDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Journalism record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Journalism record) {
		return 0;
	}

	@Override
	public List<Map> getAll(Map<String, Object> query_map) {
		return journalismDao.getAll(query_map);
	}

	@Override
	public int toExamineJournalismCouont(Map<String, Object> query_map) {
		return journalismDao.toExamineJournalismCouont(query_map);
	}

	@Override
	public int toShowJournalismCouont(Map<String, Object> query_map) {
		return journalismDao.toShowJournalismCouont(query_map);
	}

	@Override
	public int hitsJournalismCouont(Map<String, Object> query_map) {
		return journalismDao.hitsJournalismCouont(query_map);
	}

	@Override
	public List<Map> getArticle(Map query_map) {
		return journalismDao.getArticle(query_map);
	}

	@Override
	public List<Map> getbrandConunt(Map query_map) {
		return journalismDao.getbrandConunt(query_map);
	}

	@Override
	public List<Map> getArticleRanking(Map query_map) {
		return journalismDao.getArticleRanking(query_map);
	}
}
