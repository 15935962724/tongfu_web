package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.Ad;
import com.tongfu.entity.AdIncome;
import com.tongfu.entity.AdPosition;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/aboutus")
public class AboutusController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private AboutusService aboutusService;

	@Value("${aboutus-logo}")
	private String aboutusLogo;



	/**
	 *
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param title
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);
		Page<Aboutus> page  = PageHelper.startPage(pageNum,pageSize);
		List<Aboutus> aboutusList = aboutusService.getAll(map);
		PageInfo<Aboutus> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(aboutusList);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		return "admin/aboutus/list";
	}




	@PostMapping("/update")
	public String update(Aboutus aboutus,@RequestParam("aboutusImage") MultipartFile aboutusImageFile) {

		if (aboutusImageFile.getSize()>0){
			String logo = FileUtils.upload( aboutusImageFile,aboutusLogo);
			aboutus.setLogo(logo);
		}
		int count = aboutusService.updateByPrimaryKeySelective(aboutus);
		return "redirect:list";
	}



	@PostMapping("/save")
	public String save(Aboutus aboutus,@RequestParam("aboutusImage") MultipartFile aboutusImageFile) {
		String logo = FileUtils.upload( aboutusImageFile,aboutusLogo);
		aboutus.setLogo(logo);
		aboutus.setCreateDate(new Date());
		int count = aboutusService.insertSelective(aboutus);
		return "redirect:list";
	}


	@GetMapping("/edit")
	public String edit(Model model,Long id) {
		Aboutus aboutus = aboutusService.selectByPrimaryKey(id);
		model.addAttribute("aboutus",aboutus);
		return "admin/aboutus/edit";
	}

	@GetMapping("/delete")
	public String eddeleteit(Model model,Long id) {
	    aboutusService.deleteByPrimaryKey(id);
		return "redirect:list";
	}


	@GetMapping("/add")
	public String add(Model model) {
		return "admin/aboutus/add";
	}




}
