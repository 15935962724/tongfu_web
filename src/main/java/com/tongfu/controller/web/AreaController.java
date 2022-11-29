package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller("webarea")
@RequestMapping("/web/area")
public class AreaController {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	AreaService areaService;

	/**
	 * 查询子集
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryArea")
	@ResponseBody
	public Object queryArea(Long id) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("parent",id==0?"":id);
		List<Area> areaList = (List<Area>) areaService.getAll(map);
		return areaList;
	}


	/**
	 * 三级联动插件
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/getAreas")
	@ResponseBody
	public Map<Long, String>  getAreas( @RequestParam(defaultValue = "0") Long parentId){
		//类别
		Map<String,Object> map = new HashMap<>(	);
		map.put("parent",parentId);
		List<Area> areas= areaService.getAll(map);
		Map<Long, String> options = new HashMap<Long, String>();
		for (Area area : areas) {
			options.put(area.getId(), area.getName());
		}
		return options;
	}




}
