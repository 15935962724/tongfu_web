package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Brand;
import com.tongfu.service.AdminService;
import com.tongfu.service.BrandService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("webbrand")
@RequestMapping("/web/brand")
public class BrandController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private BrandService brandService;

	@Value("${upload-path}")
	private String path;

	/**
	 * 附件上传路径
	 */
	@Value("${file-web-path}")
	private String fileWebPath;

	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,String name) {
		Map<String,Object> map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		map.put("adminid",admin.getId());
		map.put("name",name);
		Page<Brand> page  = PageHelper.startPage(pageNum,pageSize);

		List<Brand> all = (List<Brand>) brandService.getAll(map);
		PageInfo<Brand> pageInfo = new PageInfo<Brand>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("name",name);
		return "web/brand/list";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
//		model.addAttribute("admins","");
		return "web/brand/add";
	}


	/**
	 * 添加品牌
	 * @param model
	 * @param brand
	 * @param file
	 * @return
	 */

	@PostMapping("/save")
	public String save(Model model,Brand brand,@RequestParam("brandLogo") MultipartFile file) {
		String updateUrl = fileWebPath+path;
		Admin admin = adminService.getCurrent();
		brand.setModifyDate(new Date());
		brand.setCreateDate(new Date());
		brand.setIsDeleted(false);
		brand.setType(1);
		brand.setAdminId(admin.getId());
		Boolean fals = FileUtils.upload(file,updateUrl,file.getOriginalFilename());
		String logo = fals?path+file.getOriginalFilename():"";
		System.out.println("brnadLogo图地址:"+logo);
		brand.setLogo(logo);
		Integer count = brandService.insertSelective(brand);

		logger.info("添加品牌结果:"+count);
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
		return "web/brand/edit";
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
		String updateUrl = fileWebPath+path;
		if (file.getSize()>0){
			Boolean fals = FileUtils.upload(file,updateUrl,file.getOriginalFilename());
			String logo = fals?path+file.getOriginalFilename():"";
			System.out.println("brnadLogo图地址:"+logo);
			brand.setLogo(logo);
		}
        brand.setModifyDate(new Date());
		Integer count = brandService.updateByPrimaryKeySelective(brand);

		logger.info("添加品牌结果:"+count);
		return "redirect:list";
	}




}
