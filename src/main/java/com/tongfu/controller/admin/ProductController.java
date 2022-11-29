package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/admin/product")
public class ProductController{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductPurchaseService productPurchaseService;

	@Autowired
	private ProductActivationCodeService productActivationCodeService;

	@Autowired
	private ProductVerifyService productVerifyService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductStatisticsService productStatisticsService;

	@Autowired
	private ProductImgService productImgService;

	@Autowired
	private SpecificationsService specificationsService;

	@Autowired
	private PlatformTransactionManager txManager;

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
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sn
	 * @param productCategory
	 * @param isMarketable
	 * @param companyId
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String name,String sn ,Long productCategory ,Boolean isMarketable,Long companyId,Long status,String startDate,String endDate) {
		Map<String,Object> map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();

		map.put("name",name);
		map.put("sn",sn);
		map.put("productCategory",productCategory);
		map.put("isMarketable",isMarketable);
		map.put("status",status);
		map.put("companyid",companyId);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		Page<Map<String, Object>> page  = PageHelper.startPage(pageNum,pageSize);
//		List<Product> all = (List<Product>) productService.getAll(map);
//		PageInfo<Product> pageInfo = new PageInfo<Product>();
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
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);
		model.addAttribute("sn",sn);
		model.addAttribute("status",status);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("companyId",companyId);
		return "admin/product/list";
	}


	@PostMapping("/update")
	public String update(Model model,Product product,String productPurchase
			,@RequestParam("productimage") MultipartFile productImage
			,@RequestParam("ontrialpackage") MultipartFile ontrialPackage
			,@RequestParam("productpackage") MultipartFile productPackage
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
			String ontrial_package=  FileUtils.upload(ontrialPackage, uploadOntrialPackage);
			if (ontrial_package!=null){
				product.setOntrialPackage(ontrial_package);
			}

			//商品下载包上传路径
			String product_package= FileUtils.upload(productPackage, uploadProductPackage);
			if (product_package!=null){
				product.setProductPackage(product_package);
			}

//		商品演示小视频上传路径
			String product_video=   FileUtils.upload(productVideo, uploadProductVideo);
			if (product_video!=null){
				product.setVideo(product_video);
			}
			Integer count = productService.updateByPrimaryKeySelective(product);


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
		}

		return "redirect:list";
	}

	/**
	 * 编辑页面
	 * @param model
	 * @param id
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
		return "admin/product/edit";
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
//		map.put("activationMode",3);//培训包

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
		return "admin/product/trainPackages";
	}



	/**
	 * 商品统计
	 * @param model
	 * @return
	 */

	@RequestMapping("/visit")
	public String visit(Model model) {

		//按商品名称统计访问量
		List<Map<String, Object>> productStatistics = productStatisticsService.getProductStatisics(null);
		//按商品分类统计访问量
		List<Map<String, Object>> productCategoryStatistics = productStatisticsService.getProductCategoryStatistics(null);
		//按供应商统计访问量
		List<Map<String, Object>> productCompanyStatistics = productStatisticsService.getProductCompanyStatistics(null);
		//按城市地区统计访问量
		List<Map<String, Object>> productProvinceStatistics = productStatisticsService.getProductProvinceStatistics(null);
		model.addAttribute("productCategoryStatistics",productCategoryStatistics);
		model.addAttribute("productStatistics",productStatistics);
		model.addAttribute("productCompanyStatistics",productCompanyStatistics);
		model.addAttribute("productProvinceStatistics",productProvinceStatistics);
		return "admin/product/visit";

	}

	/**
	 *
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sn
	 * @param productCategory
	 * @param startDate
	 * @param endDate
	 * @param companyId
	 * @return
	 */

	@RequestMapping("/statistics")
	public String statistics(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
							 String name,String sn ,Long productCategory ,String startDate,String endDate ,Long companyId) {
		Map<String,Object> map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
//		map.put("cName",companyName);
		map.put("pName",name);
		map.put("companyid",companyId);
//		map.put("sn",sn);
		map.put("pCid",productCategory);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}
		Page<Map<String, Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String, Object>> all = productVerifyService.getProductVisit(map);
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
		List<Map<String,Object>> companys = companyService.getAll(null);
		model.addAttribute("companys",companys);
		model.addAttribute("sn",sn);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("companyId",companyId);
//		model.addAttribute("companyName",companyName);
		return "admin/product/statistics";
	}

	/**
	 * 认证信息统计
	 * @param model
	 * @return
	 */
	@RequestMapping("/authenticationStatistics")
	public String statistics(Model model) {

		List<Map> authenticationStatistics = productService.getAuthenticationStatistics(null);
		model.addAttribute("authenticationStatistics",authenticationStatistics);
		return "admin/product/authenticationStatistics";
	}



	//	查看
	@RequestMapping("/view")
	public String view(Model model,Long id) {
		Product product = productService.selectByPrimaryKey(id);
		Map<String,Object> product_query_map = new HashMap<>(	);
		Admin admin = adminService.getCurrent();
		Company company = companyService.selectByPrimaryKey(product.getCompanyId());
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


		return "admin/product/view";
	}


	/**
	 * 审核操作
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updateProductStatus")
	@ResponseBody
	public String updateProductStatus (@RequestBody String jsonObject) {
		Product product = JSON.parseObject(jsonObject, Product.class);
		Integer count = productService.updateByPrimaryKeySelective(product,product.getMessage());
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}

	/**
	 * 审核培训包
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updateTrainPackagesStatus")
	@ResponseBody
	public String updateTrainPackagesStatus(@RequestBody String jsonObject) {
		Product product = JSON.parseObject(jsonObject, Product.class);
		Integer count = productService.updateByPrimaryKeySelective(product,product.getMessage());
		logger.info("结果:"+count);
		if (count>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("操作失败");
	}


	/**
	 * 删除商品
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody JSONObject jsonObject ){
		//彻底删除商品：1先删除订单日志，2删除订单项，3删除订单，4删除购物车项，5删除购物车，6删除商品统计，7删除产品

		Integer deleteByPrimaryKey = productService.deleteByPrimaryKey(jsonObject.getLong("id"));
		if (deleteByPrimaryKey>0){
			return  ResultUtil.success();
		}
		return  ResultUtil.error("删除失败");
	}








}
