package com.tongfu.config;

import com.tongfu.component.MyLocaleResolver;
import com.tongfu.interceptor.AdminInterceptor;
import com.tongfu.interceptor.WebInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 使用WebMvcConfigurerAdapter 可以来扩展springmvc的功能
 * @Configuration ：指明当前类是一个配置类，就是来代替之前的spring配置
 * 既保留了所有的自动配置，也能用我们扩展的配置
 * @author Administrator
 *
 */
//@EnableWebMvc springboot全面接管springmvc 不推荐全面接管


@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		// TODO Auto-generated method stub
////		浏览器发送user请求也来到index页面
//		registry.addViewController("users").setViewName("/user/list");
//	}

//	注册拦截器  暂不配置
	@Override
	 public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
		 System.out.println(">>>>>>>>>>>>>>>开始配置拦截器");
		 registry.addInterceptor(new WebInterceptor()).addPathPatterns("/web/**");
		 super.addInterceptors(registry);
	}

//	@Bean
//	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
//		// TODO Auto-generated method stub
//		 WebMvcConfigurerAdapter webMvcConfigurerAdapter = new WebMvcConfigurerAdapter() {
//
//			 //	注册html跳转解析器解析到页面
//			@Override
//			public void addViewControllers(ViewControllerRegistry registry) {
//				// TODO Auto-generated method stub
//				registry.addViewController("/").setViewName("index");
//				registry.addViewController("/index.html").setViewName("index");
//				registry.addViewController("/main.html").setViewName("main");
//			}
//
////			注册拦截器  暂不配置
//			 @Override
//			 public void addInterceptors(InterceptorRegistry registry) {
//				 registry.addInterceptor(new AdminInterceptor()).addPathPatterns("admin/*");
//				 registry.addInterceptor(new WebInterceptor()).addPathPatterns("/web/*");
//				 super.addInterceptors(registry);
////				 静态资源  *.css ,*.js ,*.img
////						 springboot已经做好了静态资源映射
////			 	registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")// 拦截多层请求
////				.excludePathPatterns("/index.html","/","/login","/static/images/**","/static/brand/**","/static/css/**","/static/fonts/**","/static/img/**","/static/js/**");
//			}
//
//		 };
//		 return webMvcConfigurerAdapter;
//	}

//	@Bean
//	public LocaleResolver localeResolver(){
//		return new MyLocaleResolver();
//	}
}