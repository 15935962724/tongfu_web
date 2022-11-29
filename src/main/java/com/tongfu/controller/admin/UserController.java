package com.tongfu.controller.admin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.tongfu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {


//	@Autowired
//	private UserService  userService;

//	@Autowired
//	private UsersDao usersDao;
	
	
//	@RequestMapping({"/","/index.html"})
//    public String index() {
//	        return "index";
//    }


	/**
	 * 列表页
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/list")
    public String list(Model model) {
//		Collection<User> all = usersDao.getAll();
////		放在请求域中
//		model.addAttribute("users",all);
		return "user/list";
    }
	
	@GetMapping("/list")
    public String list(Map<String,Object> map ) {
			map.put("holle","<h1>你好</h1>");
			map.put("users",Arrays.asList("张三","李四","王五"));
	        return "user/list";
    }


	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/user/add")
	public String add(Model model) {
//		Collection<User> users = usersDao.getAll();
//		model.addAttribute("users",users);
		return "user/add";
	}

	@PostMapping("/user/save")
	public String save(User user) {


		return "redircet:/user/list";
	}



}
