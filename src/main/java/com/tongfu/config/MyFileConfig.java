package com.tongfu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author wuyongao
 * @Description TODO
 * @Date 2022/08/12/18:41
 * @Version 1.0
 */
@Configuration
public class MyFileConfig extends WebMvcConfigurerAdapter {
    /**
     * 附件上传路径
     */
    @Value(value = "${file-web-path}")
    private String fileWebPath;
    /**
     * 上传的图片在F盘下的file目录下，访问路径如：http://localhost:8080/file/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
     *  其中file表示访问的前缀。"file:F:/file/"是文件真实的存储路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //广告图
        System.out.println(fileWebPath);
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+fileWebPath+"/upload/");
        //
        registry.addResourceHandler("/categoryUploadImg/**").addResourceLocations("file:"+fileWebPath+"/categoryUploadImg/");
        //产品logo
        registry.addResourceHandler("/productLogoImg/**").addResourceLocations("file:"+fileWebPath+"/productLogoImg/");
        //产品图片
        registry.addResourceHandler("/productImg/**").addResourceLocations("file:"+fileWebPath+"/productImg/");
        registry.addResourceHandler("/productPackage/**").addResourceLocations("file:"+fileWebPath+"/productPackage/");
        registry.addResourceHandler("/ontrialPackage/**").addResourceLocations("file:"+fileWebPath+"/ontrialPackage/");
        registry.addResourceHandler("/productVideo/**").addResourceLocations("file:"+fileWebPath+"/productVideo/");
        registry.addResourceHandler("/companyLogo/**").addResourceLocations("file:"+fileWebPath+"/companyLogo/");
        registry.addResourceHandler("/companyBackgroundImg/**").addResourceLocations("file:"+fileWebPath+"/companyBackgroundImg/");
        registry.addResourceHandler("/companyLicense/**").addResourceLocations("file:"+fileWebPath+"/companyLicense/");
        registry.addResourceHandler("/journalismLogo/**").addResourceLocations("file:"+fileWebPath+"/journalismLogo/");
        registry.addResourceHandler("/guanggaoImage/**").addResourceLocations("file:"+fileWebPath+"/guanggaoImage/");
        registry.addResourceHandler("/mediaLogo/**").addResourceLocations("file:"+fileWebPath+"/mediaLogo/");
        registry.addResourceHandler("/paymentMethodLogo/**").addResourceLocations("file:"+fileWebPath+"/paymentMethodLogo/");
        registry.addResourceHandler("/workshopLogo/**").addResourceLocations("file:"+fileWebPath+"/workshopLogo/");
        registry.addResourceHandler("/articleLogo/**").addResourceLocations("file:"+fileWebPath+"/articleLogo/");
        registry.addResourceHandler("/exhibitionLogo/**").addResourceLocations("file:"+fileWebPath+"/exhibitionLogo/");
        registry.addResourceHandler("/learningLogo/**").addResourceLocations("file:"+fileWebPath+"/learningLogo/");
        registry.addResourceHandler("/knowhowArticleLogo/**").addResourceLocations("file:"+fileWebPath+"/knowhowArticleLogo/");
        registry.addResourceHandler("/aboutusLogo/**").addResourceLocations("file:"+fileWebPath+"/aboutusLogo/");
        registry.addResourceHandler("/qrCode/**").addResourceLocations("file:"+fileWebPath+"/qrCode/");
        /**
         * 多媒体编辑器附件，图片上传。
         */
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+fileWebPath+"/file/");
    }

}
