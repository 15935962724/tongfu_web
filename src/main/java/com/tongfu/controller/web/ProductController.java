package com.tongfu.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ExcelUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Controller("webproduct")
@RequestMapping("/web/product")
public class ProductController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductPurchaseService productPurchaseService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductImgService productImgService;

	@Autowired
	private ProductSoftwarePackageService productSoftwarePackageService;

	@Autowired
	private SpecificationsService specificationsService;

	@Autowired
	private PlatformTransactionManager txManager;

	@Autowired
	private ProductStatisticsService productStatisticsService;

	@Autowired
	private ProductActivationCodeService productActivationCodeService;

	@Autowired
	private ProductMealService productMealService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DemonstrationStatisticsService demonstrationStatisticsService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ArticleStatisticsService articleStatisticsService;

	@Value("${upload-path}")
	private String path;

	//商品logo上传路径
	@Value("${product-logo-img}")
	private String uploadProductLogoImg;

	//商品详情上传路径
	@Value("${product-img}")
	private String uploadProductImg;

	//商品下载包上传路径
	@Value("${product-package}")
	private String uploadProductPackage;

	//商品试用包上传路径
	@Value("${ontrial-package}")
	private String uploadOntrialPackage;

	//商品演示小视频上传路径
	@Value("${product-video}")
	private String uploadProductVideo;



	/**
	 * 产品列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String name,String sn ,Long productCategory ,Boolean isMarketable,Long status,String startDate,String endDate) {
		Map<String,Object> map = new HashMap<>();
		Admin admin = adminService.getCurrent();
//		Subject subject = SecurityUtils.getSubject();
		map.put("companyid",admin.getCompanyId());
		map.put("name",name);
		map.put("sn",sn);
		map.put("productCategory",productCategory);
		map.put("isMarketable",isMarketable);
		map.put("isTrainingPackage",false);
		map.put("status",status);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		Page<Map<String, Object>> page  = PageHelper.startPage(pageNum,pageSize);

		List<Map<String, Object>> all = productService.getAllMap(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("name",name);
		map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(map);
		model.addAttribute("productCategories",productCategories);//商品分类
		model.addAttribute("sn",sn);
		model.addAttribute("status",status);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		return "web/product/list";
	}


	/**
	 * 添加培训包
	 * @param model
	 * @return
	 */
	@RequestMapping("/addTrainPackages")
	public String addTrainPackages(Model model) {
		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
//		map.put("adminid",admin.getId());
		product_query_map.put("companyid",companyService.getCompanyId());
		Page<Product> page  = PageHelper.startPage(1,10);//最新发布产品
//		product_query_map.put("companyid",companyService.getCompanyId());
		List<Product> all = (List<Product>) productService.getAll(product_query_map); //查询最新发布商品
		PageInfo<Product> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(10);
		pageInfo.setPageNum(1);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);

		product_query_map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
//		List<Brand> brands = brandService.getAll(null);
		//普通商品购买方式
		List<Map<String,List<Purchase>>> map_purchases = new ArrayList<>();
		//增值服务商品购买方式
		List<Map<String,List<Purchase>>> isAddServicepurchases = new ArrayList<>();

//		List<Purchase> purchases = purchaseService.getAll(null);
//		for (Purchase purchase : purchases) {
//
//			Map<String,Object> query_map = new HashMap<>();
//			query_map.put("parent",purchase.getId());
//			Map<String,List<Purchase>> map = new HashMap<>();
//			purchase.setPurchases(purchaseService.getAll(query_map));
//		}

		List<Purchase> purchases = purchaseService.getAll(null);
		for (Purchase purchase : purchases) {

			Map<String,Object> query_map = new HashMap<>();
			query_map.put("parent",purchase.getId());
			Map<String,List<Purchase>> map = new HashMap<>();
			map.put(purchase.getName(),purchaseService.getAll(query_map));
			if (purchase.getType()==1){
				map_purchases.add(map);
			}else {
				isAddServicepurchases.add(map);
			}

		}

		product_query_map.put("isAddService",2);
		List<Product> isAddServiceProducts = (List<Product>) productService.getAll(product_query_map); //
		model.addAttribute("isAddServiceProducts",isAddServiceProducts);
		model.addAttribute("products",all);
		model.addAttribute("productCategories",productCategories);//商品分类
//		model.addAttribute("brands",brands);//品牌
		model.addAttribute("purchases",map_purchases);//普通商品购买方式
		model.addAttribute("assServicePurchases",isAddServicepurchases);//增值商品购买方式

		return "web/product/addTrainPackages";
	}


	/**
	 * 编辑培训包
	 * @param model
	 * @return
	 */
	@RequestMapping("/editTrainPackages")
	public String editTrainPackages(Model model,Long id) {
		Product product = productService.selectByPrimaryKey(id);
		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
//		map.put("adminid",admin.getId());
		product_query_map.put("companyid",companyService.getCompanyId());
		Page<Product> page  = PageHelper.startPage(1,10);//最新发布产品
//		product_query_map.put("companyid",companyService.getCompanyId());
		List<Product> all = (List<Product>) productService.getAll(product_query_map); //查询最新发布商品
		PageInfo<Product> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(10);
		pageInfo.setPageNum(1);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		product_query_map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
		ProductCategory productCategory = productCategoryService.selectByPrimaryKey(product.getProductCategory());//查找当前商品的所属分类
		ProductCategory parentProductCategory = productCategoryService.getPrentProductCategory(productCategory.getId());//查找当前商品的顶级分类

		//普通商品购买方式
		List<Map<String,List<Purchase>>> map_purchases = new ArrayList<>();
		//增值服务商品购买方式
		List<Map<String,List<Purchase>>> isAddServicepurchases = new ArrayList<>();

		List<Purchase> purchases = purchaseService.getAll(null);
		for (Purchase purchase : purchases) {

			Map<String,Object> query_map = new HashMap<>();
			query_map.put("parent",purchase.getId());
			Map<String,List<Purchase>> map = new HashMap<>();
			map.put(purchase.getName(),purchaseService.getAll(query_map));
			if (purchase.getType()==1){
				map_purchases.add(map);
			}else {
				isAddServicepurchases.add(map);
			}
		}

		product_query_map.put("productId",product.getId());
		List<ProductPurchase> productPurchases = productPurchaseService.getAll(product_query_map);//该商品的实际购买方式

		product_query_map.put("isAddService",true);
		List<Product> isAddServiceProducts = (List<Product>) productService.getAll(product_query_map); //查询增值服务产品
		model.addAttribute("isAddServiceProducts",isAddServiceProducts);//增值服务产品
		model.addAttribute("products",all);//最新发布的商品
		model.addAttribute("productPurchases",productPurchases);//该商品的实际购买方式

		List<String> purchaseIds = new ArrayList<>();
		Map<String,Object> map_purchase = new HashMap<>();
		for (ProductPurchase productPurchase : productPurchases) {
			purchaseIds.add(productPurchase.getPurchaseId());
			map_purchase.put(productPurchase.getPurchaseId(),productPurchase.getPrice());
		}

		List<Specifications> specificationsList = specificationsService.getAll(product_query_map);
		Map<String,Object> specificationsMap = new HashMap<>();

//		这个是最傻逼的一种写法
// 		for (Specifications specification : specificationsList) {
//			specificationsMap.put(specification.getTitle(),specification.getContent());
//		}
		String [] titles = {"软件概览","推荐配置","典型病例","软件教程","临床文献","用户评价","产品彩页"};
		String [] contents = {"overview","configure","cases","tutorial","clinical","evaluation","colorpage"};

		for (int i = 0; i < contents.length; i++) {
			if (specificationsList.size()>=contents.length){
				specificationsMap.put(contents[i],specificationsList.get(i).getContent());
			}else {
				specificationsMap.put(contents[i],"");
			}

		}
		model.addAttribute("specificationsMap",specificationsMap);//当前商品详情(八大项)

		model.addAttribute("purchaseIds",purchaseIds);//当前商品的购买方式id
		model.addAttribute("map_purchase",map_purchase);//当前商品的购买方式

		model.addAttribute("productCategories",productCategories);//商品分类

		model.addAttribute("purchases",map_purchases);//普通商品购买方式
		model.addAttribute("assServicePurchases",isAddServicepurchases);//增值商品购买方式
		model.addAttribute("product",product);
		model.addAttribute("productCategory",productCategory);//该商品当前分类
		model.addAttribute("parentProductCategory",parentProductCategory);//该商品所属父类
		return "web/product/editTrainPackages";
	}


	/**
	 * 编辑培训包
	 * @param model
	 * @param product
	 * @param productImage
	 * @param productVideo
	 * @param inputProductImage
	 * @param overview
	 * @param configure
	 * @param cases
	 * @param tutorial
	 * @param clinical
	 * @param evaluation
	 * @param colorpage
	 * @return
	 */
	@PostMapping("/updateTrainPackages")
	public String updateTrainPackages(Model model,Product product
			,@RequestParam("productimage") MultipartFile productImage
			,@RequestParam("productvideo") MultipartFile productVideo
			,@RequestParam("inputProductImage") MultipartFile[] inputProductImage
			,String overview //软件概览
			,String configure //推荐配置
			,String cases //典型病例
			,String tutorial //软件教程
			,String clinical //临床文献
			,String evaluation //用户评价
			,String colorpage  //产品彩页
	){

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			System.out.println("编辑培训包");
			if (product.getProductCategory()!=null){
				ProductCategory productCategory = productCategoryService.selectByPrimaryKey(product.getProductCategory());
				ProductCategory parentProductCategory = productCategoryService.selectByPrimaryKey(productCategory.getParent());
				//供应商补0操作
				String companyId=String.format("%03d%n", companyService.getCompanyId());
				String productCount=String.format("%04d%n", product.getId());
				String sn = parentProductCategory.getSeoTitle()+"-"+productCategory.getSeoTitle()+"-"+companyId.trim()+"-"+productCount.trim();
				product.setSn(sn);
			}

			if (product.getPriceExplain().equals("")){//隐层价格说明
				product.setPriceExplain("面议");
			}
			if (product.getIsHideprice()==null){//是否隐层价格
				product.setIsHideprice(false);
			}

			if (product.getIsShowOntrialPackageUrl()==null){//申请试用是否显示
				product.setIsShowOntrialPackageUrl(false);
			}

			if (product.getIsShowproductPackageUrl()==null){//立即下载是否显示
				product.setIsShowproductPackageUrl(false);
			}


			if (product.getIsDemonstration()==null){//申请演示  是否显示
				product.setIsDemonstration(false);
			}

			if (product.getIsScientific()==null){//是否科研使用
				product.setIsScientific(false);
			}


			//商品logo上传路径
			String product_logo_img = FileUtils.upload(productImage, uploadProductLogoImg);
			if (product_logo_img!=null){
				product.setImage(product_logo_img);
			}

			//商品演示小视频上传路径
			String product_video=   FileUtils.upload(productVideo, uploadProductVideo);
			if (product_video!=null){
				product.setVideo(product_video);
			}
			//		上传多图和上传视频
			product.setStatus(1l);//编辑商品将状态置为待审核
			Integer count = productService.updateByPrimaryKeySelective(product);
			Map query_map = new HashMap();
			query_map.put("productPackageId",product.getId());
			productMealService.updateProductMeal(query_map);//将套餐 也变为  待审核专挑

			//编辑多图
			Map<String,Object> productImgMap = new HashMap<>();
			List<Map<String,Object>> productImages = new ArrayList<>();
			Integer orders = 0;
			for (MultipartFile multipartFile : inputProductImage) {
				Map<String,Object> product_img_map = new HashMap<>();
				product_img_map.put("orders",orders++);
				product_img_map.put("productId",product.getId());
				ProductImg productImg = productImgService.selectByProductAndOrders(product_img_map);
				//商品多图编辑，用商品id 和 排序的序号去查询看能否查到
				if (productImg==null){
					if (multipartFile.getSize()>0){
						//商品详情上传路径
						String product_img=  FileUtils.upload(multipartFile, uploadProductImg);
						product_img_map.put("large",product_img);
						product_img_map.put("medium",product_img);
						product_img_map.put("source",product_img);
						product_img_map.put("thumbnail",product_img);
//					product_img_map.put("orders",orders++);
						product_img_map.put("title",product.getId()+"号商品图片");
						productImages.add(product_img_map);
					}
				}else {
					if (multipartFile.getSize()>0){
						String product_img=  FileUtils.upload(multipartFile, uploadProductImg);
						product_img_map.put("large",product_img);
						product_img_map.put("medium",product_img);
						product_img_map.put("source",product_img);
						product_img_map.put("thumbnail",product_img);
//					product_img_map.put("orders",orders++);
						product_img_map.put("title",product.getId()+"号商品图片");
						Integer updateProductImgCount = productImgService.updateByProductId(product_img_map);
						System.out.println(product.getId()+"号商品图片,编辑结果为:"+updateProductImgCount);
					}

				}
			}
			productImgMap.put("product",product.getId());
			productImgMap.put("productImages",productImages);
			if(productImages.size()>0){
				Integer insetProductImgCount = productImgService.insertMap(productImgMap);
				logger.info("添加商品结果:"+count+"新增商品id为:"+product.getId());
			}
//			String [] titles = {"软件概览","推荐配置","典型病例","软件教程","临床文献","用户评价","产品彩页"};
//			String [] contents = {overview,configure,cases,tutorial,clinical,evaluation,colorpage};
//			//先删除
//			specificationsService.deleteByProduct(product.getId());
//			//八大项
//			for (int i = 0; i < titles.length; i++) {
//				Specifications specifications = new Specifications();
//				specifications.setContent(String.valueOf(contents[i]));
//				specifications.setTitle(titles[i]);
//				specifications.setOrders(i);
//				specifications.setProduct(product.getId());
//				specificationsService.insertSelective(specifications);
//
//			}
			System.out.println("编辑商品结果:"+count);
			txManager.commit(status);
		}catch (Exception e){
			System.out.println("事务回滚了");
			//强制手动事务回滚
			txManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return "redirect:trainPackages";
	}



	/**
	 * 培训包
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sn
	 * @param productCategory
	 * @param isMarketable
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/trainPackages")
	public String trainPackages(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String name,String sn ,Long productCategory ,Boolean isMarketable,Long status,String startDate,String endDate) {
		Map<String,Object> map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		Subject subject = SecurityUtils.getSubject();
		map.put("companyid",companyService.getCompanyId());
		map.put("name",name);
		map.put("sn",sn);
		map.put("productCategory",productCategory);
		map.put("isMarketable",isMarketable);
		map.put("isTrainingPackage",true);
		map.put("status",status);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		Page<Map<String, Object>> page  = PageHelper.startPage(pageNum,pageSize);

		List<Map<String, Object>> all = productService.getAllMap(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("name",name);
		map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(map);
		model.addAttribute("productCategories",productCategories);//商品分类
		model.addAttribute("sn",sn);
		model.addAttribute("status",status);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		return "web/product/trainPackages";
	}

	/**
	 * 培训包
	 * @param model
	 * @param product
	 * @param productPurchase
	 * @param productImage
	 * @param productVideo
	 * @param inputProductImage
	 * @param overview
	 * @param configure
	 * @param cases
	 * @param tutorial
	 * @param clinical
	 * @param evaluation
	 * @param colorpage
	 * @return
	 */
	@RequestMapping(value = "/saveTrainPackages", method = RequestMethod.POST)
	public String saveTrainPackages(Model model, Product product, String productPurchase
			,@RequestParam("productimage") MultipartFile productImage
//			,@RequestParam("productpackage") MultipartFile productPackage
//			,@RequestParam("ontrialpackage") MultipartFile ontrialPackage
			,@RequestParam("productvideo") MultipartFile productVideo
			,@RequestParam("inputProductImage") MultipartFile[] inputProductImage
			,String overview //软件概览
			,String configure //推荐配置
			,String cases //典型病例
			,String tutorial //软件教程
			,String clinical //临床文献
			,String evaluation //用户评价
			,String colorpage  //产品彩页
	){
		System.out.println(overview);


		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			Admin admin = adminService.getCurrent();
			Company company = companyService.selectByPrimaryKey(admin.getCompanyId());
			product.setStatus(2l);//培训包自动审核通过
			product.setModifyDate(new Date());
			product.setCreateDate(new Date());
			product.setIsDeleted(false);
			product.setCompanyId(company.getId());
			product.setAllocatedStock(100);
			product.setStock(product.getReserveStock());

//			product.setPrice(new BigDecimal(0));
			product.setMarketPrice(new BigDecimal(0));
			product.setIsTop(false);
			product.setIsMarketable(true);
			product.setIsList(true);
			product.setIsGift(false);
			product.setFullName(product.getName());
			product.setHits(0l);
			product.setManufactor(company.getName());
			product.setUrl(company.getUrl());
			product.setIsAddService(3l);

			ProductCategory productCategory = productCategoryService.selectByPrimaryKey(product.getProductCategory());
			ProductCategory parentProductCategory = productCategoryService.selectByPrimaryKey(productCategory.getParent());

//		供应商补0操作
			String companyId=String.format("%03d%n", company.getId());

			Integer nextTableId = productService.nextTableId("tf_product");//查询该表下一个增长id
//		String productCount=String.format("%04d%n", productService.getAll(null).size());
			String productCount=String.format("%04d%n", nextTableId);

			String sn = parentProductCategory.getSeoTitle()+"-"+productCategory.getSeoTitle()+"-"+companyId.trim()+"-"+productCount.trim();
			product.setSn(sn);

			if (product.getPriceExplain().equals("")){//隐层价格说明
				product.setPriceExplain("面议");
			}
			if (product.getIsHideprice()==null){//是否隐层价格
				product.setIsHideprice(false);
			}

			if (product.getIsShowOntrialPackageUrl()==null){//申请试用是否显示
				product.setIsShowOntrialPackageUrl(false);
			}

			if (product.getIsShowproductPackageUrl()==null){//立即下载是否显示
				product.setIsShowproductPackageUrl(false);
			}

			if (product.getIsDemonstration()==null){//申请演示  是否显示
				product.setIsDemonstration(false);
			}


			//商品logo上传路径
			String product_logo_img = FileUtils.upload(productImage, uploadProductLogoImg);
			//商品下载包上传路径
//			String product_package= FileUtils.upload(productPackage, uploadProductPackage);
			//商品试用包上传路径
//			String ontrial_package=  FileUtils.upload(ontrialPackage, uploadOntrialPackage);
			//商品演示小视频上传路径
			String product_video=   FileUtils.upload(productVideo, uploadProductVideo);

			product.setImage(product_logo_img);
//			product.setProductPackage(product_package);
//			product.setOntrialPackage(ontrial_package);
			product.setVideo(product_video);



//		String logo = fals?path+file.getOriginalFilename():"";
//		System.out.println("brnadLogo图地址:"+logo);
//		product.setImage(logo);
			Integer count = productService.insertSelective(product);
			logger.info("添加商品结果:"+count+"新增商品id为:"+product.getId());
			Map<String,Object> productImgMap = new HashMap<>();
			List<Map<String,Object>> productImages = new ArrayList<>();
			Integer orders = 0;
			for (MultipartFile multipartFile : inputProductImage) {
				Map<String,Object> product_img_map = new HashMap<>();
				//商品详情上传路径
				String product_img=  FileUtils.upload(multipartFile, uploadProductImg);
				System.out.println("product_img>>>>"+product_img);
				if (product_img!=null){
					product_img_map.put("large",product_img);
					product_img_map.put("medium",product_img);
					product_img_map.put("source",product_img);
					product_img_map.put("thumbnail",product_img);
					product_img_map.put("orders",orders++);
					product_img_map.put("title",product.getId()+"号商品图片");
					productImages.add(product_img_map);
				}
			}
			productImgMap.put("product",product.getId());
			productImgMap.put("productImages",productImages);
			if(productImages.size()>0){
				Integer insetProductImgCount = productImgService.insertMap(productImgMap);
				logger.info("添加商品结果:"+count+"新增商品id为:"+product.getId());
			}

			String [] titles = {"软件概览","推荐配置","典型病例","软件教程","临床文献","用户评价","产品彩页"};
			String [] contents = {overview,configure,cases,tutorial,clinical,evaluation,colorpage};
			//八大项
			for (int i = 0; i < titles.length; i++) {
				Specifications specifications = new Specifications();
				specifications.setContent(String.valueOf(contents[i]));
				specifications.setTitle(titles[i]);
				specifications.setOrders(i);
				specifications.setProduct(product.getId());
				specificationsService.insertSelective(specifications);

			}
		}catch (Exception e){
			System.out.println("事务回滚了");
			//强制手动事务回滚
			txManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		txManager.commit(status);
		return "redirect:trainPackages";
	}


	/**
	 * 添加商品
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
//		map.put("adminid",admin.getId());
		product_query_map.put("companyid",companyService.getCompanyId());
		Page<Product> page  = PageHelper.startPage(1,10);//最新发布产品
//		product_query_map.put("companyid",companyService.getCompanyId());
		List<Product> all = (List<Product>) productService.getAll(product_query_map); //查询最新发布商品
		PageInfo<Product> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(10);
		pageInfo.setPageNum(1);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);


		product_query_map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
//		//普通商品购买方式
//		List<Map<String,List<Purchase>>> map_purchases = new ArrayList<>();
//		//增值服务商品购买方式
//		List<Map<String,List<Purchase>>> isAddServicepurchases = new ArrayList<>();
//
//		List<Purchase> purchases = purchaseService.getAll(null);
//		for (Purchase purchase : purchases) {
//
//			Map<String,Object> query_map = new HashMap<>();
//			query_map.put("parent",purchase.getId());
//			Map<String,List<Purchase>> map = new HashMap<>();
//			map.put(purchase.getName(),purchaseService.getAll(query_map));
//			if (purchase.getType()==1){
//				map_purchases.add(map);
//			}
//			if(purchase.getType()==2) {
//				isAddServicepurchases.add(map);
//			}
//
//		}

		product_query_map.put("isAddService",2);
		List<Product> isAddServiceProducts = (List<Product>) productService.getAll(product_query_map); //
		model.addAttribute("isAddServiceProducts",isAddServiceProducts);
		model.addAttribute("products",all);
		model.addAttribute("productCategories",productCategories);//商品分类
//		model.addAttribute("brands",brands);//品牌
//		model.addAttribute("purchases",map_purchases);//普通商品购买方式
//		model.addAttribute("assServicePurchases",isAddServicepurchases);//增值商品购买方式


		return "web/product/add";
	}


	/**
	 * 补货
	 * @param model
	 * @return
	 */
	@RequestMapping("/replenishment")
	public String replenishment(Model model,Long productId) {
		Map<String,Object> map = new HashMap<>();
		Admin admin = adminService.getCurrent();
//		Subject subject = SecurityUtils.getSubject();
		map.put("companyid",admin.getCompanyId());
		map.put("productId",productId);
		List<Map<String, Object>> all = productActivationCodeService.replenishmentStatus(map);
		model.addAttribute("list",all);
		return "web/product/replenishment";
	}





	/**
	 * 添加商品
	 * @param model
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, Product product
			           ,String productPurchase
					   ,String productSoftwarePackage
					   ,@RequestParam("productimage") MultipartFile productImage
//					   ,@RequestParam("productpackage") MultipartFile productPackage
//					   ,@RequestParam("ontrialpackage") MultipartFile ontrialPackage
					   ,@RequestParam("productvideo") MultipartFile productVideo
					   ,@RequestParam("inputProductImage") MultipartFile[] inputProductImage
			            ,String overview //软件概览
						,String configure //推荐配置
						,String cases //典型病例
						,String tutorial //软件教程
						,String clinical //临床文献
						,String evaluation //用户评价
						,String colorpage  //产品彩页
	){
		System.out.println(overview);


		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
		Admin admin = adminService.getCurrent();
		Company company = companyService.selectByPrimaryKey(admin.getCompanyId());
		product.setModifyDate(new Date());
		product.setCreateDate(new Date());
		product.setIsDeleted(false);
		product.setCompanyId(company.getId());
		product.setAllocatedStock(100);
		product.setStock(product.getReserveStock());

		product.setPrice(new BigDecimal(0));
		product.setMarketPrice(new BigDecimal(0));
		product.setIsTop(false);
		product.setIsMarketable(true);
		product.setIsList(true);
		product.setIsGift(false);
		product.setFullName(product.getName());
		product.setHits(0l);
		product.setManufactor(company.getName());
		product.setUrl(company.getUrl());
		if(StringUtils.isEmpty(product.getIntroduction())){
			product.setIntroduction(product.getName());
		}

		ProductCategory productCategory = productCategoryService.selectByPrimaryKey(product.getProductCategory());
		ProductCategory parentProductCategory = productCategoryService.selectByPrimaryKey(productCategory.getParent());

//		供应商补0操作
		String companyId=String.format("%03d%n", company.getId());

		Integer nextTableId = productService.nextTableId("tf_product");//查询该表下一个增长id
//		String productCount=String.format("%04d%n", productService.getAll(null).size());
		String productCount=String.format("%04d%n", nextTableId);

		String sn = parentProductCategory.getSeoTitle()+"-"+productCategory.getSeoTitle()+"-"+companyId.trim()+"-"+productCount.trim();
		product.setSn(sn);

		if (product.getPriceExplain().equals("")){//隐层价格说明
			product.setPriceExplain("面议");
		}
		if (product.getIsHideprice()==null){//是否隐层价格
			product.setIsHideprice(false);
		}

		if (product.getIsShowOntrialPackageUrl()==null){//申请试用是否显示
			product.setIsShowOntrialPackageUrl(false);
		}

		if (product.getIsShowproductPackageUrl()==null){//立即下载是否显示
			product.setIsShowproductPackageUrl(false);
		}

		if (product.getIsDemonstration()==null){//申请演示  是否显示
			product.setIsDemonstration(false);
		}

		if(product.getLanguage()==null){
			product.setLanguage("");
		}

//		if (product.getIsAddService()==2) {//是否增值服务产品
//			product.setProductId(null);
//		}

		if (product.getIsScientific()==null){//是否科研使用
			product.setIsScientific(false);
		}

		//商品logo上传路径
		String product_logo_img = FileUtils.upload(productImage, uploadProductLogoImg);
		//商品下载包上传路径
//		String product_package= FileUtils.upload(productPackage, uploadProductPackage);
		//商品试用包上传路径
//		String ontrial_package=  FileUtils.upload(ontrialPackage, uploadOntrialPackage);
		//商品演示小视频上传路径
		String product_video=   FileUtils.upload(productVideo, uploadProductVideo);

		product.setImage(product_logo_img);
//		product.setProductPackage(product_package);
//		product.setOntrialPackage(ontrial_package);
		product.setVideo(product_video);



//		String logo = fals?path+file.getOriginalFilename():"";
//		System.out.println("brnadLogo图地址:"+logo);
//		product.setImage(logo);
		Integer count = productService.insertSelective(product);
		logger.info("添加商品结果:"+count+"新增商品id为:"+product.getId());
		Map<String,Object> productImgMap = new HashMap<>();
		List<Map<String,Object>> productImages = new ArrayList<>();
		Integer orders = 0;
		for (MultipartFile multipartFile : inputProductImage) {
			Map<String,Object> product_img_map = new HashMap<>();
			//商品详情上传路径
			String product_img=  FileUtils.upload(multipartFile, uploadProductImg);
			System.out.println("product_img>>>>"+product_img);
			if (product_img!=null){
				product_img_map.put("large",product_img);
				product_img_map.put("medium",product_img);
				product_img_map.put("source",product_img);
				product_img_map.put("thumbnail",product_img);
				product_img_map.put("orders",orders++);
				product_img_map.put("title",product.getId()+"号商品图片");
				productImages.add(product_img_map);
			}
		}
			productImgMap.put("product",product.getId());
			productImgMap.put("productImages",productImages);
			if(productImages.size()>0){
				Integer insetProductImgCount = productImgService.insertMap(productImgMap);
				logger.info("添加商品结果:"+count+"新增商品id为:"+product.getId());
			}


			JSONArray productSoftwarPacaageJsonArray = JSONArray.parseArray(productSoftwarePackage);
			List<Map> list = JSONObject.parseArray(productSoftwarePackage,Map.class);

			Map productSoftwarePackageMap = new HashMap();
			productSoftwarePackageMap.put("productSoftwarPacaages",productSoftwarPacaageJsonArray);
			productSoftwarePackageMap.put("productId",product.getId());
			productSoftwarePackageService.insertSelectiveMap(productSoftwarePackageMap);

			System.out.println("productPurchase："+productPurchase);
//		String [] purchasePrices = productPurchase.split(",");
		List<Map<String,Object>> purchasePriceList = new ArrayList<>();
		JSONArray purchasePrices = JSONArray.parseArray(productPurchase);
		int k = 0;
		for (Object purchasePrice : purchasePrices) {
//		    String []purchase_price=	purchasePrice.split("&");
			JSONObject jsonObject = JSONObject.parseObject(String.valueOf(purchasePrice));
//			JSONObject jsonObject = JSONObject.parseObject(purchasePrice;

		    ProductPurchase productPurchase1 = new ProductPurchase();
			productPurchase1.setOrders(k++);
		    productPurchase1.setProductId(product.getId().toString());
		    productPurchase1.setPurchaseId(jsonObject.getString("id"));
		    productPurchase1.setPrice(new BigDecimal(jsonObject.getString("price")));
			productPurchaseService.insertSelective(productPurchase1);//插入购买方式



			Map<String,Object> purchase_code_map = new HashMap<>();
			System.out.println(productPurchase1.getId());
			purchase_code_map.put("prouctPurchaseId",productPurchase1.getId());

			System.out.println(jsonObject.getString("activation"));
			String activation = jsonObject.getString("activation");
			if(activation!=null&&!activation.equals("")){

				JSONArray jsonArray = JSONArray.parseArray(activation);
				List<String> codes = JSONObject.parseArray(jsonArray.toJSONString(), String.class);
				purchase_code_map.put("purchaseCodes",codes);
				productActivationCodeService.insertSelectiveMap(purchase_code_map);//插入激活码
			}


		}

		String [] titles = {"软件概览","推荐配置","典型病例","软件教程","临床文献","用户评价","产品彩页"};
		String [] contents = {overview,configure,cases,tutorial,clinical,evaluation,colorpage};
//		八大项
			for (int i = 0; i < titles.length; i++) {
				Specifications specifications = new Specifications();
				specifications.setContent(String.valueOf(contents[i]));
				specifications.setTitle(titles[i]);
				specifications.setOrders(i);
				specifications.setProduct(product.getId());
				specificationsService.insertSelective(specifications);

			}
			txManager.commit(status);
		}catch (Exception e){
			System.out.println("事务回滚了"+e.getMessage());
			//强制手动事务回滚
			txManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return "redirect:list";
	}

	/**
	 * 商品统计
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
							 String name,String sn ,Long productCategory ,String startDate,String endDate ,String companyName) {
		Map<String,Object> map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		Subject subject = SecurityUtils.getSubject();
		map.put("companyId",companyService.getCompanyId());
		map.put("name",name);
		map.put("sn",sn);
		map.put("productCategory",productCategory);
		map.put("companyName",companyName);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		Page<Map<String, Object>> page  = PageHelper.startPage(pageNum,pageSize);

		List<Map<String, Object>> all = productService.getProductStatistics(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("name",name);
		map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(map);
		model.addAttribute("productCategories",productCategories);//商品分类
		model.addAttribute("sn",sn);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("companyName",companyName);
		List<Long> purchasesLackStock = productPurchaseService.getPurchasesLackStock(map);
		model.addAttribute("purchasesLackStock",purchasesLackStock);
		return "web/product/statistics";
	}


	/**
	 * 访问量统计
	 * @param model
	 * @return
	 */
//	@RequestMapping("/visit")
//	public String visit(Model model) {
//
//		Map<String,Object> query_map = new HashMap<>();
//		query_map.put("companyId",companyService.getCompanyId());
//		//按商品名称统计访问量
//		List<Map<String, Object>> productStatistics = productStatisticsService.getProductStatisics(query_map);
//		//按商品分类统计访问量
//		List<Map<String, Object>> productCategoryStatistics = productStatisticsService.getProductCategoryStatistics(query_map);
//		//按供应商统计访问量
////		List<Map<String, Object>> productCompanyStatistics = productStatisticsService.getProductCompanyStatistics(query_map);
//
//		//按供商品统计销量
//		List<Map<String, Object>> productSalesStatistics = productService.getProductSalesStatistics(query_map);
//
//		//按城市地区统计访问量
//		List<Map<String, Object>> productProvinceStatistics = productStatisticsService.getProductProvinceStatistics(query_map);
//		model.addAttribute("productCategoryStatistics",productCategoryStatistics);
//		model.addAttribute("productStatistics",productStatistics);
////		model.addAttribute("productCompanyStatistics",productCompanyStatistics);
//		model.addAttribute("productSalesStatistics",productSalesStatistics);
//		model.addAttribute("productProvinceStatistics",productProvinceStatistics);
//
//		String year = DateUtil.getDatetoString("YYYY",new Date());
//		query_map.put("year",year);
//		List<Map> yearOrderSums = orderService.getYearOrderSum(query_map);//产品订单量统计
//		model.addAttribute("yearOrderSums", yearOrderSums);
//
//		List<Map> demonstrationStatisticStatisics = demonstrationStatisticsService.getDemonstrationStatisticStatisics(query_map);//产品申请演示统计
//		model.addAttribute("demonstrationStatisticStatisics", demonstrationStatisticStatisics);
//
//		return "web/product/visit";
//
//	}



	@RequestMapping("/visit")
	public String visit(Model model) {
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		List<Map> orderHospital = orderService.getOrderHospital(query_map);
		model.addAttribute("orderHospital", orderHospital);//医院排名分析
		List<Map> orderArea = orderService.getOrderArea(query_map);
		for (Map map : orderArea) {
			Map area = areaService.parentArea(Long.valueOf(map.get("area").toString()));
			map.put("areaName",area.get("name"));
		}
		model.addAttribute("orderArea", orderArea);//城市排名分析

		List<Map> countMemberSource = memberService.getCountMemberSource(query_map);
		model.addAttribute("countMemberSource", countMemberSource);//用户来源分析

		List<Map> memberSearch = articleStatisticsService.getMemberSearch(query_map);
		model.addAttribute("memberSearch", memberSearch);//用户搜索分析

		return "web/product/userDataStatistics";
	}


	/**
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/searchVisit", method = RequestMethod.POST)
	@ResponseBody
	public String searchVisit(Integer type,String startDate,String endDate){
		JSONObject jsonObject = new JSONObject();
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			query_map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			query_map.put("endDate",end_Date);
		}
		if (type==1){
			List<Map> orderHospital = orderService.getOrderHospital(query_map);
			jsonObject.put("orderHospital", orderHospital);//医院排名分析
		}
		if (type==2){
			List<Map> orderArea = orderService.getOrderArea(query_map);
			for (Map map : orderArea) {
				Map area = areaService.parentArea(Long.valueOf(map.get("area").toString()));
				map.put("areaName",area.get("name"));
			}
			jsonObject.put("orderArea", orderArea);//城市排名分析
		}
		if (type==3){
			List<Map> countMemberSource = memberService.getCountMemberSource(query_map);
			jsonObject.put("countMemberSource", countMemberSource);//用户来源分析
		}
		if (type==4){
			List<Map> memberSearch = articleStatisticsService.getMemberSearch(query_map);
			jsonObject.put("memberSearch", memberSearch);//用户搜索分析
		}

		return ResultUtil.success(jsonObject);
	}


	/**
	 * 申请演示统计
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/demonstrationStatisticStatisics", method = RequestMethod.POST)
	@ResponseBody
	public String companySales(Long year){
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",companyService.getCompanyId());
		query_map.put("year",year);
		List<Map> demonstrationStatisticStatisics = demonstrationStatisticsService.getDemonstrationStatisticStatisics(query_map);//产品申请演示统计
		return ResultUtil.success(demonstrationStatisticStatisics);
	}




	/**
	 * 编辑商品
	 * @param model
	 * @param product
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,Product product
						,String productPurchase
						,String productSoftwarePackage
						,@RequestParam("productimage") MultipartFile productImage
//						,@RequestParam("ontrialpackage") MultipartFile ontrialPackage
//						,@RequestParam("productpackage") MultipartFile productPackage
						,@RequestParam("productvideo") MultipartFile productVideo
						,@RequestParam("inputProductImage") MultipartFile[] inputProductImage
						,String overview //软件概览
						,String configure //推荐配置
						,String cases //典型病例
						,String tutorial //软件教程
						,String clinical //临床文献
						,String evaluation //用户评价
						,String colorpage  //产品彩页
	){

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			System.out.println("编辑商品");
		if (product.getProductCategory()!=null){
			ProductCategory productCategory = productCategoryService.selectByPrimaryKey(product.getProductCategory());
			ProductCategory parentProductCategory = productCategoryService.selectByPrimaryKey(productCategory.getParent());
			//供应商补0操作
			String companyId=String.format("%03d%n", companyService.getCompanyId());
			String productCount=String.format("%04d%n", product.getId());
			String sn = parentProductCategory.getSeoTitle()+"-"+productCategory.getSeoTitle()+"-"+companyId.trim()+"-"+productCount.trim();
			product.setSn(sn);
		}

		if (product.getPriceExplain().equals("")){//隐层价格说明
			product.setPriceExplain("面议");
		}
		if (product.getIsHideprice()==null){//是否隐层价格
			product.setIsHideprice(false);
		}

		if (product.getIsShowOntrialPackageUrl()==null){//申请试用是否显示
			product.setIsShowOntrialPackageUrl(false);
		}

		if (product.getIsShowproductPackageUrl()==null){//立即下载是否显示
			product.setIsShowproductPackageUrl(false);
		}


		if (product.getIsDemonstration()==null){//申请演示  是否显示
			product.setIsDemonstration(false);
		}

		if (product.getIsAddService()==2){//是否增值服务产品
			product.setProductId(null);
		}

		if (product.getIsScientific()==null){ //仅供临床科研使用
			product.setIsScientific(false);
		}

		//商品logo上传路径
		String product_logo_img = FileUtils.upload(productImage, uploadProductLogoImg);
		if (product_logo_img!=null){
			product.setImage(product_logo_img);
		}

		//商品试用包上传路径
//		String ontrial_package=  FileUtils.upload(ontrialPackage, uploadOntrialPackage);
//		if (ontrial_package!=null){
//			product.setOntrialPackage(ontrial_package);
//		}

		//商品下载包上传路径
//		String product_package= FileUtils.upload(productPackage, uploadProductPackage);
//		if (product_package!=null){
//			product.setProductPackage(product_package);
//		}

//		商品演示小视频上传路径
		String product_video=   FileUtils.upload(productVideo, uploadProductVideo);
		if (product_video!=null){
			product.setVideo(product_video);
		}
		//		上传多图和上传视频
		product.setStatus(1l);//编辑商品将状态置为待审核
		Integer count = productService.updateByPrimaryKeySelective(product);

			Map query_map = new HashMap();
			query_map.put("productId",product.getId());
			productMealService.updateProductMeal(query_map);//将套餐 也变为  待审核专挑


			//编辑多图
		Map<String,Object> productImgMap = new HashMap<>();
		List<Map<String,Object>> productImages = new ArrayList<>();
		Integer orders = 0;
		for (MultipartFile multipartFile : inputProductImage) {
			Map<String,Object> product_img_map = new HashMap<>();
			product_img_map.put("orders",orders++);
			product_img_map.put("productId",product.getId());
			ProductImg productImg = productImgService.selectByProductAndOrders(product_img_map);
			//商品多图编辑，用商品id 和 排序的序号去查询看能否查到
			if (productImg==null){
				if (multipartFile.getSize()>0){
					//商品详情上传路径
					String product_img=  FileUtils.upload(multipartFile, uploadProductImg);
					product_img_map.put("large",product_img);
					product_img_map.put("medium",product_img);
					product_img_map.put("source",product_img);
					product_img_map.put("thumbnail",product_img);
//					product_img_map.put("orders",orders++);
					product_img_map.put("title",product.getId()+"号商品图片");
					productImages.add(product_img_map);
				}
			}else {
				if (multipartFile.getSize()>0){
					String product_img=  FileUtils.upload(multipartFile, uploadProductImg);
					product_img_map.put("large",product_img);
					product_img_map.put("medium",product_img);
					product_img_map.put("source",product_img);
					product_img_map.put("thumbnail",product_img);
//					product_img_map.put("orders",orders++);
					product_img_map.put("title",product.getId()+"号商品图片");
					Integer updateProductImgCount = productImgService.updateByProductId(product_img_map);
					System.out.println(product.getId()+"号商品图片,编辑结果为:"+updateProductImgCount);
				}

			}
		}
		productImgMap.put("product",product.getId());
		productImgMap.put("productImages",productImages);
		if(productImages.size()>0){
			Integer insetProductImgCount = productImgService.insertMap(productImgMap);
			logger.info("添加商品结果:"+count+"新增商品id为:"+product.getId());
		}

		if (productSoftwarePackage!=null){
			productSoftwarePackageService.deleteProductSoftwarePackage(product.getId());//先删除所有的软件版本
			JSONArray productSoftwarPacaageJsonArray = JSONArray.parseArray(productSoftwarePackage);
			List<Map> list = JSONObject.parseArray(productSoftwarePackage,Map.class);

			Map productSoftwarePackageMap = new HashMap();
			productSoftwarePackageMap.put("productSoftwarPacaages",productSoftwarPacaageJsonArray);
			productSoftwarePackageMap.put("productId",product.getId());
			productSoftwarePackageService.insertSelectiveMap(productSoftwarePackageMap);
		}

//		//先删除所有的购买方式
//		Integer deleteProductPurchasecount = productPurchaseService.deleteByProductId(product.getId());

		JSONArray jsonArray = JSONArray.parseArray(productPurchase);
			for (Object object : jsonArray) {
				JSONObject jsonObject = JSONObject.parseObject(object.toString());
				Map<String,Object> purchasePriceMap = new HashMap<>();
				purchasePriceMap.put("productId",product.getId());
				purchasePriceMap.put("purchaseId",jsonObject.getLong("id"));
				if (!jsonObject.getBoolean("check")){//  编辑得时候 去掉原先得购买方式 ，删除激活码  并删除  购买方式及价格
					Long productPurchaseId = productPurchaseService.getProductPurchaseId(purchasePriceMap);//根据产品id和购买方式id 查询本产品的购买方式id
						productActivationCodeService.deleteProductActivationCode(productPurchaseId);  //删除本产品购买方式的  激活码
						productPurchaseService.deleteByPrimaryKey(productPurchaseId);//再删除 本产品的购买方式
				}else {
					Long productPurchaseId = productPurchaseService.getProductPurchaseId(purchasePriceMap);//根据产品id和购买方式id 查询本产品的购买方式id
					if (productPurchaseId==null){
						ProductPurchase newProductPurchase = new ProductPurchase();
						newProductPurchase.setOrders(0);
						newProductPurchase.setProductId(String.valueOf(product.getId()));
						newProductPurchase.setPrice(new BigDecimal(jsonObject.getString("price")));
						newProductPurchase.setPurchaseId(jsonObject.getString("id"));
						productPurchaseService.insertSelective(newProductPurchase);
						Map<String,Object> purchase_code_map = new HashMap<>();
						purchase_code_map.put("prouctPurchaseId",newProductPurchase.getId());
						String activation = jsonObject.getString("activation");
						if(activation!=null&&!activation.equals("")){
							JSONArray activationJsonArray = JSONArray.parseArray(activation);
							List<String> codes = JSONObject.parseArray(activationJsonArray.toJSONString(), String.class);
							purchase_code_map.put("purchaseCodes",codes);
							productActivationCodeService.insertSelectiveMap(purchase_code_map);//插入激活码
						}
					}else {
						ProductPurchase productPurchase1 = new ProductPurchase();
						productPurchase1.setId(productPurchaseId);
						productPurchase1.setPrice(jsonObject.getBigDecimal("price"));
						productPurchaseService.updateByPrimaryKeySelective(productPurchase1);
						Map<String,Object> purchase_code_map = new HashMap<>();
						purchase_code_map.put("prouctPurchaseId",productPurchaseId);
						String activation = jsonObject.getString("activation");
						if(activation!=null&&!activation.equals("")){
							JSONArray activationJsonArray = JSONArray.parseArray(activation);
							List<String> codes = JSONObject.parseArray(activationJsonArray.toJSONString(), String.class);
							purchase_code_map.put("purchaseCodes",codes);
							productActivationCodeService.insertSelectiveMap(purchase_code_map);//插入激活码
						}
					}
				}
			}

		String [] titles = {"软件概览","推荐配置","典型病例","软件教程","临床文献","用户评价","产品彩页"};
		String [] contents = {overview,configure,cases,tutorial,clinical,evaluation,colorpage};
		//先删除
		specificationsService.deleteByProduct(product.getId());
//		八大项
		for (int i = 0; i < titles.length; i++) {
			Specifications specifications = new Specifications();
			specifications.setContent(String.valueOf(contents[i]));
			specifications.setTitle(titles[i]);
			specifications.setOrders(i);
			specifications.setProduct(product.getId());
			specificationsService.insertSelective(specifications);

		}
			System.out.println("编辑商品结果:"+count);
			txManager.commit(status);
		}catch (Exception e){
			System.out.println("事务回滚了");
			//强制手动事务回滚
			txManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return "redirect:list";
	}

	/**
	 * 编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Product product = productService.selectByPrimaryKey(id);
		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
//		map.put("adminid",admin.getId());
		product_query_map.put("companyid",companyService.getCompanyId());
		Page<Product> page  = PageHelper.startPage(1,10);//最新发布产品
//		product_query_map.put("companyid",companyService.getCompanyId());
		List<Product> all = (List<Product>) productService.getAll(product_query_map); //查询最新发布商品
		PageInfo<Product> pageInfo = new PageInfo<>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(10);
		pageInfo.setPageNum(1);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		product_query_map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
		ProductCategory productCategory = productCategoryService.selectByPrimaryKey(product.getProductCategory());//查找当前商品的所属分类
		ProductCategory parentProductCategory = productCategoryService.getPrentProductCategory(productCategory.getId());//查找当前商品的顶级分类


		product_query_map.put("productId",product.getId());
		List<ProductPurchase> productPurchases = productPurchaseService.getAll(product_query_map);//该商品的实际购买方式
		model.addAttribute("productPurchases",JSON.toJSON(productPurchases) );//该商品的实际购买方式

		product_query_map.put("isAddService",2);
		List<Product> isAddServiceProducts = (List<Product>) productService.getAll(product_query_map); //查询增值服务产品
		model.addAttribute("isAddServiceProducts",isAddServiceProducts);//增值服务产品
		model.addAttribute("products",all);//最新发布的商品


		List<String> purchaseIds = new ArrayList<>();
		Map<String,Object> map_purchase = new HashMap<>();
		for (ProductPurchase productPurchase : productPurchases) {
			purchaseIds.add(productPurchase.getPurchaseId());
			map_purchase.put(productPurchase.getPurchaseId().toString(),productPurchase.getPrice());
		}

		List<Specifications> specificationsList = specificationsService.getAll(product_query_map);
		Map<String,Object> specificationsMap = new HashMap<>();

//		这个是最傻逼的一种写法
// 		for (Specifications specification : specificationsList) {
//			specificationsMap.put(specification.getTitle(),specification.getContent());
//		}
		String [] titles = {"软件概览","推荐配置","典型病例","软件教程","临床文献","用户评价","产品彩页"};
		String [] contents = {"overview","configure","cases","tutorial","clinical","evaluation","colorpage"};

		for (int i = 0; i < contents.length; i++) {
			if (specificationsList.size()>=contents.length){
				specificationsMap.put(contents[i],specificationsList.get(i).getContent());
			}else {
				specificationsMap.put(contents[i],"");
			}

		}
		model.addAttribute("specificationsMap",specificationsMap);//当前商品详情(八大项)

		model.addAttribute("purchaseIds",purchaseIds);//当前商品的购买方式id
		System.out.println(JSON.toJSON(map_purchase));
		model.addAttribute("map_purchase", JSON.toJSON(map_purchase));//当前商品的购买方式

		model.addAttribute("productCategories",productCategories);//商品分类

//		model.addAttribute("purchases",purchases);//普通商品购买方式
//		model.addAttribute("assServicePurchases",isAddServicepurchases);//增值商品购买方式
		model.addAttribute("product",product);
		model.addAttribute("productCategory",productCategory);//该商品当前分类
		model.addAttribute("parentProductCategory",parentProductCategory);//该商品所属父类
		List<ProductSoftwarePackage> productSoftwarePackages = productSoftwarePackageService.getProductSoftwarePackages(product_query_map);
		model.addAttribute("productSoftwarePackages",productSoftwarePackages);//所有的软件版本

		return "web/product/edit";
	}

//	查看
	@RequestMapping("/view")
	public String view(Model model,Long id) {
		Product product = productService.selectByPrimaryKey(id);
		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		Company company = companyService.selectByPrimaryKey(admin.getCompanyId());
//		map.put("adminid",admin.getId());
		product_query_map.put("companyid",companyService.getCompanyId());
		product_query_map.put("type",1);

		product_query_map.put("productId",product.getId());
		List<Purchase>  purchases = purchaseService.getParentPurchase(product_query_map);//该商品的顶级购买方式
		List<ProductPurchase> productPurchases = productPurchaseService.getAll(product_query_map);//该商品的实际购买方式
		List<ProductImg> listImg = productImgService.getProductImgs(id);
		model.addAttribute("listImg",listImg);
		//获取详情页商品图片（大图）
		if(listImg.size()>0){
			model.addAttribute("picture",listImg.get(0));
		}else{
			model.addAttribute("picture",null);
		}
		model.addAttribute("price",productPurchases.get(0).getPrice());
		model.addAttribute("product",product);
		model.addAttribute("company",company);
		model.addAttribute("purchases",purchases);
		ProductCategory parentProductCategory = productCategoryService.getPrentProductCategory(product.getProductCategory());
		model.addAttribute("parentProductCategory",parentProductCategory);
		//获取软件概览等八大项
		List<Specifications> listSpe =  specificationsService.getAll(product_query_map);
		model.addAttribute("listSpe",listSpe);

		return "web/product/view";
	}




	@PostMapping("/updateProductStatus")
	public String updateStatus(Model model, Product product,String content){
		System.out.println(">>>>>>");
		productService.updateByPrimaryKeySelective(product,content);
		return "redirect:list";
	}

	//	修改商品状态
	@PostMapping("/updateProduct")
	@ResponseBody
	public Object updateProduct(@RequestBody String params) {

		Product product = JSONObject.parseObject(params,Product.class);

	    Integer updateProduct = 	productService.updateByPrimaryKeySelective(product);
		if (updateProduct>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}

	//	修改商品状态
	@PostMapping("/updateProductStock")
	@ResponseBody
	public Object updateProductStock(@RequestBody String params) {
		System.out.println("》》》"+params);

		JSONObject jsonObject = JSONObject.parseObject(params);
		Long id = jsonObject.getLong("id");
		Integer stock = jsonObject.getInteger("stock");
		Product product = productService.selectByPrimaryKey(id);
		product.setStock(product.getStock()+stock);
		Integer updateProduct = 	productService.updateByPrimaryKeySelective(product);
		if (updateProduct>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}


	//补货激活码
	@PostMapping("/replenishment")
	@ResponseBody
	public String replenishment(@RequestParam("file") MultipartFile file,Long id) throws IOException {

		if (file.getSize()<=0){
			return ResultUtil.error("文件错误!");
		}

		File toFile = null;
		InputStream ins = null;
		ins = file.getInputStream();
		toFile = new File(file.getOriginalFilename());
		inputStreamToFile(ins, toFile);
		ins.close();
		String codes = ExcelUtil.readExcel(toFile);//两种方法都可实现，这里使用最简便的方法
		System.out.println("codes:"+codes);

		Map<String,Object> purchase_code_map = new HashMap<>();

		purchase_code_map.put("prouctPurchaseId",id);

		List<String> purchaseCodes = JSONObject.parseArray(codes, String.class);
		purchase_code_map.put("purchaseCodes",purchaseCodes);
		Integer count = productActivationCodeService.insertSelectiveMap(purchase_code_map);//插入激活码
		if (count>0){
			return ResultUtil.success();
		}
		return ResultUtil.error("补货失败!");



	}


	//删除培训包
	@PostMapping("/deleteTrainPackages")
	@ResponseBody
	public Object deleteTrainPackages(@RequestBody JSONObject jsonobject) {

		Long id = jsonobject.getLong("id");
		Map query_map = new HashMap();
		query_map.put("productPackageId",id);
		List productMeal = productMealService.getAll(query_map);
		if (productMeal.size()>0) {
			return ResultUtil.error("该培训包有绑定套餐商品不可删除！");
		}
		Integer updateProduct = 	productService.deleteByPrimaryKey(id);
		if (updateProduct>0){
			return ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}


	//读取excel激活码
	@PostMapping("/activations")
	@ResponseBody
	public String activations(@RequestParam("file") MultipartFile file) throws IOException {

		if (file.getSize()<=0){
			return null;
		}

		File toFile = null;
		InputStream ins = null;
		ins = file.getInputStream();
		toFile = new File(file.getOriginalFilename());
		inputStreamToFile(ins, toFile);
		ins.close();

		return ExcelUtil.readExcel(toFile);//两种方法都可实现，这里使用最简便的方法





//		String path = "/excel/";
//
//		//		获取静态资源路径
//		String staticPath= ClassUtils.getDefaultClassLoader().getResource("static").getPath()+path;
//		File files = new File(staticPath);
//		if (!files.isDirectory()) {
//			files.mkdirs();
//		}
//
//		// 获取文件名
//		String fileName = file.getOriginalFilename();
//		System.out.println("上传的文件名为：" + fileName);
//		// 获取文件的后缀名
//		String suffixName = fileName.substring(fileName.lastIndexOf("."));
//		System.out.println("上传的后缀名为：" + suffixName);
//		fileName= Long.toString(System.currentTimeMillis())+suffixName;//重新命名图片，变成随机的名字
//		//使用原文件名
//		String realPath = staticPath  + fileName;
//
//		File dest = new File(realPath);
//
//		//判断文件父目录是否存在
//		if (!dest.getParentFile().exists()) {
//			dest.getParentFile().mkdir();
//		}
//		try {
//			//保存文件
//			file.transferTo(dest);
//			return ExcelUtil.readExcel(staticPath+fileName);
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
	}

	private static void inputStreamToFile(InputStream ins, File file) {
		try {
			FileOutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@ResponseBody
	@RequestMapping(value = "/selectGuige",method = RequestMethod.POST)
	public Object selectGuige(Model model, @Param("id") Long id, @Param("parentid") Long parentid) {
		//根据商品id和父类id查询购买方式下的购买规格
		Map<String,Object> purchasemap=new HashMap<>();
		purchasemap.put("productId",id);
		purchasemap.put("parent",parentid);
		List<Map<String,Object>> list = purchaseService.selectGuige(purchasemap);
		return list;

	}


	/**
	 * 根据分类查询所有购买方式
	 * @param model
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPurchase",method = RequestMethod.POST)
	public Object getPurchase(Model model, Long type) {
		Map purchasemap=new HashMap<>();
		purchasemap.put("type",type);
		List<Purchase> purchases = purchaseService.getAll(purchasemap);
		for (Purchase purchase : purchases) {
			Map<String,Object> query_map = new HashMap<>();
			query_map.put("parent",purchase.getId());
			purchase.setPurchases(purchaseService.getAll(query_map));
		}
		return purchases;

	}


	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody JSONObject jsonObject ){

		Product product = new Product();
		product.setId(jsonObject.getLong("id"));
		product.setIsDeleted(true);
		Integer deleteByPrimaryKey = productService.updateByPrimaryKeySelective(product);
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}


	/**
	 * 查询该供应商下的所有产品
	 * @return
	 */
	@PostMapping("/queryProducts")
	@ResponseBody
	public String queryProducts( @RequestBody JSONObject jsonObject  ){
		Long productCategoryId = jsonObject.getLong("productCategoryId");
		Map<String,Object> map = new HashMap<>();
		Admin admin = adminService.getCurrent();
		map.put("companyid",admin.getCompanyId());
		map.put("productCategory",productCategoryId);
		List<Map<String, Object>> all = productService.getAllMap(map);
		return  ResultUtil.success(all);
	}


	/**
	 * 上传软件包
	 * @param productSofwarePackage
	 * @return
	 */
	@PostMapping("/uploadSofwarePackage")
	@ResponseBody
	public String uploadSofwarePackage(@RequestParam("sofwarePackage") MultipartFile productSofwarePackage){
		String sofwarePackageUrl=  FileUtils.upload(productSofwarePackage, "/sofwarePackage/");
		return sofwarePackageUrl;
	}













}
