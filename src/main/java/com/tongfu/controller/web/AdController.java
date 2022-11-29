package com.tongfu.controller.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Ad;
import com.tongfu.entity.AdIncome;
import com.tongfu.entity.AdPosition;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.*;
import com.tongfu.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller("webad")
@RequestMapping("/web/ad")
public class AdController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	CompanyService companyService;

	@Autowired
	AdService adService;

	@Autowired
	AdPositionService adPositionService;

	@Autowired
	private  AdIncomeService adIncomeService;

	@Autowired
	private ProductCategoryService productCategoryService;

	//广告图上传路径
	@Value("${ad-image}")
	private String adImage;

	//	微信appid
	@Value("${appid}")
	private String appid;

	//	商户号
	@Value("${mch_id}")
	private String mch_id;

	//	商户key
	@Value("${key}")
	private String key;




	/**
	 * 广告列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
					   String title, Long adPosition, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("title", title);
		map.put("adPosition", adPosition);
		map.put("companyId", companyService.getCompanyId());
		if (startDate != null && !startDate.equals("")) {
			Date start_Date = DateUtil.getStringtoDate(startDate + " 00:00:00", "MM/dd/yyyy HH:mm:ss");
			map.put("startDate", start_Date);
		}
		if (endDate != null && !endDate.equals("")) {
			Date end_Date = DateUtil.getStringtoDate(endDate + " 23:59:59", "MM/dd/yyyy HH:mm:ss");
			map.put("endDate", end_Date);
		}
		Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> all = (List<Map<String, Object>>) adService.getAll(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		List<AdPosition> adPositions = (List<AdPosition>) adPositionService.getAll(map);
		model.addAttribute("page", pageInfo);
		model.addAttribute("title", title);
		model.addAttribute("adPosition", adPosition);
		model.addAttribute("adPositions", adPositions);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "web/ad/list";
	}


	/**
	 * 发布广告页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		Map<String, Object> product_query_map = new HashMap<>();
		product_query_map.put("type", 1);
		List<AdPosition> adPositions = adPositionService.getAll(null);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
		model.addAttribute("productCategories", productCategories);
		model.addAttribute("adPositions", adPositions);
		return "web/ad/add";
	}


	/**
	 * 发布广告
	 *
	 * @param ad
	 * @param adImageFile
	 * @return
	 */
	@PostMapping("/save")
	public String save(Ad ad, String singledaterange, @RequestParam("adImage") MultipartFile adImageFile) {
		ad.setModifyDate(new Date());
		ad.setCreateDate(new Date());
		ad.setIsDeleted(false);
		ad.setStatus(1l);
		ad.setType(2);
		ad.setPaymentStatus(1l);
		AdPosition adPosition = adPositionService.selectByPrimaryKey(ad.getAdPosition());
		BigDecimal adPrice = new BigDecimal(0);
		Integer differenceDays = DateUtil.getDifferenceDays(ad.getBeginDate(), ad.getEndDate());//计算该广告投放天数
		if (adPosition.getPriceType() == 1) { //按月计算queryWxPay
			Double days = differenceDays / 30 + (differenceDays % 30 > 15 ? 1 : 0.5);//此处计算总共展示多少个月 不够半月按半月计算，超过半月按一个月计算
			adPrice = adPosition.getPrice().multiply(new BigDecimal(days));
		}
		if (adPosition.getPriceType() == 2) { //按天计算
			adPrice = adPosition.getPrice().multiply(new BigDecimal(differenceDays));
		}

		ad.setPrice(adPrice.setScale(2, BigDecimal.ROUND_HALF_UP));//四舍五入保留两位小数
		ad.setCompanyId(companyService.getCompanyId());
		//广告图路径
		String ad_image = FileUtils.upload(adImageFile, adImage);
		ad.setPath(ad_image);
		if (singledaterange != null) {
			System.out.println(singledaterange.split("-")[0] + ">>>>" + singledaterange.split("-")[1]);
			Date startDate = DateUtil.getStringtoDate((singledaterange.split("-")[0]).trim() + " 00:00:00", "MM/dd/yyyy HH:mm:ss");
			Date endDate = DateUtil.getStringtoDate((singledaterange.split("-")[1]).trim() + " 23:59:59", "MM/dd/yyyy HH:mm:ss");
			ad.setBeginDate(startDate);
			ad.setEndDate(endDate);
		}
		Integer conunt = adService.insertSelective(ad);
		logger.info("发布广告结果:" + conunt);
		return "redirect:list";
	}

	@PostMapping("/update")
	public String update(Ad ad, String singledaterange, @RequestParam("adImage") MultipartFile adImageFile) {

		//广告图路径
		if (adImageFile.getSize() > 0) {
			String ad_image = FileUtils.upload(adImageFile, adImage);
			ad.setPath(ad_image);
		}
		if (singledaterange != null) {
			System.out.println(singledaterange.split("-")[0] + ">>>>" + singledaterange.split("-")[1]);
			Date startDate = DateUtil.getStringtoDate((singledaterange.split("-")[0]).trim() + " 00:00:00", "MM/dd/yyyy HH:mm:ss");
			Date endDate = DateUtil.getStringtoDate((singledaterange.split("-")[1]).trim() + " 23:59:59", "MM/dd/yyyy HH:mm:ss");
			ad.setBeginDate(startDate);
			ad.setEndDate(endDate);
		}
		ad.setStatus(1l);
	    Ad ad1 = adService.selectByPrimaryKey(ad.getId());
	    ad.setPrice(ad1.getPrice());
	    if (ad1.getPaymentStatus()!=2){
			AdPosition adPosition = adPositionService.selectByPrimaryKey(ad.getAdPosition());
			BigDecimal adPrice = new BigDecimal(0);
			Integer differenceDays = DateUtil.getDifferenceDays(ad.getBeginDate(), ad.getEndDate());//计算该广告投放天数
			if (adPosition.getPriceType() == 1) { //按月计算
				Double days = differenceDays / 30 + (differenceDays % 30 > 15 ? 1 : 0.5);//此处计算总共展示多少个月 不够半月按半月计算，超过半月按一个月计算
				adPrice = adPosition.getPrice().multiply(new BigDecimal(days));
			}
			if (adPosition.getPriceType() == 2) { //按天计算
				adPrice = adPosition.getPrice().multiply(new BigDecimal(differenceDays));
			}
			ad.setPrice(adPrice);
	    }
		Integer conunt = adService.updateByPrimaryKeySelective(ad);
		logger.info("发布广告结果:" + conunt);
		return "redirect:list";
	}

	/**
	 * 发布广告页面knowhowlist
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model, Long id) {
		Ad ad = adService.selectByPrimaryKey(id);
		Map<String, Object> product_query_map = new HashMap<>();
		product_query_map.put("type", 1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(product_query_map);
		List<AdPosition> adPositions = adPositionService.getAll(null);
		model.addAttribute("ad", ad);
		model.addAttribute("productCategories", productCategories);
		model.addAttribute("adPositions", adPositions);

		return "web/ad/edit";
	}


	@RequestMapping("/delete")
	public String delete(Long id) {
		Integer conunt = adService.deleteByPrimaryKey(id);
		logger.info("删除广告结果:" + conunt);
		return "redirect:list";
	}

	@RequestMapping(value = "/check_ad_position", method = RequestMethod.GET)
	@ResponseBody
	public AdPosition checkAdPosition(@RequestParam Long id) {
		return adPositionService.selectByPrimaryKey(id);
	}


	//支付
	@RequestMapping(value = "/wxPay", method = RequestMethod.GET)
	public void wxPay(HttpServletRequest req, HttpServletResponse resp,
						@RequestParam Long adId) throws Exception {
		Ad ad = adService.selectByPrimaryKey(adId);
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", appid);	//	是	公众账号ID	String(32)	wxd678efh567hg6787	微信支付分配的公众账号ID（企业号corpid即为此appId）
		data.put("mch_id", mch_id);	//	是	商户号	String(32)	1230000109	微信支付分配的商户号
//        data.put("device_info", "");	//	否	设备号	String(32)	1.3467E+13	自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
		data.put("nonce_str", UUIDUtil.getUUID());	//	是	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，长度要求在32位以内。推荐随机数生成算法
		data.put("sign_type", "MD5");	//	否	签名类型	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
		data.put("body", ad.getTitle());	//	是	商品描述	String(128)	腾讯充值中心-QQ会员充值	商品简单描述，该字段请按照规范传递，具体请见参数规定
//		data.put("detail", "");	//	否	商品详情	String(6000)		商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见“单品优惠参数说明”
//		data.put("attach", "");	//	否	附加数据	String(127)	深圳分店	附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。

//		商户订单号为广告的开始日期拼接截止日期拼接id
		String out_trade_no = (String.valueOf(ad.getBeginDate().getTime())+String.valueOf(ad.getEndDate().getTime())+String.format("%06d%n", adId)).trim();
		data.put("out_trade_no", out_trade_no);	//	是	商户订单号	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
		data.put("fee_type", "CNY");	//	否	标价币种	String(16)	CNY	符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
//		价格
        Integer total_fee = ad.getPrice().multiply(new BigDecimal(100)).intValue();
		data.put("total_fee", String.valueOf(total_fee));	//	是	标价金额	Int	88	订单总金额，单位为分，详见支付金额
		data.put("spbill_create_ip", "106.13.123.1");	//	是	终端IP	String(64)	123.12.12.123	支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
//		data.put("time_start", "");	//	否	交易起始时间	String(14)	2.00912E+13	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//		data.put("time_expire", "");	//	否	交易结束时间	String(14)	2.00912E+13	订time_expire只能第一次下单传值，不允许二次修改，二次修改系统将报错。如用户支付失败后，需再次支付，需更换原订单号重新下单。建议：最短失效时间间隔大于1分钟
//		data.put("goods_tag", "");	//	否	订单优惠标记	String(32)	WXG	订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
		data.put("notify_url", "https://www.baidu.com");	//	是	通知地址	String(256)	http://www.weixin.qq.com/wxpay/pay.php	异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
		data.put("trade_type", "NATIVE");	//	是	交易类型	String(16)	JSAPI	JSAPI -JSAPI支付NATIVE -Native支付APP -APP支付			说明详见参数规定
//		data.put("product_id", "");	//	否	商品ID	String(32)	1.22354E+22	trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
//		data.put("limit_pay", "");	//	否	指定支付方式	String(32)	no_credit	上传此参数no_credit--可限制用户不能使用信用卡支付
		data.put("key",key);
		String sign = WxPayUtil.createSign(data);
		data.put("sign",sign);	//
		data.remove("key");
		String data_xml = XmlUtil.mapToXml(data);
		String return_data_xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder",data_xml);
		System.out.println("统一下单结果:"+return_data_xml);
		Map<String,Object> return_Data_Map = XmlUtil.getXmlToMap(return_data_xml);
		String content = "";
		if (return_Data_Map.get("return_code").toString().equals("SUCCESS")){
			if (return_Data_Map.get("result_code").toString().equals("SUCCESS")){
				String code_url = return_Data_Map.get("code_url").toString();
				content = code_url;
			}else{
				content = return_Data_Map.get("err_code_des").toString();
			}

		}else {
			content = return_Data_Map.get("return_msg").toString();
		}
		QRCoderUtil.creatRrCode(content, 128,128,resp);
	}

//	查询账单
	@RequestMapping(value = "/queryWxPay", method = RequestMethod.GET)
	@ResponseBody
	public Boolean queryWxPay(@RequestParam Long id) throws Exception {
		Ad ad = adService.selectByPrimaryKey(id);
		Map<String, String> data = new HashMap<String, String>();
		data.put("appid", appid);	//	是	公众账号ID	String(32)	wxd678efh567hg6787	微信支付分配的公众账号ID（企业号corpid即为此appId）
		data.put("mch_id", mch_id);	//	是	商户号	String(32)	1230000109	微信支付分配的商户号
		data.put("nonce_str", UUIDUtil.getUUID());	//	是	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，长度要求在32位以内。推荐随机数生成算法
		data.put("sign_type", "MD5");	//	否	签名类型	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
		String out_trade_no = (String.valueOf(ad.getBeginDate().getTime())+String.valueOf(ad.getEndDate().getTime())+String.format("%06d%n", id)).trim();
		data.put("out_trade_no", out_trade_no);	//	是	商户订单号	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号

		data.put("key",key);
		String sign = WxPayUtil.createSign(data);
		data.put("sign",sign);	//
		data.remove("key");
		String data_xml = XmlUtil.mapToXml(data);
		String return_data_xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/orderquery",data_xml);
		System.out.println("下单查询结果:"+return_data_xml);
		Map<String,Object> return_Data_Map = XmlUtil.getXmlToMap(return_data_xml);
		if (return_Data_Map.get("return_code").toString().equals("SUCCESS")){
			if (return_Data_Map.get("result_code").toString().equals("SUCCESS")){
				if (return_Data_Map.get("trade_state").toString().equals("SUCCESS")){
					ad.setPaymentStatus(2l);
					Integer updateAd = adService.updateByPrimaryKeySelective(ad);
					AdIncome adIncome = new AdIncome();
					adIncome.setCreateDate(new Date());
					adIncome.setPrice(ad.getPrice());
					adIncome.setAdId(ad.getId());
					adIncomeService.insertSelective(adIncome);
					return updateAd==1;
				}else{
					return false;
				}
			}else{
				return false;
			}

		}
		return false;

	}




}
