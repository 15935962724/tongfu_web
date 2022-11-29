package com.tongfu.service.impl;

import com.tongfu.dao.LearningDao;
import com.tongfu.dao.MessageDao;
import com.tongfu.entity.Learning;
import com.tongfu.entity.Message;
import com.tongfu.service.LearningService;
import com.tongfu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDao messageDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Message record) {
		return 0;
	}

	@Override
	public int insertSelective(Message record) {
		return 0;
	}

	@Override
	public Message selectByPrimaryKey(Long id) {
		return messageDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Message record) {
		return messageDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Message record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Message record) {
		return 0;
	}

	@Override
	public int getMonthCountProductMessage(Map<String, Object> query_map) {
		return messageDao.getMonthCountProductMessage(query_map);
	}

	@Override
	public int getWeekCountProductMessage(Map<String, Object> query_map) {
		return messageDao.getWeekCountProductMessage(query_map);
	}

	@Override
	public int getDayCountProductMessage(Map<String, Object> query_map) {
		return messageDao.getDayCountProductMessage(query_map);
	}

	@Override
	public List<Map<String, Object>> getAll(Map<String, Object> query_map) {
		return messageDao.getAll(query_map);
	}

	@Override
	public List<Map<String, Object>> messageStatisticList(Map<String, Object> query_map) {
		return messageDao.messageStatisticList(query_map);
	}

	@Override
	public Map getAdvice(Map query_map) {
		return messageDao.getAdvice(query_map);
	}
}
