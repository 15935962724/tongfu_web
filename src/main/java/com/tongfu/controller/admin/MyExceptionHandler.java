package com.tongfu.controller.admin;

import com.tongfu.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {


	//浏览器返回的也是json数据不是自适应效果
//	@ResponseBody
//	@ExceptionHandler(UserNotExistException.class)
//	public Map<Object, Object>   exceptionHandler(Exception e) {
//		Map<Object, Object> error_map = new HashMap<>();
//		error_map.put("code","dd");
//		error_map.put("message",e.getMessage());
//		error_map.put("aa","dd");
//		error_map.put("aa","dd");
//		return  error_map;
//	}


	@ExceptionHandler(UserNotExistException.class)
	public String   exceptionHandler(Exception e, HttpServletRequest request) {
		Map<Object, Object> error_map = new HashMap<>();
		error_map.put("javax.servlet.error.status_code","500");
		error_map.put("code","user.notexist");
		error_map.put("message",e.getMessage());
		request.setAttribute("ext",error_map);

		return  "forward:/error";
	}


}
