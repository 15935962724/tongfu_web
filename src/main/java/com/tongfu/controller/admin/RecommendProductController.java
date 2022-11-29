package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.RecommendProduct;
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
@RequestMapping("/admin/recommendProduct")
public class RecommendProductController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private RecommendProductService recommendProductService;

	@Autowired
	private AdPositionService adPositionService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;


	//广告图上传路径
	@Value("${ad-image}")
	private String adImage;

	//	微信appid
	@Value("${appid}")
	private String appid;

	//	商户号
	@Value("${mch_id}")
	private String mch_id;

	//	商户key
	@Value("${key}")
	private String key;




	/**
	 * 广告列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
					   String title, String startDate, String endDate,Long status,Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("title", title);
		map.put("status",status);
		map.put("companyId",companyId);
		if (startDate != null && !startDate.equals("")) {
			Date start_Date = DateUtil.getStringtoDate(startDate + " 00:00:00", "MM/dd/yyyy HH:mm:ss");
			map.put("startDate", start_Date);
		}
		if (endDate != null && !endDate.equals("")) {
			Date end_Date = DateUtil.getStringtoDate(endDate + " 23:59:59", "MM/dd/yyyy HH:mm:ss");
			map.put("endDate", end_Date);
		}
		Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> all = (List<Map<String, Object>>) recommendProductService.getAll(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);
		model.addAttribute("page", pageInfo);
		model.addAttribute("title", title);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("companyId",companyId);
		model.addAttribute("status",status);
		return "admin/recommendProduct/list";
	}


	/**
	 * 审核
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updatecRecommendProductStatus")
	@ResponseBody
	public String updatecRecommendProductStatus(@RequestBody String jsonObject) {
		RecommendProduct recommendProduct = JSON.parseObject(jsonObject, RecommendProduct.class);
		Integer count = recommendProductService.updateByPrimaryKeySelective(recommendProduct);
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}


}
