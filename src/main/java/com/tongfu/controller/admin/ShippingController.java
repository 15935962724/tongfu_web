package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.dao.ShippingDao;
import com.tongfu.entity.*;
import com.tongfu.service.AreaService;
import com.tongfu.service.ShippingService;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/admin/shipping")
public class ShippingController {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	AreaService areaService;

	@Autowired
	ShippingService shippingService;


	//发货
	@PostMapping("/save")
	@ResponseBody
	public Object updateProduct(@RequestBody String params) {
		return  shippingService.insertSelective(params);
	}





}
