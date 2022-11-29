package com.tongfu.dao;

import java.util.List;


import com.tongfu.entity.User;
import org.springframework.stereotype.Repository;

//@Mapper
@Repository
public interface UserDao {




    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> queryList();



}