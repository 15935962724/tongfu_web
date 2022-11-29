package com.tongfu.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 上传文件工具类
 */
@Component
public class FileUtils {
    private static SystemPropertiesUtil systemPropertiesUtil;
    @Autowired
    public void init(SystemPropertiesUtil systemPropertiesConfig) {
        FileUtils.systemPropertiesUtil = systemPropertiesConfig;
    }
    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName) {

        // 生成新的文件名
        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);

        File files = new File(path);
        if (!files.isDirectory()) {
            files.mkdirs();
        }

        //使用原文件名
        String realPath = path + "/" + fileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
        /**
         * 上传文件
         */
        public static String upload (MultipartFile file, String path){

            if (file.getSize()<=0){
                return null;
            }

            // 生成新的文件名
            //String realPath = path + "/" + FileNameUtils.getFileName(fileName);

            //		获取静态资源路径
            String staticPath= systemPropertiesUtil.getFileWebPath()+path;//ClassUtils.getDefaultClassLoader().getResource("static").getPath()+path;
            File files = new File(staticPath);
            if (!files.isDirectory()) {
                files.mkdirs();
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("上传的后缀名为：" + suffixName);

            fileName= UUIDUtil.getUUID()+suffixName;//重新命名图片，变成随机的名字
            //使用原文件名
            String realPath = staticPath  + fileName;

            File dest = new File(realPath);

            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            try {
                //保存文件
                file.transferTo(dest);
                return path+fileName;
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }

        }


    /**
     * 等比例压缩图片
     * @param file
     * @param uploadPath
     * @return
     */
    public static String thumbnail(MultipartFile file,String uploadPath ,String filename) {

         int WINDTH = 750;
         int HEIGHT = 0;

        OutputStream os = null;

        String desc = uploadPath + filename;
        try {
            os = new FileOutputStream(desc);
            Image image = ImageIO.read(file.getInputStream());
            int width = image.getWidth(null);// 获得原图的宽度
            int height = image.getHeight(null);// 获得原图的高度
            double p3 = WINDTH*1.0/width;//压缩比例
            // 计算缩略图最总的宽度和高度
            int newWidth = WINDTH;
            int newHeight = (int) (height *p3);

            BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, image.SCALE_SMOOTH), 0, 0, null);

            String imageType = file.getContentType().substring(file.getContentType().indexOf("/") + 1);
            ImageIO.write(bufferedImage, imageType, os);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return desc;
    }






    //链接url下载图片
    public static String downloadPicture(String urlList, String path,String urlPrefix) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlPrefix;
    }


    /**
     * 替换内容中的img 的src路径
     * @param htmlStr
     * @param path
     * @return
     */
    public static String replaceImgStc(String htmlStr,String path,String urlPrefix) {
        List<String> pics = new ArrayList<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
//                pics.add(m.group(1));
                String primaryUrl = String.valueOf(m.group(1));//图片原路径
                //排除Data URI scheme 直接加载页面的数据。例如data: 文本数据 data: text/plain, ------- 文本数据
                //data: text/html, -------- HTML代码 data: text/html;base64, -------- base64编码的HTML代码
                //data: text/css, ---------- CSS代码 data: text/css;base64, ---------- base64编码的CSS代码 data: text/javascript,Javascript代码
                //data: text/javascript;base64, --------- base64编码的Javascript代码
                //data: image/gif;base64, ---------------- base64编码的gif图片数据
                //data: image/png;base64, -------------- base64编码的png图片数据
                //data: image/jpeg;base64, ------------- base64编码的jpeg图片数据
                //data: image/x-icon;base64, ---------- base64编码的icon图片数据
                if (StringUtils.isNotBlank(primaryUrl) && primaryUrl.indexOf("data:") != 0) {
                    String fileName = Long.toString(System.currentTimeMillis())+".jpg";
                    String staticPath= path+ fileName;
                    String newUrl = downloadPicture(primaryUrl,staticPath,urlPrefix+fileName);//上传至本地服务器得到的新路径
                    htmlStr = htmlStr.replace(primaryUrl, newUrl);
                }
            }
        }
        return htmlStr;
    }

}
