package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Brand;
import com.tongfu.entity.Workshop;
import com.tongfu.service.AdminService;
import com.tongfu.service.BrandService;
import com.tongfu.service.WorkshopService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/workshop")
public class WorkshopController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private WorkshopService workshopService;

	@Value("${workshop-logo}")
	private String uploadWorkshopLogo;



	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title,String startDate,String endDate,String holdingstartDate,String holdingendDate ,Boolean isCarefully) {
		Map<String,Object> map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		map.put("title",title);

		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		if (holdingstartDate!=null&&!holdingstartDate.equals("")){
			Date holdingstart_Date = DateUtil.getStringtoDate( holdingstartDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("holdingstartDate",holdingstart_Date);
		}
		if (holdingendDate!=null&&!holdingendDate.equals("")){
			Date holdingend_Date = DateUtil.getStringtoDate( holdingendDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("holdingendDate",holdingend_Date);
		}

		map.put("isCarefully",isCarefully);
		Page<Workshop> page  = PageHelper.startPage(pageNum,pageSize);

		List<Workshop> all = (List<Workshop>) workshopService.getAll(map);
		PageInfo<Workshop> pageInfo = new PageInfo<Workshop>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("holdingstartDate",holdingstartDate);
		model.addAttribute("holdingendDate",holdingendDate);
		model.addAttribute("isCarefully",isCarefully);
		return "admin/workshop/list";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
//		model.addAttribute("admins","");
		return "admin/workshop/add";
	}


	/**
	 * 添加研讨会
	 * @param model
	 * @param workshop
	 * @param file
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Workshop workshop,@RequestParam("workShopLogo") MultipartFile file) {
		workshop.setModifyDate(new Date());
		workshop.setCreateDate(new Date());
		String workshop_logo=   FileUtils.upload(file, uploadWorkshopLogo);
		workshop.setLogo(workshop_logo);

		Integer count = workshopService.insertSelective(workshop);

		logger.info("添加研讨会:"+count);
		return "redirect:list";
	}


	/**
	 * 编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Brand brand = brandService.selectByPrimaryKey(id);
		model.addAttribute("brand",brand);
		return "admin/workshop/edit";
	}

	/**
	 *
	 * @param model
	 * @param brand
	 * @param file
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,Brand brand,@RequestParam("brandLogo") MultipartFile file) {
//		String staticPath= ClassUtils.getDefaultClassLoader().getResource("static").getPath();
//		String updateUrl = staticPath+path;
//		if (file.getSize()>0){
//			Boolean fals = FileUtils.upload(file,updateUrl,file.getOriginalFilename());
//			String logo = fals?path+file.getOriginalFilename():"";
//			System.out.println("brnadLogo图地址:"+logo);
//			brand.setLogo(logo);
//		}
//        brand.setModifyDate(new Date());
		Integer count = brandService.updateByPrimaryKeySelective(brand);

		logger.info("添加品牌结果:"+count);
		return "redirect:list";
	}




}
