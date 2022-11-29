package com.tongfu.service.impl;

import com.tongfu.config.ShiroConfig;
import com.tongfu.dao.*;
import com.tongfu.entity.Admin;
import com.tongfu.entity.AdminCompanyRole;
import com.tongfu.entity.Company;
import com.tongfu.entity.CompanyRole;
import com.tongfu.service.CompanyService;
import com.tongfu.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	AdminDao adminDao;

	@Autowired
	CompanyDao companyDao;

	@Autowired
	CompanyRoleDao companyRoleDao;

	@Autowired
	AdminCompanyRoleDao adminCompanyRoleDao;

	@Autowired
	CompanyRoleAuthorityDao companyRoleAuthorityDao;

	@Value("${company-role-authority}")
	private String company_role_authority;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Company record) {
		return 0;
	}

	@Override
	@Transactional
	public int insertSelective(Map<String,Object> query_map) {
		Integer count = 0;
		try {
			//		创建供应商公司信息
			Company company = (Company) query_map.get("company");
			company.setCreateDate(new Date());
			company.setModifyDate(new Date());
			company.setStatus(1L);
			company.setLogo("");
			company.setLicense("");
			company.setBackgroundimg("");
			company.setStartDate(new Date());
					Scanner sc = new Scanner(System.in);
					Calendar curr = Calendar.getInstance();
					curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
					Date endDate=curr.getTime();
					company.setEndDate(endDate);
			count = companyDao.insertSelective(company);
			System.out.println("companyId:"+company.getId());
			String admin_name = (String) query_map.get("adminName");
			String username = (String) query_map.get("username");
			//创建供应商账号信息
			Admin admin = new Admin();
			admin.setModifyDate(new Date());
			admin.setCreateDate(new Date());
			admin.setIsEnabled(true);
			admin.setIsLocked(false);
			admin.setIsDeleted(false);
			admin.setIsSystem(true);
			admin.setName(admin_name);
			admin.setType(1l);

			admin.setCompanyId(company.getId());
			admin.setUsername(username);
			String encodedPassword = ShiroConfig.shiroEncryption("111111",username);
			System.out.println(admin.getUsername() + "的密码是：" + encodedPassword);
			admin.setPassword(encodedPassword);
			admin.setLoginFailureCount(0);
			admin.setEmail("");
			count = adminDao.insertSelective(admin);
			System.out.println("adminId:"+admin.getId());
//		给供应商创建角色
			CompanyRole companyRole = new CompanyRole();
			companyRole.setCreateDate(new Date());
			companyRole.setModifyDate(new Date());
			companyRole.setCompanyId(company.getId());
			companyRole.setName("管理员");
			companyRole.setIsSystem(true);
			companyRole.setDescription(company.getName()+ " 管理员");
			count = companyRoleDao.insertSelective(companyRole);
			System.out.println("companyRoleId:"+companyRole.getId());

			AdminCompanyRole adminCompanyRole = new AdminCompanyRole();
			adminCompanyRole.setAdmins(admin.getId());
			adminCompanyRole.setCompanyRole(companyRole.getId());
			count = adminCompanyRoleDao.insertSelective(adminCompanyRole);

			String []companyRoleAuthoritys = company_role_authority.split(",");
			System.out.println(companyRoleAuthoritys);
			query_map.put("companyRoleAuthoritys",companyRoleAuthoritys);
			query_map.put("companyRoleId", companyRole.getId());
			count = companyRoleAuthorityDao.insertMap(query_map);
		}catch (RuntimeException e){
			System.out.println("事务回滚啦");
			count = 0;
			throw new RuntimeException("抛异常了");
		}

		return count ;
	}

	@Override
	public Company selectByPrimaryKey(Long id) {
		return companyDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Company record) {
		return companyDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Company record) {
		return 0;
	}

	@Override
	public Long getCompanyId(){

		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Admin admin = (Admin) subject.getPrincipal();
			if (admin != null) {
//				Company company = companyDao.selectByPrimaryKey(admin.getCompanyId());
//				if (company==null){
//					return null;
//				}
				return  admin.getCompanyId();
			}else {
				return null;
			}
		}else{
			return null;
		}


	}

	@Override
	public Company selectByAdminId(Long adminId) {
		Company company = companyDao.selectByAdminId(adminId);
		return company;
	}

	@Override
	public Company getCompany() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Admin admin = (Admin) subject.getPrincipal();
			if (admin != null) {
				Company company = companyDao.selectByPrimaryKey(admin.getCompanyId());
				if (company==null){
					return null;
				}
				return  company;
			}else {
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public List<Map<String,Object>> getAll(Map<String, Object> query_map) {
		return companyDao.getAll(query_map);
	}

	@Override
	public List<Map> getVisitsRanking(Map query_map) {
		return companyDao.getVisitsRanking(query_map);
	}


}
