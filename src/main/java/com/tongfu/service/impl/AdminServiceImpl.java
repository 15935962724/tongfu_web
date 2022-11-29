package com.tongfu.service.impl;

import com.tongfu.dao.AdminDao;
import com.tongfu.entity.Admin;
import com.tongfu.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return adminDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Admin record) {
		return adminDao.insert(record);
	}

	@Override
	public int insertSelective(Admin record) {
		return adminDao.insertSelective(record);
	}

	@Override
	public Admin selectByPrimaryKey(Long id) {
		return adminDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Admin record) {
		return adminDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Admin record) {
		return 0;
	}

	@Override
	public int updateAdmin(Admin record) {
		return adminDao.updateAdmin(record);
	}

	@Override
	public Admin selectByUserName(String userName) {
		return adminDao.selectByUserName(userName);
	}

	@Override
	public Collection<Admin> getAll(Map<String, Object> query_map) {
		return adminDao.getAll(query_map);
	}

	@Override
	public Admin getCurrent() {

		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
//			String username  =  subject.getPrincipals().toString();
//			if (username != null) {
//				return adminDao.selectByUserName(username);
//			}
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			if (admin!=null){
				return admin;
			}
		}
		return null;

	}

	@Override
	public Admin getCompanyAdmin(Map<String, Object> query_map) {
		return adminDao.getCompanyAdmin(query_map);
	}

	@Override
	public List<Map> getCompanyAdminAll(Map query_map) {
		return adminDao.getCompanyAdminAll(query_map);
	}
}
