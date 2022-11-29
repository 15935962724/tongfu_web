package com.tongfu.controller.web;


import com.tongfu.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller("webfile")
//@RequestMapping("/web/file")
public class FileController {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 附件上传路径
     */
    @Value(value = "${file-web-path}")
    private String fileWebPath;

    /**
     * 环境标识
     */
    @Value("${spring.profiles.active}")
    private String envType;
    /**
     *
     * @param action
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/config")
    public String config(String action ,HttpServletRequest request, HttpServletResponse response){


        String json = "";
//        response.setContentType("application/json");
//        response.setContentType("multipart/form-data");

        // 获取项目在磁盘的绝对路径
//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        try {
//            // 将josn文件读到Stirng
//            json =  IOUtils.toString(new FileInputStream(new File(path + "/static/ueditor/jsp/config.json")), Charsets.UTF_8.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  "redirect:/ueditor/jsp/config.json";

        System.out.println("action:+++++++++"+action);

//
        if(action.equals("config")) {
            System.out.println("action:"+action);
            return "redirect:/ueditor/jsp/config-"+envType+".json";
        }else if(action.equals("uploadimage")){
            return "forward:/uploadimage";
        }else if(action.equals("uploadvideo")){
            return "forward:/uploadvideo";
        }else if(action.equals("uploadFile")){
            return "forward:/uploadFile";
        }
        return "";

    }


    /**
     * 上传图片
     * @param upfile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadimage")
    @ResponseBody
    public Object uploadimage(@RequestParam("upfile") MultipartFile upfile,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rootPath = "/file/";

        String staticPath= fileWebPath+rootPath;
        File files = new File(staticPath);
        if (!files.isDirectory()) {
            files.mkdirs();
        }


        Image image = ImageIO.read(upfile.getInputStream());
        int width = image.getWidth(null);// 获得原图的宽度
        // 获取文件名
        String fileName = upfile.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        String time = Long.toString(System.currentTimeMillis());
        fileName=time+suffixName;//重新命名图片，变成随机的名字
        String realPath = "";
        try {
            if (width>750){
                realPath = FileUtils.thumbnail(upfile,staticPath,fileName);
                System.out.println("图片宽度大于750地址:"+realPath);
            }else {

                //使用原文件名
                realPath = staticPath  + fileName;
                System.out.println("原图片地址:"+realPath);
                File dest = new File(realPath);

                //判断文件父目录是否存在
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                //保存文件
                upfile.transferTo(dest);
            }


            Map<String,Object> map = new HashMap<String,Object>();
            map.put("original",fileName);
            map.put("name",fileName);
            map.put("url",rootPath+fileName);
            map.put("size",upfile.getSize());
            map.put("type",suffixName);
            map.put("state","SUCCESS");
            return map;

//            return path+fileName;
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
     * 上传视频
     * @param upfile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadvideo")
    @ResponseBody
    public Object uploadvideo(@RequestParam("upfile") MultipartFile upfile,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rootPath = "/file/voide/";
        String staticPath= fileWebPath+rootPath;
        File files = new File(staticPath);
        if (!files.isDirectory()) {
            files.mkdirs();
        }
        // 获取文件名
        String fileName = upfile.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        fileName= Long.toString(System.currentTimeMillis())+suffixName;//重新命名图片，变成随机的名字
        //使用原文件名
        String realPath = staticPath  + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            upfile.transferTo(dest);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("original",fileName);
            map.put("name",fileName);
            map.put("url",rootPath+fileName);
            map.put("size",upfile.getSize());
            map.put("type",suffixName);
            map.put("state","SUCCESS");
            return map;
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
     * 上传文件
     * @param upfile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object uploadFile(@RequestParam("upfile") MultipartFile upfile,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rootPath = "/file/files/";
        String staticPath= fileWebPath+rootPath;
        File files = new File(staticPath);
        if (!files.isDirectory()) {
            files.mkdirs();
        }
        // 获取文件名
        String fileName = upfile.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        fileName= Long.toString(System.currentTimeMillis())+suffixName;//重新命名图片，变成随机的名字
        //使用原文件名
        String realPath = staticPath  + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            upfile.transferTo(dest);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("original",fileName);
            map.put("name",fileName);
            map.put("url",rootPath+fileName);
            map.put("size",upfile.getSize());
            map.put("type",suffixName);
            map.put("state","SUCCESS");
            return map;
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



//
//    SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
//        String path = "//" + formater.format(new Date());
//        String action = request.getParameter("action");
//        String rPath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/ueditor/jsp";
//        String sPath =  rootPath + CommonConstnts.IMAGES_PATH;
//        if(action.equals("uploadimage")){
//
//            String filePath = sPath+path;
//            File file = new File(filePath);
//            if(!file.exists()){
//                file.mkdirs();
//            }
//            String imgName = multipartFile.getOriginalFilename();
//            String hz = imgName.substring(imgName.indexOf("."));
//            String uuid = UUID.randomUUID().toString();
//            String uuid1 = UUID.randomUUID().toString();
//            uuid = uuid.replace("-","");
//            uuid1 = uuid1.replace("-","");
//            String name = filePath+"//"+uuid+hz;
//            String fileName = filePath+"//"+uuid1+hz;
//            File f = new File(name);
//            File f1 = new File(fileName);
//            try {
//                multipartFile.transferTo(f);
//                InputStream inputStream = new FileInputStream(f);
//                OutputStream os = new FileOutputStream(f1);
//                ImageUtil.resizeImage(inputStream,os,500,750,hz.replace(".",""));
//                f.delete();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("original",multipartFile.getOriginalFilename());
//            map.put("name",multipartFile.getOriginalFilename());
//            map.put("url",CommonConstnts.IMAGES_PATH+path+"//"+uuid1+hz);
//            map.put("size",multipartFile.getSize());
//            map.put("type","."+hz);
//            map.put("state","SUCCESS");
//            return map;
//        }else{
//            response.setContentType("application/json");
//            try {
//                response.setCharacterEncoding("utf-8");
//                request.setCharacterEncoding( "utf-8" );
//                PrintWriter writer = response.getWriter();
////                writer.write(new ActionEnter(request,rPath).exec());
//                writer.flush();
//                writer.close();
//            }catch (Exception r){
//                r.printStackTrace();
//            }
//        }
//        return null;
//    }



}
