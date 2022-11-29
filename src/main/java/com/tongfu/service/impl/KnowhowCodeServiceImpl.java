package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.dao.KnowHowDao;
import com.tongfu.dao.KnowhowCodeDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.KnowhowCode;
import com.tongfu.service.AdminService;
import com.tongfu.service.KnowhowCodeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service
public class KnowhowCodeServiceImpl implements KnowhowCodeService {

	@Autowired
	KnowhowCodeDao knowhowCodeDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return knowhowCodeDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(KnowhowCode record) {
		return 0;
	}

	@Override
	public int insertSelective(KnowhowCode record) {
		return knowhowCodeDao.insertSelective(record);
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return knowhowCodeDao.insertMap(map);
	}

	@Override
	public KnowhowCode selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(KnowhowCode record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(KnowhowCode record) {
		return 0;
	}

	@Override
	public List<KnowhowCode> getAll(Map<String, Object> query_map) {
		return knowhowCodeDao.getAll(query_map);
	}

    @Override
    public List<Map<String, Object>> getAllMap(Map<String, Object> query_map) {
        return knowhowCodeDao.getAllMap(query_map);
    }
}
