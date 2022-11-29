package com.tongfu.interceptor;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;


public class WebInterceptor implements HandlerInterceptor {



    /** "重定向URL"参数名称 */
    private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";



    /** 默认登录URL */
    private static final String DEFAULT_LOGIN_URL = "/webindex";

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
                    String webRedirectUrl= redirectUrl.replace("/tongfu","");
                    subject.getSession().setAttribute("webRedirectUrl",webRedirectUrl);
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

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}