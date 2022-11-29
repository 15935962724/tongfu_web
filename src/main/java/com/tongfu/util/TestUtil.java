package com.tongfu.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class TestUtil {


    public static Map<String,String> getMap(){
        Map<String, String> data = new HashMap<String, String>();

        data.put("appid", "wxc4f84cdd9ad8e0c8");	//	是	公众账号ID	String(32)	wxd678efh567hg6787	微信支付分配的公众账号ID（企业号corpid即为此appId）
        data.put("mch_id", "1387558402");	//	是	商户号	String(32)	1230000109	微信支付分配的商户号
//        data.put("device_info", "");	//	否	设备号	String(32)	1.3467E+13	自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("nonce_str", UUIDUtil.getUUID());	//	是	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，长度要求在32位以内。推荐随机数生成算法
        data.put("sign_type", "MD5");	//	否	签名类型	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
        data.put("body", "ceshi");	//	是	商品描述	String(128)	腾讯充值中心-QQ会员充值	商品简单描述，该字段请按照规范传递，具体请见参数规定
//		data.put("detail", "");	//	否	商品详情	String(6000)		商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见“单品优惠参数说明”
//		data.put("attach", "");	//	否	附加数据	String(127)	深圳分店	附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。

//		商户订单号为广告的开始日期拼接截止日期拼接id
        String out_trade_no = (String.valueOf(new Date().getTime())+String.valueOf(new Date().getTime())+String.format("%06d%n", 1)).trim();
        data.put("out_trade_no", out_trade_no);	//	是	商户订单号	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
        data.put("fee_type", "CNY");	//	否	标价币种	String(16)	CNY	符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
//		价格
//        Integer total_fee = ad.getPrice().multiply(new BigDecimal(100)).intValue();
        data.put("total_fee", String.valueOf(1));	//	是	标价金额	Int	88	订单总金额，单位为分，详见支付金额
        data.put("spbill_create_ip", "106.13.123.1");	//	是	终端IP	String(64)	123.12.12.123	支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
//		data.put("time_start", "");	//	否	交易起始时间	String(14)	2.00912E+13	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//		data.put("time_expire", "");	//	否	交易结束时间	String(14)	2.00912E+13	订time_expire只能第一次下单传值，不允许二次修改，二次修改系统将报错。如用户支付失败后，需再次支付，需更换原订单号重新下单。建议：最短失效时间间隔大于1分钟
//		data.put("goods_tag", "");	//	否	订单优惠标记	String(32)	WXG	订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
        data.put("notify_url", "https://www.baidu.com");	//	是	通知地址	String(256)	http://www.weixin.qq.com/wxpay/pay.php	异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        data.put("trade_type", "NATIVE");	//	是	交易类型	String(16)	JSAPI	JSAPI -JSAPI支付NATIVE -Native支付APP -APP支付			说明详见参数规定
//		data.put("product_id", "");	//	否	商品ID	String(32)	1.22354E+22	trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
//		data.put("limit_pay", "");	//	否	指定支付方式	String(32)	no_credit	上传此参数no_credit--可限制用户不能使用信用卡支付
        String sign = WxPayUtil.createSign(data);
        data.put("sign",sign);	//
        return data;
    }

    public static void main(String[] args) throws Exception {
        String body = XmlUtil.mapToXml(getMap());
        String data = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder",body);
        System.out.println("DATA："+data);


    }







}
