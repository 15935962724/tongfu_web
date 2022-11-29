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

@Controller("webexhibition")
@RequestMapping("/web/exhibition")
public class ExhibitionController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ExhibitionService exhibitionService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ArticleService articleService;

	@Value("${exhibition-logo}")
	private String exhibitionLogo;

	@Autowired
	private HttpServletRequest request;
	/**
	 * 附件上传路径
	 */
	@Value("${file-web-path}")
	private String fileWebPath;
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title,String startDate,String endDate ) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);
		map.put("companyId",companyService.getCompanyId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}
		Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map> all = exhibitionService.getAll(map);
		PageInfo<Map> pageInfo = new PageInfo<Map>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		return "web/exhibition/list";
	}


	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map query_map = new HashMap();
		query_map.put("parent",1);
		List<ProductCategory> categoryList = (List<ProductCategory>) productCategoryService.getAll(query_map);
		model.addAttribute("categoryList",categoryList);
		return "web/exhibition/add";
	}


	/**
	 *
	 * @param exhibition
	 * @param exhibitionFile
	 * @return
	 */
	@PostMapping("/save")
	public String save(Exhibition exhibition,@RequestParam("exhibitionLogo") MultipartFile exhibitionFile
			,String yearTime,String minutesTime,Long [] categoryIds ) {
		Admin admin = adminService.getCurrent();
		Date holdingTime = DateUtil.getStringtoDate(yearTime.trim()+" "+minutesTime.trim(),"MM/dd/yyyy HH:mm:ss");
		exhibition.setHoldingTime(holdingTime);
		exhibition.setModifyDate(new Date());
		exhibition.setCreateDate(new Date());
		exhibition.setStatus(1l);
		exhibition.setHits(0l);
		String urlPrefix = HttpUtil.getRequestPrefix(request);
		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(exhibition.getContent(),staticPath,urlPrefix+rootPath);
		exhibition.setContent(newContent);
		exhibition.setCompanyId(companyService.getCompanyId());
		String media_logo=   FileUtils.upload( exhibitionFile,exhibitionLogo);
		exhibition.setLogo(media_logo);
		Integer conunt = exhibitionService.insertSelective(exhibition);
		logger.info("结果:"+conunt);
		if (categoryIds!=null&&categoryIds.length>0){


			Map article_map = new HashMap<>();
			Article article = new Article();
			article.setCreateDate(new Date());
			article.setModifyDate(new Date());
			article.setHits(0l);
			article.setOrders("0");
			article.setIsDeleted(false);
			article.setTitle(exhibition.getTitle());
//			article.setImage(journalismLogo);
//			article.setSeoKeywords(exhibition.getKeyword());
//			article.setDatasource(exhibition.getDatasource());
			article.setSeoTitle(exhibition.getTitle());
			article.setIsTop(false);
			article.setIsPublication(true);
			article.setContent(exhibition.getContent());
			article.setAuthor(admin.getName());
			article.setTableId(exhibition.getId());
			article.setTables("tf_exhibition");
			article.setIntroduce(exhibition.getIntroduce());
			article_map.put("article",article);
			article_map.put("articleCategorys",categoryIds);
			int insertMap = articleService.insertMap(article_map);


		}



		return "redirect:list";
	}

	/**
	 * 预览
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model model,Long id ){
		Exhibition exhibition = exhibitionService.selectByPrimaryKey(id);
		Company company = companyService.selectByPrimaryKey(exhibition.getCompanyId());
		model.addAttribute("exhibition",exhibition);
		model.addAttribute("company",company);
		return "web/exhibition/view";
	}

	/**
	 * 展会展览编辑
	 */
	@PostMapping("/update")
	public String update(Exhibition exhibition ,@RequestParam("exhibitionLogo") MultipartFile exhibitionFile,
						 String yearTime,String minutesTime,Long [] categoryIds ) {
		Admin admin = adminService.getCurrent();
		Date holdingTime = DateUtil.getStringtoDate(yearTime.trim()+" "+minutesTime.trim(),"MM/dd/yyyy HH:mm:ss");
		exhibition.setHoldingTime(holdingTime);
		Article article = new Article();
		if (exhibitionFile.getSize()>0){
			//logo
			String media_logo = FileUtils.upload( exhibitionFile,exhibitionLogo);
			exhibition.setLogo(media_logo);
			article.setImage(media_logo);
		}
		exhibition.setStatus(1l);
		String urlPrefix = HttpUtil.getRequestPrefix(request);
		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(exhibition.getContent(),staticPath,urlPrefix+rootPath);
		exhibition.setContent(newContent);
		Integer conunt = exhibitionService.updateByPrimaryKeySelective(exhibition);
		logger.info("编辑结果:"+conunt);
		Map query_map = new HashMap();
		query_map.put("tableId",exhibition.getId());
		query_map.put("tables","tf_exhibition");
		int deleteMap= articleService.deleteMap(query_map);
		if (categoryIds!=null&&categoryIds.length>0){

			Map article_map = new HashMap<>();

			article.setCreateDate(new Date());
			article.setModifyDate(new Date());
			article.setHits(0l);
			article.setOrders("0");
			article.setIsDeleted(false);
			article.setTitle(exhibition.getTitle());

//			article.setSeoKeywords(exhibition.getKeyword());
//			article.setDatasource(exhibition.getDatasource());
			article.setSeoTitle(exhibition.getTitle());
			article.setIsTop(false);
			article.setIsPublication(true);
			article.setContent(exhibition.getContent());
			article.setAuthor(admin.getName());
			article.setTableId(exhibition.getId());
			article.setIntroduce(exhibition.getIntroduce());
			article.setTables("tf_exhibition");
			article_map.put("article",article);
			article_map.put("articleCategorys",categoryIds);
			int insertMap = articleService.insertMap(article_map);
		}
		return "redirect:list";
	}

	/**
	 * 编辑展会展览
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Exhibition exhibition = exhibitionService.selectByPrimaryKey(id);
		model.addAttribute("exhibition",exhibition);
		Map query_map = new HashMap();
		query_map.put("parent",1);
		List<ProductCategory> categoryList = (List<ProductCategory>) productCategoryService.getAll(query_map);
		model.addAttribute("categoryList",categoryList);
		query_map.put("tableId",id);
		query_map.put("tables","tf_exhibition");
		List<Article>  articles = articleService.getAll(query_map);
		List<Long> categoryIds = articles.stream().map(Article -> Article.getArticleCategory()).collect(Collectors.toList());

		model.addAttribute("categoryIds",categoryIds);
		return "web/exhibition/edit";
	}


	/**
	 *
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String save(@RequestBody JSONObject jsonObject ){
		Integer deleteByPrimaryKey = exhibitionService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}


}
