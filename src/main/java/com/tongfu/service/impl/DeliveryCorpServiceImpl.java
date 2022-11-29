package com.tongfu.service.impl;

import com.tongfu.dao.AreaDao;
import com.tongfu.dao.DeliveryCorpDao;
import com.tongfu.entity.Area;
import com.tongfu.entity.DeliveryCorp;
import com.tongfu.service.AreaService;
import com.tongfu.service.DeliveryCorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class DeliveryCorpServiceImpl implements DeliveryCorpService {

	@Autowired
	DeliveryCorpDao deliveryCorpDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(DeliveryCorp record) {
		return 0;
	}

	@Override
	public int insertSelective(DeliveryCorp record) {
		return 0;
	}

	@Override
	public DeliveryCorp selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(DeliveryCorp record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(DeliveryCorp record) {
		return 0;
	}

	@Override
	public List<DeliveryCorp> getAll(Map<String, Object> query_map) {
		return deliveryCorpDao.getAll(query_map);
	}
}
