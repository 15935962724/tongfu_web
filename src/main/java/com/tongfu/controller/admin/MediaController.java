package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Company;
import com.tongfu.entity.Media;
import com.tongfu.service.*;
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
@RequestMapping("/admin/media")
public class MediaController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	CompanyService companyService;

	@Autowired
	AdService adService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private MediaService mediaService;

	//媒体报道logo传路径
	@Value("${media-logo}")
	private String mediaLogo;

	/**
	 * 媒体报道列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title,Long status ,Long companyId,String startDate,String endDate) {
			Map<String,Object> map = new HashMap<>(	);
			map.put("title",title);
			map.put("companyId",companyId);
			map.put("status",status);
			if (startDate!=null&&!startDate.equals("")){
				Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
				map.put("startDate",start_Date);
			}
			if (endDate!=null&&!endDate.equals("")){
				Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
				map.put("endDate",end_Date);
			}
		Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map> all = mediaService.getAll(map);
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
		model.addAttribute("status",status);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("companyId",companyId);
		return "admin/media/list";
	}


	/**
	 * 预览
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model model,Long id) {
		Media media = mediaService.selectByPrimaryKey(id);
		Company company = companyService.selectByPrimaryKey(media.getCompanyId());
		model.addAttribute("media",media);
		model.addAttribute("company",company);
		return "admin/media/view";
	}

	/**
	 * 审核
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updateMediaStatus")
	@ResponseBody
	public String updateMediaStatus(@RequestBody String jsonObject) {
		Media media = JSON.parseObject(jsonObject, Media.class);
		Integer count =  mediaService.updateByPrimaryKeySelective(media);
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}



}
