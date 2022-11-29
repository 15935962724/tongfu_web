package com.tongfu.util;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    static String mapUrl = "https://apis.map.qq.com/ws/location/v1/ip";

    public static String getLatLng (String ip, String key){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String urlString = mapUrl+"?ip="+ip+"&key="+key;
        String result = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            // 腾讯地图使用GET
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            // 获取地址解析结果
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }
}
