package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.AreaService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.PaymentMethodService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.FileUtils;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/paymentMethod")
public class PaymentMethodController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CompanyService companyService;

	@Autowired
	PaymentMethodService paymentMethodService;

	//支付方式图标路径
	@Value("${paymentMethod-logo-img}")
	private String uploadPaymentMethodLogoImg;


	/**
	 * 支付列表
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNum,
					   @RequestParam(defaultValue = "10")Integer pageSize,
					   String name ) {
		Map<String,Object> query_map = new HashMap<>(	);
		query_map.put("name",name);
		Page<PaymentMethod> page  = PageHelper.startPage(pageNum,pageSize);
		List<PaymentMethod> paymentMethods = (List<PaymentMethod>) paymentMethodService.getAll(query_map);
		PageInfo<PaymentMethod> pageInfo = new PageInfo<PaymentMethod>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(paymentMethods);
		model.addAttribute("page",pageInfo);
		model.addAttribute("name",name);
		return "admin/paymentMethod/list";
	}



	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		return "admin/paymentMethod/add";
	}


	/**
	 * 添加支付方式
	 * @param model
	 * @param paymentMethod
	 * @param paymentMethodLogo
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model,PaymentMethod paymentMethod,@RequestParam("paymentmethodlogo") MultipartFile paymentMethodLogo) {

		String paymentMethod_logo_img = FileUtils.upload(paymentMethodLogo, uploadPaymentMethodLogoImg);
		paymentMethod.setIcon(paymentMethod_logo_img);
		paymentMethod.setCreateDate(new Date());
		paymentMethod.setModifyDate(new Date());
		Integer count = paymentMethodService.insertSelective(paymentMethod);

		return "redirect:list";
	}



	/**
	 * 编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		PaymentMethod paymentMethod = paymentMethodService.selectByPrimaryKey(id);
		model.addAttribute("paymentMethod",paymentMethod);
		return "admin/paymentMethod/edit";
	}

	/**
	 *
	 * @param model
	 * @param file
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,PaymentMethod paymentMethod,@RequestParam("paymentmethodlogo") MultipartFile file) {

		if (file.getSize()>0){
			String paymentMethod_logo_img = FileUtils.upload(file, uploadPaymentMethodLogoImg);
			paymentMethod.setIcon(paymentMethod_logo_img);
		}
		paymentMethod.setModifyDate(new Date());
		Integer count = paymentMethodService.updateByPrimaryKeySelective(paymentMethod);
		return "redirect:list";
	}

	//支付方式是否启用
	@PostMapping("/updatePaymentMethod")
	@ResponseBody
	public Object updatePaymentMethod(@RequestBody String params) {
		System.out.println("》》》"+params);
		PaymentMethod paymentMethod = JSONObject.parseObject(params,PaymentMethod.class);
		Integer updatePaymentMothod = paymentMethodService.updateByPrimaryKeySelective(paymentMethod);
		if (updatePaymentMothod>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}


	/**
	 * 删除
	 * @param params
	 * @return
	 */
	@PostMapping("/deletePaymentMethod")
	@ResponseBody
	public Object deletePaymentMethod(@RequestBody String params) {

		System.out.println("》》》"+params);
		PaymentMethod paymentMethod = JSONObject.parseObject(params,PaymentMethod.class);
		Integer updatePaymentMothod = paymentMethodService.deleteByPrimaryKey(paymentMethod.getId());
		if (updatePaymentMothod>0){
			return  ResultUtil.success("操作成功");
		}
		return ResultUtil.error("操作失败");
	}





}
