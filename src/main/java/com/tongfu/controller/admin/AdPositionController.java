package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/admin/adPosition")
public class AdPositionController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	AdPositionService adPositionService;



	/**
	 * 广告列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,
					   @RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   String title,Long  adPosition,String startDate,String endDate) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);
		map.put("adPosition",adPosition);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}
		Page<AdPosition> page  = PageHelper.startPage(pageNum,pageSize);
		List<AdPosition> all = (List<AdPosition>) adPositionService.getAll(map);
		PageInfo<AdPosition> pageInfo = new PageInfo<AdPosition>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		model.addAttribute("adPosition",adPosition);
		return "admin/adPosition/list";
	}

	@RequestMapping("/add")
	public String add(Model model) {
		return "admin/adPosition/add";
	}

	/**
	 * 添加广告位
	 * @param model
	 * @param adPosition
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model, AdPosition adPosition) {
		adPosition.setModifyDate(new Date());
		adPosition.setCreateDate(new Date());
		Integer count = adPositionService.insertSelective(adPosition);
		logger.info("添加广告位结果:"+count);
		return "redirect:list";
	}


	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		AdPosition adPosition = adPositionService.selectByPrimaryKey(id);
		model.addAttribute("adPosition",adPosition);
		return "admin/adPosition/edit";
	}

	@PostMapping("/update")
	public String update(Model model, AdPosition adPosition) {
		adPosition.setModifyDate(new Date());
		Integer count = adPositionService.updateByPrimaryKeySelective(adPosition);
		logger.info("编辑广告位结果:"+count);
		return "redirect:list";
	}

	@RequestMapping("/delete")
	public String delete(Model model,Long id) {
		adPositionService.deleteByPrimaryKey(id);
//		model.addAttribute("adPosition",adPosition);
		return "redirect:list";
	}


}
