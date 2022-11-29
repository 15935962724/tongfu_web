/*
package com.tongfu.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingleWechatTest {


    public static void main(String[] args) throws Exception {
        initPage();
        testLogin();

    }



    private static void initPage() throws Exception {
        SingleWechat singleWechat = new SingleWechat();
        singleWechat.initPage();
    }


    public static void testLogin(){
        System.setProperty ("jsse.enableSNIExtension", "false");
        SingleWechat l = new SingleWechat();
        l.initPage();
        String appid=l.getPng1();
        if(!"".equals(appid))
        {
            l.getPng2(appid);
        }
        for(int i=0;;i++)
        {
            int cf=l.checklogin(appid);
            if(cf==3)
            {
                System.out.println("已在手机端确认");
                break;
            }
            if(cf==2)
            {
                appid=l.getPng1();
                if(!"".equals(appid))
                {
                    l.getPng2(appid);
                }
            }
            if(cf==1)
            {
                continue;
            }
            try {
                Thread.sleep(13000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        l.login();
    }

}*/
