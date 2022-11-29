package com.tongfu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tongfu.dao.DogDao;
import com.tongfu.entity.Dog;
import com.tongfu.service.DogService;


@Service
public class DogServiceImpl implements DogService {

	@Autowired
	DogDao dogDao;
	
	@Override
	public Dog selectByPrimaryKey(Integer age) {
		// TODO Auto-generated method stub
		return dogDao.selectByPrimaryKey(age);
	}

	@Override
	public int deleteDogByAge(Integer age) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertDog(Dog dog) {
		// TODO Auto-generated method stub
		return dogDao.insertDog(dog);
	}

	@Override
	public int updateDog(Dog dog) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
}
