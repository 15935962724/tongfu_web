package com.tongfu.controller.admin;

import com.tongfu.entity.Ad;
import com.tongfu.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tongfu.entity.Dog;
import com.tongfu.service.DogService;

@Controller
public class DogController {


	@Autowired
	DogService dogService;

	@Autowired
	AdService adService;

	@ResponseBody
	@RequestMapping( "/ad/{id}")
	public Ad getAd(@PathVariable("id") Long id) {
		return adService.selectByPrimaryKey(id);
	}

	//	@ResponseBody
//	@RequestMapping( "/dog/{age}")
//	public Dog getDog(@PathVariable("age") Integer age) {
//		return dogService.selectByPrimaryKey(age);
//	}

	
	@ResponseBody
	@RequestMapping( "/dog")
	public Dog insertDog(Dog dog) {
		 dogService.insertDog(dog);
		 return dog;
		
	}







	 
	 
}
