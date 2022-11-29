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

@Controller("webmedia")
@RequestMapping("/web/media")
public class MediaController<articleService> {

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
	private ArticleService articleService;

	@Autowired
	private MediaService mediaService;

	//媒体报道logo传路径
	@Value("${media-logo}")
	private String mediaLogo;

	@Autowired
	private HttpServletRequest request;
	/**
	 * 附件上传路径
	 */
	@Value("${file-web-path}")
	private String fileWebPath;
	/**
	 * 媒体报道列表
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
		List<Map> all = mediaService.getAll(map);
		PageInfo<Map> pageInfo = new PageInfo<Map>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);
		return "web/media/list";
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
		return "web/media/add";
	}


	/**
	 *
	 * @param media
	 * @param adImageFile
	 * @return
	 */
	@PostMapping("/save")
	public String save(Media media,@RequestParam("mediaLogo") MultipartFile adImageFile,String yearTime,String minutesTime,Long [] categoryIds) {
		Admin admin = adminService.getCurrent();
		media.setModifyDate(new Date());
		Date holdingTime = DateUtil.getStringtoDate(yearTime.trim()+" "+minutesTime.trim(),"MM/dd/yyyy HH:mm:ss");
		media.setCreateDate(holdingTime);
		media.setStatus(1l);
		media.setHits(0l);

		String urlPrefix = HttpUtil.getRequestPrefix(request);
		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(media.getContent(),staticPath,urlPrefix+rootPath);
		media.setContent(newContent);
		media.setCompanyId(companyService.getCompanyId());
		//广告图路径
		String media_logo=   FileUtils.upload( adImageFile,mediaLogo);
		media.setLogo(media_logo);

		Integer conunt = mediaService.insertSelective(media);
		logger.info("媒体报道结果:"+conunt);
		if (categoryIds!=null&&categoryIds.length>0){

			Map article_map = new HashMap<>();
			Article article = new Article();
			article.setCreateDate(new Date());
			article.setModifyDate(new Date());
			article.setHits(0l);
			article.setOrders("0");
			article.setIsDeleted(false);
			article.setTitle(media.getTitle());
			article.setImage(media_logo);
			article.setSeoKeywords(media.getKeyword());
			article.setDatasource(media.getDatasource());
			article.setSeoTitle(media.getTitle());
			article.setIsTop(false);
			article.setIsPublication(true);
			article.setContent(media.getContent());
			article.setAuthor(admin.getName());
			article.setTableId(media.getId());
			article.setIntroduce(media.getIntroduce());
			article.setTables("tf_media");
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
	public String view(Model model,Long id) {
		Media media = mediaService.selectByPrimaryKey(id);
		Company company = companyService.selectByPrimaryKey(media.getCompanyId());
		model.addAttribute("media",media);
		model.addAttribute("company",company);
		return "web/media/view";
	}


	/**
	 * 编辑
	 * @param media
	 * @param mediaImageFile
	 * @param yearTime
	 * @param minutesTime
	 * @param categoryIds
	 * @return
	 */
	@PostMapping("/update")
	public String update(Media media,@RequestParam("mediaLogo") MultipartFile mediaImageFile,String yearTime,String minutesTime,Long [] categoryIds) {

		Admin admin = adminService.getCurrent();
		Article article = new Article();
		//广告图路径
		if (mediaImageFile.getSize()>0){
			String media_logo = FileUtils.upload( mediaImageFile,mediaLogo);
			media.setLogo(media_logo);
			article.setImage(media_logo);
		}
		String urlPrefix = HttpUtil.getRequestPrefix(request);
		String rootPath = "/file/";
		String staticPath= fileWebPath+rootPath;
		String newContent = FileUtils.replaceImgStc(media.getContent(),staticPath,urlPrefix+rootPath);
		media.setContent(newContent);
		Date holdingTime = DateUtil.getStringtoDate(yearTime.trim()+" "+minutesTime.trim(),"MM/dd/yyyy HH:mm:ss");
		media.setCreateDate(holdingTime);
		media.setStatus(1l);
		Integer conunt = mediaService.updateByPrimaryKeySelective(media);
		Map query_map = new HashMap();
		query_map.put("tableId",media.getId());
		query_map.put("tables","tf_media");
		int deleteMap= articleService.deleteMap(query_map);
		if (categoryIds!=null&&categoryIds.length>0){
			Map article_map = new HashMap<>();

			article.setCreateDate(new Date());
			article.setModifyDate(new Date());
			article.setHits(0l);
			article.setOrders("0");
			article.setIsDeleted(false);
			article.setTitle(media.getTitle());
			article.setSeoKeywords(media.getKeyword());
			article.setDatasource(media.getDatasource());
			article.setSeoTitle(media.getTitle());
			article.setIsTop(false);
			article.setIsPublication(true);
			article.setContent(media.getContent());
			article.setAuthor(admin.getName());
			article.setTableId(media.getId());
			article.setTables("tf_media");
			article.setIntroduce(media.getIntroduce());
			article_map.put("article",article);
			article_map.put("articleCategorys",categoryIds);
			int insertMap = articleService.insertMap(article_map);
		}
		return "redirect:list";
	}

	/**
	 * 发布广告页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Media media = mediaService.selectByPrimaryKey(id);
		model.addAttribute("media",media);
		Map query_map = new HashMap();
		query_map.put("parent",1);
		List<ProductCategory> categoryList = (List<ProductCategory>) productCategoryService.getAll(query_map);
		model.addAttribute("categoryList",categoryList);
		query_map.put("tableId",id);
		query_map.put("tables","tf_media");
		List<Article>  articles = articleService.getAll(query_map);
		List<Long> categoryIds = articles.stream().map(Article -> Article.getArticleCategory()).collect(Collectors.toList());

		model.addAttribute("categoryIds",categoryIds);
		return "web/media/edit";
	}


	/**
	 * 删除媒体报道
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String save(@RequestBody JSONObject jsonObject ){
		Integer deleteByPrimaryKey = mediaService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}


}
