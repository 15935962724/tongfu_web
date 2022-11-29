package com.tongfu.dao;

import com.tongfu.entity.MemberPointLog;

public interface MemberPointLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(MemberPointLog record);

    int insertSelective(MemberPointLog record);

    MemberPointLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberPointLog record);

    int updateByPrimaryKey(MemberPointLog record);
}