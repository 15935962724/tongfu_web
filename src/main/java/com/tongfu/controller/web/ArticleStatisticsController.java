package com.tongfu.controller.web;

import com.tongfu.entity.Company;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller("webarticleStatistics")
@RequestMapping("/web/articleStatistics")
public class ArticleStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JournalismService journalismService;

	@Autowired
	private LearningService learningService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private ExhibitionService exhibitionService;

	@Autowired
	private OrderItemService orderItemService;

	@Value("${article-logo}")
	private String article_logo;


//	@RequestMapping("/statistics")
//	public String statistics(Model model){
//
//		Company company = companyService.getCompany();
//		Map query_map = new HashMap();
//		query_map.put("companyId",company.getId());
//		Map journalismMap = new HashMap();
//		List journalisms = journalismService.getAll(query_map);
//		journalismMap.put("name","新闻动态");
//		journalismMap.put("countNum",journalisms.size());
//		journalismMap.put("sumHits",journalismService.hitsJournalismCouont(query_map));
//		Map learningMap = new HashMap();
//		List learnings = learningService.getAll(query_map);
//		learningMap.put("name","学术活动");
//		learningMap.put("countNum",learnings.size());
//		learningMap.put("sumHits",learningService.hitsLearningCouont(query_map));
//		Map mediaMap = new HashMap();
//		List medias = mediaService.getAll(query_map);
//		mediaMap.put("name","媒体报道");
//		mediaMap.put("countNum",medias.size());
//		mediaMap.put("sumHits",mediaService.hitsMediaCouont(query_map));
//		Map exhibitionMap = new HashMap();
//		List exhibitions = exhibitionService.getAll(query_map);
//		exhibitionMap.put("name","展会展览");
//		exhibitionMap.put("countNum",exhibitions.size());
//		exhibitionMap.put("sumHits",exhibitionService.hitsExhibitionCouont(query_map));
//		List<Map> articles = new ArrayList<>();
//		articles.add(journalismMap);
//		articles.add(learningMap);
//		articles.add(mediaMap);
//		articles.add(exhibitionMap);
//		model.addAttribute("articles",articles);
//		List<Map> visitsRanking = companyService.getVisitsRanking(query_map);//文章访问量排名
//		model.addAttribute("visitsRanking",visitsRanking);
//		return "web/articleStatistics/statistics";
//	}

	/**
	 * 品牌数据统计
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model){


		Company company = companyService.getCompany();
		Map query_map = new HashMap();
		query_map.put("companyId",company.getId());
		List<Map> articles = journalismService.getArticle(query_map);
		model.addAttribute("articles",articles);
//		List<Map> visitsRanking = companyService.getVisitsRanking(query_map);//文章访问量排名
//		model.addAttribute("visitsRanking",visitsRanking);
		List<Map> brandConunts = journalismService.getbrandConunt(query_map);
		model.addAttribute("brandConunts",brandConunts);
		List<Map> articleRanking = journalismService.getArticleRanking(query_map);
		model.addAttribute("articleRanking",articleRanking);
		return "web/articleStatistics/brandDataStatistics";
	}



	/**
	 * 文章自定义统计
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/articleSize", method = RequestMethod.POST)
	@ResponseBody
	public String articleSize(String startDate ,String endDate){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}


		List<Map> articles = journalismService.getArticle(query_map);
		return ResultUtil.success(articles);
	}


	/**
	 * 文章排名自定义日期查询
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/getArticles", method = RequestMethod.POST)
	@ResponseBody
	public String getArticles(String startDate ,String endDate){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		List<Map> articleRanking = journalismService.getArticleRanking(query_map);
		return ResultUtil.success(articleRanking);
	}






	/**
	 * 文章自定义统计
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	/*@RequestMapping(value = "/articleSize", method = RequestMethod.POST)
	@ResponseBody
	public String articleSize(Long type ,String startDate ,String endDate){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		query_map.put("type",type);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}

		Map journalismMap = new HashMap();
		List journalisms = journalismService.getAll(query_map);
		journalismMap.put("name","新闻动态");
		journalismMap.put("countNum",journalisms.size());
		Map learningMap = new HashMap();
		List learnings = learningService.getAll(query_map);
		learningMap.put("name","学术活动");
		learningMap.put("countNum",learnings.size());
		Map mediaMap = new HashMap();
		List medias = mediaService.getAll(query_map);
		mediaMap.put("name","媒体报道");
		mediaMap.put("countNum",medias.size());
		Map exhibitionMap = new HashMap();
		List exhibitions = exhibitionService.getAll(query_map);
		exhibitionMap.put("name","展会展览");
		exhibitionMap.put("countNum",exhibitions.size());
		List<Map> articles = new ArrayList<>();
//		if (journalisms.size()>0){
			articles.add(journalismMap);
//		}
//		if (learnings.size()>0){
			articles.add(learningMap);
//		}
//		if (medias.size()>0){
			articles.add(mediaMap);
//		}
//		if (exhibitions.size()>0){
			articles.add(exhibitionMap);
//		}
		return ResultUtil.success(articles);
	}*/


	/**
	 * 点击量自定义统计
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/articleHits", method = RequestMethod.POST)
	@ResponseBody
	public String articleHits(Long type ,String startDate ,String endDate){
		Map query_map = new HashMap();
		Company company = companyService.getCompany();
		query_map.put("companyId",company.getId());
		query_map.put("type",type);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
			query_map.put("endDate",end_Date);
		}

		Map journalismMap = new HashMap();
		int hitsJournalismCouont = journalismService.hitsJournalismCouont(query_map);
		journalismMap.put("name","新闻动态");
		journalismMap.put("sumHits",hitsJournalismCouont);
		Map learningMap = new HashMap();
		Integer hitsLearningCouont = learningService.hitsLearningCouont(query_map);
		learningMap.put("name","学术活动");
		learningMap.put("sumHits",hitsLearningCouont);
		Map mediaMap = new HashMap();
		Integer hitsMediaCouont = mediaService.hitsMediaCouont(query_map);
		mediaMap.put("name","媒体报道");
		mediaMap.put("sumHits",hitsMediaCouont);
		Map exhibitionMap = new HashMap();
		Integer hitsExhibitionCouont = exhibitionService.hitsExhibitionCouont(query_map);
		exhibitionMap.put("name","展会展览");
		exhibitionMap.put("sumHits",hitsExhibitionCouont);
		List<Map> articles = new ArrayList<>();
//		if (hitsJournalismCouont>0){
//			articles.add(journalismMap);
//		}
//		if (hitsLearningCouont>0){
//			articles.add(learningMap);
//		}
//		if (hitsMediaCouont>0){
//			articles.add(mediaMap);
//		}
//		if (hitsExhibitionCouont>0){
//			articles.add(exhibitionMap);
//		}
			articles.add(journalismMap);
			articles.add(learningMap);
			articles.add(mediaMap);
			articles.add(exhibitionMap);
		return ResultUtil.success(articles);
	}






}
