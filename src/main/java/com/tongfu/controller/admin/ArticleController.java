package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Article;
import com.tongfu.service.AdminService;
import com.tongfu.service.ArticleService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.HtmlSpiritUtil;
import com.tongfu.util.HttpUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	ArticleService articleService;

	@Value("${article-logo}")
	private String article_logo;
	/**
	 * 附件上传路径
	 */
	@Value("${file-web-path}")
	private String fileWebPath;
	@Autowired
	private HttpServletRequest request;
	/**
	 * 文章列表
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param title
	 * @param seoKeywords
	 * @param startDate
	 * @param endDate
	 * @param articleCategory
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title,String seoKeywords ,String startDate,String endDate ,Long articleCategory ) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);
		map.put("seoKeywords",seoKeywords);
		map.put("articleCategory",articleCategory);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}
		Page<Article> page  = PageHelper.startPage(pageNum,pageSize);
		List<Article> all = articleService.getAll(map);
		PageInfo<Article> pageInfo = new PageInfo<Article>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		model.addAttribute("seoKeywords",seoKeywords);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("articleCategory",articleCategory);
		return "admin/article/list";
	}


	/**
	 * 添加新闻
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model ,Long articleCategory) {
		model.addAttribute("articleCategory",articleCategory);
		return "admin/article/add";
	}

	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
//		Collection<Role> roles = roleService.getAll(null);
		Article article = articleService.selectByPrimaryKey(id);
		model.addAttribute("article",article);
		return "admin/article/edit";
	}

	/**
	 * 保存文章
	 * @param model
	 * @param article
	 * @param file
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Article article ,@RequestParam("articleLogo") MultipartFile file) {
		String articleLogo = FileUtils.upload(file,article_logo);
		article.setCreateDate(new Date());
		article.setModifyDate(new Date());
		article.setHits(0l);
		article.setImage(articleLogo);
		article.setIsDeleted(false);
		article.setIsPublication(true);
		article.setIsTop(false);
		article.setSeoDescription(HtmlSpiritUtil.delHTMLTag(article.getContent()));
		String urlPrefix = HttpUtil.getRequestPrefix(request);
		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(article.getContent(),staticPath,urlPrefix+rootPath);
		article.setContent(newContent);
		Integer conunt = articleService.insertSelective(article);
		logger.info("添加结果:"+conunt);
		return "redirect:list?articleCategory="+article.getArticleCategory();
	}

	@PostMapping("/update")
	public String update(Model model,Article article ,@RequestParam("articleLogo") MultipartFile file) {
		String articleLogo = FileUtils.upload(file,article_logo);
		if (articleLogo!=null){
			article.setImage(articleLogo);
		}
		String urlPrefix = HttpUtil.getRequestPrefix(request);
		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(article.getContent(),staticPath,urlPrefix+rootPath);
		article.setContent(newContent);
		Integer conunt = articleService.updateByPrimaryKeySelective(article);

		logger.info("结果:"+conunt);
		return "redirect:list?articleCategory="+articleService.selectByPrimaryKey(article.getId()).getArticleCategory();
	}

	@RequestMapping("/delete")
	public String delete(Model model,Long id) {
//		Collection<Role> roles = roleService.getAll(null);
		Article article = articleService.selectByPrimaryKey(id);
		Integer deleteByPrimaryKey = articleService.deleteByPrimaryKey(id);
//		model.addAttribute("specifications",specifications);
		return "redirect:list?articleCategory="+article.getArticleCategory();
	}


}
