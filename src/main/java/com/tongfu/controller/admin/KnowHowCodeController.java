package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Admin;
import com.tongfu.entity.KnowHow;
import com.tongfu.entity.KnowhowCode;
import com.tongfu.service.AdminService;
import com.tongfu.service.KnowHowService;
import com.tongfu.service.KnowhowCodeService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ExcelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin/knowHowCode")
public class KnowHowCodeController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private KnowHowService knowHowService;

	@Autowired
	private KnowhowCodeService knowhowCodeService;

	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   Long knowHowId,String code,Integer isSell) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("knowHowId",knowHowId);
		map.put("code",code);
		map.put("isSell",isSell);
		KnowHow knowHow = knowHowService.selectByPrimaryKey(knowHowId);
		Page<KnowhowCode> page  = PageHelper.startPage(pageNum,pageSize);
		List<KnowhowCode> all = knowhowCodeService.getAll(map);
		PageInfo<KnowhowCode> pageInfo = new PageInfo<KnowhowCode>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);

		model.addAttribute("page",pageInfo);
		model.addAttribute("knowHowId",knowHowId);
		model.addAttribute("code",code);
		model.addAttribute("type",knowHow.getType());
		model.addAttribute("isSell",isSell);


		return "admin/knowHowCode/list";
	}





	@RequestMapping("/delete")
	public String delete(Long id,Long knowHowId) {
		Integer conunt = knowhowCodeService.deleteByPrimaryKey(id);
		logger.info("删除KnowHow结果:" + conunt);
		return "redirect:list?knowHowId="+knowHowId;
	}

	/**
	 * 生成KnowHow Code
	 * @param knowHowId
	 * @param code
	 * @param isSell
	 * @param model
	 * @return
	 */
	@RequestMapping("/dowlondKnowHowCode")
	public ModelAndView downloadCharge(Long knowHowId,String code,Integer isSell, ModelMap model) {

		Admin admin = adminService.getCurrent();

		Map<String,Object> map = new HashMap<>(	);
		map.put("knowHowId",knowHowId);
		map.put("code",code);
		map.put("isSell",isSell);
		KnowHow knowHow = knowHowService.selectByPrimaryKey(knowHowId);
		List<Map<String,Object>> data = knowhowCodeService.getAllMap(map);

		String filename = "knowHow卡" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"卡号","面值","有效期起始日期","有效期截止日期","是否出售"};//title
		String[] contents = new String []{"code","face_value","start_date","end_date","is_sell"};//content

		String[] memos = new String [4];//memo
		memos[0] = "记录数" + ": " + data.size();
		memos[1] = "操作员" + ": " + admin.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data, memos), model);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data, memos), model);


	}


}
