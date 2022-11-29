package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Ad;
import com.tongfu.entity.AdPosition;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/admin/ad")
public class AdController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	AdService adService;

	@Autowired
	AdPositionService adPositionService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private  AdIncomeService adIncomeService;

	//广告图上传路径
	@Value("${ad-image}")
	private String adImage;

	/**
	 * 广告列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title,Long  adPosition,String startDate,String endDate,Long status,Long companyId ) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);
		map.put("adPosition",adPosition);
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
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> all = (List<Map<String,Object>>) adService.getAll(map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);
		List<AdPosition> adPositions = (List<AdPosition>) adPositionService.getAll(map);
		model.addAttribute("adPositions",adPositions);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		model.addAttribute("adPosition",adPosition);
		model.addAttribute("status",status);
		model.addAttribute("companyId",companyId);

		return "admin/ad/list";
	}


	/**
	 * 发布广告页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map<String, Object> product_query_map = new HashMap<>();
		product_query_map.put("type", 1);
		List<AdPosition> adPositions = adPositionService.getAll(null);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
		model.addAttribute("productCategories", productCategories);
		model.addAttribute("adPositions", adPositions);
		return "admin/ad/add";
	}


	/**
	 * 发布广告
	 *
	 * @param ad
	 * @param adImageFile
	 * @return
	 */
	@PostMapping("/save")
	public String save(Ad ad, @RequestParam("adImage") MultipartFile adImageFile) {
		ad.setModifyDate(new Date());
		ad.setCreateDate(new Date());
		ad.setIsDeleted(false);
		ad.setStatus(2l);
		ad.setType(2);
		ad.setPaymentStatus(2l);
		AdPosition adPosition = adPositionService.selectByPrimaryKey(ad.getAdPosition());
		ad.setPrice(new BigDecimal(0));
		//广告图路径
		String ad_image = FileUtils.upload(adImageFile, adImage);
		ad.setPath(ad_image);
		Integer conunt = adService.insertSelective(ad);
		logger.info("发布广告结果:" + conunt);
		return "redirect:list";
	}

	@PostMapping("/update")
	public String update(Ad ad, @RequestParam("adImage") MultipartFile adImageFile) {

		//广告图路径
		if (adImageFile.getSize() > 0) {
			String ad_image = FileUtils.upload(adImageFile, adImage);
			ad.setPath(ad_image);
		}
		Integer conunt = adService.updateByPrimaryKeySelective(ad);
		logger.info("发布广告结果:" + conunt);
		return "redirect:list";
	}

	/**
	 * 发布广告页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model, Long id) {
		Ad ad = adService.selectByPrimaryKey(id);
		Map<String, Object> product_query_map = new HashMap<>();
		product_query_map.put("type", 1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
		List<AdPosition> adPositions = adPositionService.getAll(null);
		model.addAttribute("ad", ad);
		model.addAttribute("productCategories", productCategories);
		model.addAttribute("adPositions", adPositions);

		return "admin/ad/edit";
	}


	/**
	 *
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody JSONObject jsonObject ){
		int deleteByPrimaryKey = adService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}





	@RequestMapping(value = "/check_ad_position", method = RequestMethod.GET)
	@ResponseBody
	public AdPosition checkAdPosition(@RequestParam Long id) {
		return adPositionService.selectByPrimaryKey(id);
	}


	/**
	 * 审核操作
	 * @return
	 */
	@PostMapping("/updateAdStatus")
	@ResponseBody
	public String updateAdStatus(@RequestBody String jsonObject) {
		Ad ad = JSON.parseObject(jsonObject, Ad.class);
		Integer count = adService.updateByPrimaryKeySelective(ad);
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}



}
