package com.tongfu.interceptor;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;


@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER= LoggerFactory.getLogger(AdminInterceptor.class.getName());


    /** "重定向URL"参数名称 */
    private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";

    /** 默认登录URL */
    private static final String DEFAULT_LOGIN_URL = "/";

    /** 登录URL */
    private String loginUrl = DEFAULT_LOGIN_URL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("=====================");
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            return true;
        } else {
            String requestType = request.getHeader("X-Requested-With");
            if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
                response.addHeader("loginStatus", "accessDenied");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            } else {
                if (request.getMethod().equalsIgnoreCase("GET")) {
                    System.out.println(request.getRequestURI()+"==="+request.getQueryString()+"==="+request.getRequestURI());
                    String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
                    String adminRedirectUrl= redirectUrl.replace("/tongfu","");
                    subject.getSession().setAttribute("adminRedirectUrl",adminRedirectUrl);
                    String redirect_url = request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, "UTF-8");

                    response.sendRedirect(redirect_url);
                } else {
                    System.out.println(request.getContextPath() + loginUrl);
                    response.sendRedirect(request.getContextPath() + loginUrl);
                }
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        LOGGER.info("2、postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        LOGGER.info("2、afterCompletion");
    }
}