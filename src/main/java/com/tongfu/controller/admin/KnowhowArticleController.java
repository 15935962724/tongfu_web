package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.Ad;
import com.tongfu.entity.AdPosition;
import com.tongfu.entity.KnowhowArticle;
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
@RequestMapping("/admin/knowhowArticle")
public class KnowhowArticleController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;


	@Autowired
	private KnowhowArticleService knowhowArticleService;


	@Value("${knowhowArticle-logo}")
	private String knowhowArticleLogo;

	/**
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
		Page<KnowhowArticle> page  = PageHelper.startPage(pageNum,pageSize);
		List<KnowhowArticle> aboutusList = knowhowArticleService.getAll(map);
		PageInfo<KnowhowArticle> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(aboutusList);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		return "admin/knowhowArticle/list";
	}




	@PostMapping("/update")
	public String update(KnowhowArticle knowhowArticle,@RequestParam("knowhowArticleImage") MultipartFile knowhowArticleImageFile ) {

		if (knowhowArticleImageFile.getSize()> 0){
			String logo = FileUtils.upload( knowhowArticleImageFile,knowhowArticleLogo);
			knowhowArticle.setLogo(logo);
		}

		int i = knowhowArticleService.updateByPrimaryKeySelective(knowhowArticle);
		return "redirect:list";
	}



	@PostMapping("/save")
	public String save(KnowhowArticle knowhowArticle, @RequestParam("knowhowArticleImage") MultipartFile knowhowArticleImageFile ) {

		String logo = FileUtils.upload( knowhowArticleImageFile,knowhowArticleLogo);
		knowhowArticle.setLogo(logo);
		knowhowArticle.setCreateDate(new Date());
		knowhowArticle.setModifyDate(new Date());
		int i = knowhowArticleService.insertSelective(knowhowArticle);
		return "redirect:list";
	}

	@GetMapping("/edit")
	public String edit(Model model,Long id) {
		KnowhowArticle knowhowArticle = knowhowArticleService.selectByPrimaryKey(id);
		model.addAttribute("knowhowArticle",knowhowArticle);
		return "admin/knowhowArticle/edit";
	}

	@GetMapping("/delete")
	public String delete(Model model,Long id) {

		knowhowArticleService.deleteByPrimaryKey(id);

		return "redirect:list";
	}


	@GetMapping("/add")
	public String add(Model model) {
		return "admin/knowhowArticle/add";
	}



}
