package com.tongfu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tongfu.entity.Dog;
import com.tongfu.entity.User;

//@Mapper
public interface DogDao {


	@Select("select * from dog where age=#{age}")
	public Dog selectByPrimaryKey(Integer age);

	@Delete("delete from dog where age = #{age}")
	public int deleteDogByAge(Integer age);

	@Options(useGeneratedKeys=true,keyProperty="age")
	@Insert("insert into dog(name) values (#{name})")
	public int insertDog(Dog dog);

	@Update("update dog set name = #{name} where age = #{age}")
	public int updateDog(Dog dog);

}