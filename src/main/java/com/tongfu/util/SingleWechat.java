/**
 * projectName: mylearn
 * fileName: SingleWechat.java
 * packageName: com.sunliang.wechat
 * date: 2020-02-16 22:51
 * copyright(c) 2017-2020 sunliang
 *//*

package com.tongfu.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

*/
/**
 * @version: V1.0
 * @author: WuKong
 * @className: SingleWechat
 * @packageName: com.sunliang.wechat
 * @description: 微信客户端
 * @data: 2020-02-16 22:51
 **//*

public class SingleWechat {


    public static final String WX_PAGE_URL = "https://wx.qq.com/";
    CloseableHttpClient https = HttpClients.createDefault();
    private String iamgeImg="D:\\data1\\wechat\\images";
    String redirect_uri="";
    */
/**
     * 初始化页面
     *//*

    public void initPage() {
        HttpGet httpGet = new HttpGet(WX_PAGE_URL);
        String html = "";
        try {
            HttpResponse response = https.execute(httpGet);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(html);
    }

    */
/**
     * 下载二维码之获取参数
     *//*

    public String wgetPng1() {
        String url = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=" + System.currentTimeMillis();
        HttpGet httpPost = new HttpGet(url);

        String html = "";
        try {
            HttpResponse response = https.execute(httpPost);
            HttpEntity entitySort = response.getEntity();
            html = EntityUtils.toString(entitySort, "utf-8");
            System.out.println(html);
            if (html.indexOf("window.QRLogin.code = 200") != -1) {
                return html.replace("window.QRLogin.code = 200; window.QRLogin.uuid = \"", "").replace("\";", "");
            }
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    */
/**
     * 下载二维码
     * @param appid
     * @return
     *//*

    public void getPng2(String appid)
    {
        String url="https://login.weixin.qq.com/qrcode/"+appid;
        HttpGet httpget = new HttpGet(url);
        System.out.println("获取二维码：Executing request " + httpget.getURI());//开始
        String html="";
        FileOutputStream fos;
        try {
            HttpResponse response = https.execute(httpget);
            System.out.println(response.getStatusLine());
            InputStream inputStream = response.getEntity().getContent();
            File file = new File(this.iamgeImg);
            if (!file.exists()) {
                file.mkdirs();
            }
            fos = new FileOutputStream("D:\\data1\\wechat\\images\\test.jpg");
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(data)) != -1) {
                fos.write(data, 0, len);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(html);
    }

    public int checklogin(String appid) {
        String url="https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid="+appid+"&tip=0&r=123&_="+System.currentTimeMillis();
        System.out.println(url);
        HttpGet httpPost=new HttpGet(url);
        httpPost.setHeader("Host", "login.wx.qq.com");
        httpPost.setHeader("Pragma", "no-cache");
        httpPost.setHeader("Referer", "https://wx.qq.com/");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        httpPost.setHeader("Connection", "keep-alive");
        int timeout = 200000;
        // System.out.println("Executing request " +
        // httpget.getRequestLine());
        RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).build();
        httpPost.setConfig(config);
        String html="";
        try {
            HttpResponse response = https.execute(httpPost);
            HttpEntity entitySort = response.getEntity();
            html=EntityUtils.toString(entitySort, "utf-8");
            System.out.println(html);
            if(html.indexOf("408")!=-1)
            {
                return 1;
            }
            if(html.indexOf("400")!=-1)
            {

                return 2;
            }
            if(html.indexOf("200")!=-1)
            {
                int start=html.indexOf("https");
                html=html.substring(start).replace("\";", "");
                this.redirect_uri=html;
                System.out.println(this.redirect_uri);
                return 3;
            }
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void login(){
        HttpGet httpPost=new HttpGet(this.redirect_uri);
        httpPost.setHeader("Host", "wx.qq.com");
        httpPost.setHeader("Pragma", "no-cache");
        httpPost.setHeader("Referer", "https://wx.qq.com/?&lang=zh_CN");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        httpPost.setHeader("Connection", "keep-alive");
        String html="";
        try {
            HttpResponse response = https.execute(httpPost);
            HttpEntity entitySort = response.getEntity();
            html=EntityUtils.toString(entitySort, "utf-8");
            System.out.println(html);

        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}*/
