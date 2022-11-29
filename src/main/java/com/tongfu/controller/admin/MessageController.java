package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Learning;
import com.tongfu.entity.Message;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller("adminmessage")
@RequestMapping("/admin/message")
public class MessageController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private LearningService learningService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Value("${learning-logo}")
	private String learningLogo;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "9")Integer pageSize,
					   Long productCategory,Boolean isHandle, Long companyId,String name) {

		Map<String,Object> query_map = new HashMap<>();
		query_map.put("isHandle",isHandle);
		query_map.put("isForwardReceiver",false);
		query_map.put("productCategory",productCategory);
		query_map.put("companyId",companyId);
		query_map.put("name",name);
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> all = (List<Map<String,Object>>) messageService.getAll(query_map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		query_map.put("type",1);
		Collection<ProductCategory> productCategories = productCategoryService.getAll(query_map);
		List<Map<String,Object>> companys = companyService.getAll(null);//供应商
		model.addAttribute("productCategories",productCategories);//商品分类
		model.addAttribute("companys",companys);
		model.addAttribute("name",name);
		model.addAttribute("companyId",companyId);
		model.addAttribute("productCategory",productCategory);

		model.addAttribute("page",pageInfo);
		return "admin/message/list";
	}


	@RequestMapping("/updateStatus")
	@ResponseBody
	public Object updateStatus(Long id) {
		Message message = new Message();
		message.setId(id);
		message.setIsForwardReceiver(true);
		Integer updateMessage = messageService.updateByPrimaryKeySelective(message);
		if(updateMessage>0){
			return ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}




}
