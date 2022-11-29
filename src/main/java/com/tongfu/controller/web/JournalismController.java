package com.tongfu.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.HttpUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("webjournalism")
@RequestMapping("/web/journalism")
public class JournalismController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JournalismService journalismService ;

	@Autowired
	private ProductCategoryService categoryService;

	@Autowired
	private ArticleService articleService;

	@Value("${journalism-logo}")
	private String journalism_logo;

	@Autowired
	private HttpServletRequest request;
	/**
	 * 附件上传路径
	 */
	@Value("${file-web-path}")
	private String fileWebPath;
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
					   String title,String keyword,String singledaterange) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);
		map.put("keyword",keyword);
		map.put("companyId",companyService.getCompanyId());
		if(singledaterange!=null){
			System.out.println(singledaterange.split("-")[0]+">>>>"+singledaterange.split("-")[1]);
			Date startDate = DateUtil.getStringtoDate( (singledaterange.split("-")[0]).trim()+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			Date endDate = DateUtil.getStringtoDate( (singledaterange.split("-")[1]).trim()+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",startDate);
			map.put("endDate",endDate);
		}
		Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map> all = journalismService.getAll(map);
		PageInfo<Map> pageInfo = new PageInfo<Map>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		model.addAttribute("keyword",keyword);
		model.addAttribute("singledaterange",singledaterange);
//		model.addAttribute("productId",productId);
		return "web/journalism/list";
	}


	/**
	 * 添加新闻
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map query_map = new HashMap();
		query_map.put("parent",1);
		List<ProductCategory> categoryList = (List<ProductCategory>) categoryService.getAll(query_map);
		model.addAttribute("categoryList",categoryList);
		return "web/journalism/add";
	}

	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Journalism journalism = journalismService.selectByPrimaryKey(id);
		model.addAttribute("journalism",journalism);
		Map query_map = new HashMap();
		query_map.put("parent",1);
		List<ProductCategory> categoryList = (List<ProductCategory>) categoryService.getAll(query_map);
		model.addAttribute("categoryList",categoryList);
		query_map.put("tableId",id);
		query_map.put("tables","tf_journalism");
		List<Article>  articles = articleService.getAll(query_map);
		List<Long> categoryIds = articles.stream().map(Article -> Article.getArticleCategory()).collect(Collectors.toList());

		model.addAttribute("categoryIds",categoryIds);

		return "web/journalism/edit";
	}


	/**
	 * 预览
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model model,Long id) {
		Journalism journalism = journalismService.selectByPrimaryKey(id);
		Company company = companyService.selectByPrimaryKey(journalism.getCompanyId());
		model.addAttribute("journalism",journalism);
		model.addAttribute("company",company);
		return "web/journalism/view";
	}


	/**
	 * 保存新闻
	 * @param model
	 * @param journalism
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Journalism journalism ,@RequestParam("journalismLogo") MultipartFile file ,Long [] categoryIds) {
		Admin admin = adminService.getCurrent();
		String journalismLogo = FileUtils.upload(file,journalism_logo);
		journalism.setCreateDate(new Date());
		journalism.setModifyDate(new Date());
		journalism.setHits(0l);

		String urlPrefix = HttpUtil.getRequestPrefix(request);

		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(journalism.getContent(),staticPath,urlPrefix+rootPath);
//		System.out.println("替换之后的内容:"+newContent);
		journalism.setContent(newContent);
		journalism.setCompanyId(companyService.getCompanyId());
		journalism.setStatus(1l);
		System.out.println("新闻logo:"+journalismLogo);
		if (journalismLogo!=null){
			journalism.setLogo(journalismLogo);
		}
		Integer conunt = journalismService.insertSelective(journalism);
		if (categoryIds.length>0){

			//编辑多图
			Map article_map = new HashMap<>();
			Article article = new Article();
			article.setCreateDate(new Date());
			article.setModifyDate(new Date());
			article.setHits(0l);
			article.setOrders("0");
			article.setIsDeleted(false);
			article.setTitle(journalism.getTitle());
			article.setImage(journalismLogo);
			article.setSeoKeywords(journalism.getKeyword());
			article.setDatasource(journalism.getDatasource());
			article.setSeoTitle(journalism.getTitle());
			article.setIsTop(false);
			article.setIsPublication(true);
			article.setContent(journalism.getContent());
			article.setAuthor(admin.getName());
			article.setTableId(journalism.getId());
			article.setTables("tf_journalism");
			article.setIntroduce(journalism.getIntroduce());
//			article_map.put("createDate",new Date());
//			article_map.put("modifyDate",new Date());
//			article_map.put("hits",0);
//			article_map.put("orders",0);
//			article_map.put("isDeleted",false);
//			article_map.put("title",journalism.getTitle());
//			article_map.put("image",journalismLogo);
//			article_map.put("seoKeywords",journalism.getKeyword());
//			article_map.put("datasource",journalism.getDatasource());
//			article_map.put("seoTitle",journalism.getTitle());
//			article_map.put("isTop",false);
//			article_map.put("isPublication",false);
//			article_map.put("content",journalism.getContent());
//			article_map.put("table_id",journalism.getId());
//			article_map.put("author",admin.getName());
			article_map.put("article",article);
			article_map.put("articleCategorys",categoryIds);
			int insertMap = articleService.insertMap(article_map);


		}

		return "redirect:list";
	}

	/**
	 * 编辑
	 * @param model
	 * @param journalism
	 * @param file
	 * @param categoryIds
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,Journalism journalism,@RequestParam("journalismLogo") MultipartFile file,Long [] categoryIds) {
		Admin admin = adminService.getCurrent();
		String journalismLogo = FileUtils.upload(file,journalism_logo);
		journalism.setModifyDate(new Date());
		if (journalismLogo!=null){
			journalism.setLogo(journalismLogo);
		}
		journalism.setStatus(1l);
		String urlPrefix = HttpUtil.getRequestPrefix(request);
//		System.out.println(urlPrefix);
//		System.out.println(journalism.getContent());
		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(journalism.getContent(),staticPath,urlPrefix+rootPath);
//		System.out.println("替换之后的内容:"+newContent);
		journalism.setContent(newContent);
		Integer conunt = journalismService.updateByPrimaryKeySelective(journalism);

		Map query_map = new HashMap();
		query_map.put("tableId",journalism.getId());
		query_map.put("tables","tf_journalism");
		int deleteMap= articleService.deleteMap(query_map);
		if (categoryIds.length>0){
			//编辑多图
			Map article_map = new HashMap<>();
			Article article = new Article();
			article.setCreateDate(new Date());
			article.setModifyDate(new Date());
			article.setHits(0l);
			article.setOrders("0");
			article.setIsDeleted(false);
			article.setTitle(journalism.getTitle());
			article.setImage(journalismLogo);
			article.setSeoKeywords(journalism.getKeyword());
			article.setDatasource(journalism.getDatasource());
			article.setSeoTitle(journalism.getTitle());
			article.setIsTop(false);
			article.setIsPublication(true);
			article.setContent(journalism.getContent());
			article.setAuthor(admin.getName());
			article.setTableId(journalism.getId());
			article.setIntroduce(journalism.getIntroduce());
			article.setTables("tf_journalism");
			article_map.put("article",article);
			article_map.put("articleCategorys",categoryIds);
			int insertMap = articleService.insertMap(article_map);


		}

		logger.info("结果:"+conunt);
		return "redirect:list";
	}

	/**
	 * 删除新闻动态
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String save(@RequestBody JSONObject jsonObject ){
		Integer deleteByPrimaryKey = journalismService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}
}
