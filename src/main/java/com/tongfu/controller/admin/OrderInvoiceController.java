package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Area;
import com.tongfu.entity.Company;
import com.tongfu.entity.OrderInvoice;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.OrderInvoiceService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/orderInvoice")
public class OrderInvoiceController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	CompanyService companyService;

	@Autowired
	OrderInvoiceService orderInvoiceService;

	/**
	 * 供应商公司
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   Long type ,String orderSn,String startDate,String endDate ,Long isMakeInvoice) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("type",type);
		map.put("orderSn",orderSn);
		map.put("isMakeInvoice",isMakeInvoice);
		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> all = (List<Map<String,Object>>)orderInvoiceService.getOrderInvoices(map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("type",type);
		model.addAttribute("orderSn",orderSn);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("isMakeInvoice",isMakeInvoice);
		return "admin/orderInvoice/list";
	}


	/**
	 *
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping("/updateIsMakeInvoice")
	@ResponseBody
	public String updateIsMakeInvoice(@RequestBody JSONObject jsonObject) {
		OrderInvoice orderInvoice = JSON.parseObject(jsonObject.toJSONString(), OrderInvoice.class);
		int updateByPrimaryKeySelective = orderInvoiceService.updateByPrimaryKeySelective(orderInvoice);

		return updateByPrimaryKeySelective>0? ResultUtil.success():ResultUtil.error("操作失败!");
	}

	/**
	 * 打印详情
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping("/printView")
	@ResponseBody
	public Object printView(@RequestBody JSONObject jsonObject) {
		OrderInvoice orderInvoice = orderInvoiceService.selectByPrimaryKey(jsonObject.getLong("id"));
		return  orderInvoice;
	}





}
