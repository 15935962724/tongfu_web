package com.tongfu.controller.admin;

import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.SettingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/setting")
public class SettingController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	/**
	 * setting 设置
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model) {
		Map<String,Object> map = new HashMap<>(	);
		Setting setting = SettingUtils.get();
		model.addAttribute("setting",setting);
		return "admin/setting/edit";
	}


	@PostMapping("/update")
	public String update(Model model, Setting setting) {
		SettingUtils.set(setting);
		return "redirect:edit";
	}


}
