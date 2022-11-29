package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/admin/knowHow")
public class KnowHowController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private KnowHowService knowHowService;

	@Autowired
	private KnowhowCodeService knowhowCodeService;

	/**
	 * 广告列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String title,String startDate,String endDate,String createDate,String createEndDate,Integer type) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("title",title);

		if (startDate!=null&&!startDate.equals("")){
			Date start_Date = DateUtil.getStringtoDate( startDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("startDate",start_Date);
		}
		if (endDate!=null&&!endDate.equals("")){
			Date end_Date = DateUtil.getStringtoDate( endDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("endDate",end_Date);
		}

		if (createDate!=null&&!createDate.equals("")){
			Date create_Date = DateUtil.getStringtoDate( createDate+" 00:00:00","MM/dd/yyyy HH:mm:ss");
			map.put("create_Date",create_Date);
		}
		if (createEndDate!=null&&!createEndDate.equals("")){
			Date create_End_Date = DateUtil.getStringtoDate( createEndDate+" 23:59:59","MM/dd/yyyy HH:mm:ss");
			map.put("create_End_Date",create_End_Date);
		}

		map.put("type",type);

		Page<KnowHow> page  = PageHelper.startPage(pageNum,pageSize);
		List<KnowHow> all = knowHowService.getAll(map);
		PageInfo<KnowHow> pageInfo = new PageInfo<KnowHow>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);

		model.addAttribute("page",pageInfo);
		model.addAttribute("title",title);

		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("createDate",createDate);
		model.addAttribute("createEndDate",createEndDate);
		model.addAttribute("type",type);


		return "admin/knowHow/list";
	}


	/**
	 * 编辑
	 * @param knowHow
	 * @return
	 */
	@PostMapping("/update")
	public String update(KnowHow knowHow) {

		Integer conunt = knowHowService.updateByPrimaryKeySelective(knowHow);
		logger.info("编辑Knowhow结果:"+conunt);
		return "redirect:list";
	}

	@RequestMapping("/add")
	public String add(Model model) {
		return "admin/knowHow/add";
	}


	/**
	 * 发布KnowHow
	 * @param knowHow
	 * @return
	 */
	@PostMapping("/save")
	public String save(KnowHow knowHow) {
		knowHow.setModifyDate(new Date());
		knowHow.setCreateDate(new Date());


		Integer conunt = knowHowService.insertSelective(knowHow);
		logger.info("发布KnowHow结果:" + conunt);
		return "redirect:list";
	}


	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model, Long id) {
		KnowHow knowHow = knowHowService.selectByPrimaryKey(id);
		model.addAttribute("knowHow", knowHow);
		return "admin/knowHow/edit";
	}


	@RequestMapping("/delete")
	public String delete(Long id) {
		Integer conunt = knowHowService.deleteByPrimaryKey(id);
		logger.info("删除KnowHow结果:" + conunt);
		return "redirect:list";
	}

	/**
	 * 生成KnowHow Code
	 * @param id
	 * @param count
	 * @return
	 */


	@PostMapping("/knowHowCode")
	public String knowHowCode(Long id,Integer count) {

		KnowHow knowHow = knowHowService.selectByPrimaryKey(id);
		List<Map<String,Object>> data = new ArrayList<>();
		for (int i =0;i<count;i++){
			Map<String,Object> map = new HashMap<>();
			String code = String.valueOf(UUID.randomUUID()).toUpperCase();
			map.put("title",knowHow.getTitle());
			map.put("code",code);
			map.put("knowHowId",id);
			map.put("startDate",knowHow.getStartDate());
			map.put("endDate",knowHow.getEndDate());
			map.put("isSell",false);
			map.put("faceValue",knowHow.getFaceValue());
			map.put("price",knowHow.getPrice());
			map.put("type",knowHow.getType());
			data.add(map);
		}

		Map<String,Object> map = new HashMap<>();
		map.put("codes",data);
		Integer counts = knowhowCodeService.insertMap(map);
		System.out.println(counts);
		return "redirect:list";
	}


}
