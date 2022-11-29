package com.tongfu.component;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;


@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        errorAttributes.put("company","Product");
//        我们的异常处理器携带的数据
        Map<String, Object> ext = (Map<String, Object>) requestAttributes.getAttribute("ext",0);
        errorAttributes.put("ext",ext);
        return  errorAttributes;
//        return super.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    public MyErrorAttributes() {
        super();
    }
}
