package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Area;
import com.tongfu.entity.Hospital;
import com.tongfu.entity.HospitalCategory;
import com.tongfu.entity.HospitalRank;
import com.tongfu.service.AreaService;
import com.tongfu.service.HospitalCategoryService;
import com.tongfu.service.HospitalRankService;
import com.tongfu.service.HospitalService;
import com.tongfu.util.ResultUtil;
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
@RequestMapping("/admin/hospital")
public class HospitalController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	HospitalService hospitalService;

	@Autowired
	HospitalRankService hospitalRankService;

	@Autowired
	HospitalCategoryService hospitalCategoryService;

	@Autowired
	AreaService areaService;


	/**
	 * 医院列表
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   String name,Long hospitalCategoryId,Long  hospitalRankId) {
		Map<String,Object> query_map = new HashMap<>(	);
		query_map.put("name",name);
		query_map.put("hospitalRankId",hospitalRankId);
		query_map.put("hospitalCategoryId",hospitalCategoryId);
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> hospitals = (List<Map<String,Object>>) hospitalService.getAll(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(hospitals);
		model.addAttribute("page",pageInfo);
		model.addAttribute("name",name);

		List<HospitalRank> hospitalRanks = hospitalRankService.getAll(null);
		List<HospitalCategory> hospitalCategories = hospitalCategoryService.getAll(null);
		model.addAttribute("hospitalRanks",hospitalRanks);
		model.addAttribute("hospitalCategories",hospitalCategories);

		model.addAttribute("hospitalRankId",hospitalRankId);
		model.addAttribute("hospitalCategoryId",hospitalCategoryId);

		return "admin/hospital/list";
	}



	/**
	 * 创建医院页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {

		List<HospitalRank> hospitalRanks = hospitalRankService.getAll(null);
		List<HospitalCategory> hospitalCategories = hospitalCategoryService.getAll(null);
		model.addAttribute("hospitalRanks",hospitalRanks);
		model.addAttribute("hospitalCategories",hospitalCategories);

		return "admin/hospital/add";
	}


	/**
	 * 保存医院
	 * @param model
	 * @param hospital
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model,Hospital hospital ,Long areaId ) {
		hospital.setCreateDate(new Date());
		hospital.setAreaId(areaId);
		Integer count = hospitalService.insertSelective(hospital);
		return "redirect:list";
	}



	/**
	 * 编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Hospital hospital = hospitalService.selectByPrimaryKey(id);
		Area area = areaService.selectByPrimaryKey(hospital.getAreaId());
		List<HospitalRank> hospitalRanks = hospitalRankService.getAll(null);
		List<HospitalCategory> hospitalCategories = hospitalCategoryService.getAll(null);
		model.addAttribute("hospitalRanks",hospitalRanks);
		model.addAttribute("hospitalCategories",hospitalCategories);
		model.addAttribute("hospital",hospital);
		model.addAttribute("area",area);
		return "admin/hospital/edit";
	}

	/**
	 * @param model
	 * @param hospital
	 * @param areaId
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,Hospital hospital,Long areaId) {
		if (areaId!=null){
			hospital.setAreaId(areaId);
		}
		Integer count = hospitalService.updateByPrimaryKeySelective(hospital);
		return "redirect:list";
	}

	/**
     * 删除
	 * @param params
     * @return
     */
	@PostMapping("/deleteHospital")
	@ResponseBody
	public Object deletePaymentMethod(@RequestBody String params) {

		System.out.println("》》》"+params);
		Hospital hospital  = JSONObject.parseObject(params,Hospital.class);
		Integer deleteHospital = hospitalService.deleteByPrimaryKey(hospital.getId());
		if (deleteHospital>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}





}
