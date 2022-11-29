package com.tongfu.controller.admin;

import com.tongfu.entity.Area;
import com.tongfu.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/area")
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
