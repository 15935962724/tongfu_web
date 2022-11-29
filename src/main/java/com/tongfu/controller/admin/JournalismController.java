package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Company;
import com.tongfu.entity.Journalism;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.JournalismService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/journalism")
public class JournalismController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JournalismService journalismService ;

	@Value("${journalism-logo}")
	private String journalism_logo;

	/**
	 * 新闻列表
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param title
	 * @param keyword
	 * @param singledaterange
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title,String keyword,String startDate,String endDate,Long companyId,Long status) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);
		map.put("keyword",keyword);
		map.put("status",status);
		map.put("companyId",companyId);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}
		Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map> all = journalismService.getAll(map);
		PageInfo<Map> pageInfo = new PageInfo<Map>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		model.addAttribute("keyword",keyword);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("status",status);
		model.addAttribute("companyId",companyId);
		return "admin/journalism/list";
	}

	/**
	 * 预览
	 * @param model
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model model,Long id) {
		Journalism journalism = journalismService.selectByPrimaryKey(id);
		Company company = companyService.selectByPrimaryKey(journalism.getCompanyId());
		model.addAttribute("journalism",journalism);
		model.addAttribute("company",company);
		return "admin/journalism/view";
	}

	/**
	 * 审核
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updateJournalismStatus")
	@ResponseBody
	public String updateJournalismStatus(@RequestBody String jsonObject) {
		Journalism journalism = JSON.parseObject(jsonObject, Journalism.class);
		Integer count = journalismService.updateByPrimaryKeySelective(journalism);
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}


}
