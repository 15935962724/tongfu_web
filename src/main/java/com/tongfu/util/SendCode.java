package com.tongfu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

/**
 * 短信验证码
 */
public class SendCode {


    /**
     * 发送短信验证码
     * @param map
     * @return
     */
    public static String getSendCodeMessage (Map<String,String> map ){
        map.put("respDataType","JSON");//响应数据类型，JSON 或 XML 格式。默认为JSON
//        accountSid	必填	开发者主账号（ACCOUNT SID）。开发者账号唯一标识符
//        map.put("templateid	",map.get("templateid"));//可选	模板ID，和短信内容必传一个
//        String  smsContent = "您正在注册SP平台，验证码为"+map.get("code")+"，该验证码5分钟有效，请勿泄露他人。";
//        map.put("smsContent","JSON");//	可选	短信内容，和模板ID必传一个
        map.put("to",map.get("phone"));//	必填	发送手机号，多个手机号，用英文逗号隔开
        // 时间戳
        long timestamp = System.currentTimeMillis();
        map.put("timestamp",String.valueOf(timestamp));//	必填	时间戳(毫秒)，格式：1547005945480
        // 签名
//        String sig = DigestUtils.md5Digest(map.get("accountSid") + "" + timestamp);

        String sig = DigestUtils.md5Hex((map.get("accountSid")+map.get("authToken")+timestamp));
        map.put("sig",sig);//	必填	签名：MD5(ACCOUNT SID + AUTH TOKEN + timestamp)。共32位（小写）
        //        expandId	可选	短信扩展号
        //        param	可选	短信变量，多个变量用英文逗号隔开
        //        subCode	可选	中转子码
        //        accountId	可选	子账号ID
        map.put("smsType","100000");//	可选	短信类型（100000：验证码通知，100003：会员营销）
        String url = (String) map.get("url");
        map.remove("url");
        map.remove("authToken");
        map.remove("code");
        String body = createSign(map);
        String returnData = HttpUtil.post(url,body);
        return returnData;
    }


    public static String createSign(Map<String,String> parameters){
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (!key.equals("key")){
                String value = parameters.get(key);
                if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                    prestr = prestr + key + "=" + value;
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }

        }
        return prestr;
    }

    public static void main(String[] args) {

        Map<String,String> map = new HashMap<>();

        map.put("url","https://openapi.miaodiyun.com/distributor/sendSMS");
        map.put("accountSid","6998eca293f9ab9ae822a556dca9affc");
        map.put("authToken","d22b144c1de959e4d3c72fbcd9df8d27");
        map.put("phone","15935962724");
        map.put("templateid","247797");
        Random random = new Random();
        String code = String.valueOf(random.nextInt(1000000));
        map.put("param",code);


        System.out.println("验证码为:"+code);

        String data = getSendCodeMessage(map);
        System.out.println(data);
        JSONObject json = JSON.parseObject(data);
        if (json.getString("respCode").equals("0000")){
            System.out.println("发送成功");
        }else {
            System.out.println(json.getString("respDesc"));
        }


    }








}
