package com.tongfu.service.impl;

import com.tongfu.dao.*;
import com.tongfu.entity.*;
import com.tongfu.service.MemberPointWorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class MemberPointWorkshopServiceImpl implements MemberPointWorkshopService {

	@Autowired
	MemberPointWorkshopDao memberPointWorkshopDao;

	@Autowired
	MemberDao memberDao;

	@Autowired
	WorkshopDao workshopDao;

	@Autowired
	MemberPointLogDao memberPointLogDao;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(MemberPointWorkshop record) {
		return 0;
	}

	@Override
	public int insertSelective(MemberPointWorkshop record) {
		return 0;
	}

	@Override
	public MemberPointWorkshop selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(MemberPointWorkshop record) {
		Member member = memberDao.selectByPrimaryKey(record.getMemberId());
		if (member.getPoint()>record.getPoint()) {
			Long  point = member.getPoint() - record.getPoint();
			member.setPoint(point);
			//此处增加用户的积分记录
			MemberPointLog memberPointLog = new MemberPointLog();
			memberPointLog.setCreateDate(new Date());
			memberPointLog.setIsDeleted(false);
			memberPointLog.setModifyDate(new Date());
			memberPointLog.setType(1);//0收入 1支出
			memberPointLog.setMember(member.getId());
			memberPointLog.setDebit(null);//收入积分
			memberPointLog.setCredit(Long.valueOf(record.getPoint()));//支出积分
			Workshop workshop = workshopDao.selectByPrimaryKey(record.getWorkshopId());
			memberPointLog.setMemo("兑换编号为:"+workshop.getId()+"会议,会议标题为:"+workshop.getTitle()+",扣除积分");
			memberPointLog.setPoint(point);
			memberPointLog.setOrders(null);
			memberPointLogDao.insertSelective(memberPointLog);//插入积分记录完成
		memberDao.updateByPrimaryKeySelective(member);
		}else{
			 record.setStatus(3);
			 record.setMessage("积分不足");
		}
		return memberPointWorkshopDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberPointWorkshop record) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getAll(Map<String, Object> query_map) {
		return memberPointWorkshopDao.getAll(query_map);
	}
}
