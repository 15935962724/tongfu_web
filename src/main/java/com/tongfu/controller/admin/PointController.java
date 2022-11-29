package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Ad;
import com.tongfu.entity.MemberPointWorkshop;
import com.tongfu.entity.Setting;
import com.tongfu.service.AdminService;
import com.tongfu.service.MemberPointWorkshopService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import com.tongfu.util.SettingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/point")
public class PointController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberPointWorkshopService memberPointWorkshopService;

	/**
	 * setting 设置
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model) {
		Map<String,Object> map = new HashMap<>(	);
		Setting setting = SettingUtils.get();
		model.addAttribute("setting",setting);
		return "admin/point/edit";
	}


	@PostMapping("/update")
	public String update(Model model, Setting setting) {
		SettingUtils.set(setting);
		return "redirect:edit";
	}


	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,String username,Integer type ) {
		Map<String,Object> query_map = new HashMap<>(	);
		query_map.put("username",username);
		query_map.put("type",type);

		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> categoryList = (List<Map<String,Object>>) memberPointWorkshopService.getAll(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(categoryList);
		model.addAttribute("page",pageInfo);
		model.addAttribute("username",username);
		model.addAttribute("type",type);
		return "admin/point/list";
	}


	/**
	 * 审核操作
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updatePointStatus")
	@ResponseBody
	public String updatePointStatus(@RequestBody String jsonObject) {
		MemberPointWorkshop memberPointWorkshop = JSON.parseObject(jsonObject, MemberPointWorkshop.class);
		Integer count = memberPointWorkshopService.updateByPrimaryKeySelective(memberPointWorkshop);
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}


}
