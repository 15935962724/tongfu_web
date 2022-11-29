package com.tongfu.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tongfu.entity.Dog;

public interface DogService {
	
	public Dog selectByPrimaryKey(Integer age);

	public int deleteDogByAge(Integer age);

	public int insertDog(Dog dog);

	public int updateDog(Dog dog);
	
	
}