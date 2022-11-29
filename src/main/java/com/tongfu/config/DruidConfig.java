package com.tongfu.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.tongfu.service.UserService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Configuration
public class DruidConfig {

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DataSource druid(){
		return  new DruidDataSource() ;
	}

//	配置Druid监控
//	配置一个管理后台的servlet
//	配饰一个监控filter

	@Bean
	public ServletRegistrationBean statViewServlet(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
		Map<String ,String > initParms = new HashMap<>();

		initParms.put("loginUsername","admin");
		initParms.put("loginPassword","123456");
		initParms.put("allow","");//默认就是允许所有访问
		initParms.put("deny","192.168.15.21");//不允许这个ip访问
//		initParms.put("remoteAddress","");
		bean.setInitParameters(initParms);
		return bean;
	}

//	public FilterRegistrationBean webStatFilter(){
//		FilterRegistrationBean bean = new FilterRegistrationBean();
//		bean.setFilter(new WebStatFilter());
//		Map<String ,String > initParms = new HashMap<>();
//		initParms.put("exclusions","*.js,*.css,/druid/*");//排除这些请求不拦截
//		initParms.put("","");
//
//		bean.setInitParameters(initParms);
//		initParms.put("loginUsername","admin");
//		bean.setUrlPatterns(Arrays.asList("/*"));
//
//		return bean;
//	}
	
}