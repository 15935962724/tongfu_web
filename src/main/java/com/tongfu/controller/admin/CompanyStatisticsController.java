package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyBillService;
import com.tongfu.service.MemberService;
import com.tongfu.util.DateUtil;
import com.tongfu.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/companyStatistics")
public class CompanyStatisticsController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${map-key}")
	private String mapKey;

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberService memberService;

	@Autowired
    private CompanyBillService companyBillService;

	@RequestMapping("/statistics")
	public String list(Model model) {
//		List<Map> yearRegister = memberService.getYearRegister(null);
		List<Map> yearRegister = companyBillService.getYearRegister(null);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < yearRegister.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			if (i==0){
				jsonObject.put("name",yearRegister.get(i).get("year"));
				jsonObject.put("type","column");
				jsonObject.put("data",companyBillService.thatYearRegister(yearRegister.get(i)));
			}else {
				jsonObject.put("name",yearRegister.get(i).get("year"));
				jsonObject.put("type",i%2==0?"line":"area");
				jsonObject.put("data",companyBillService.thatYearRegister(yearRegister.get(i)));
			}
			jsonArray.add(jsonObject);

		}
		System.out.println(jsonArray.toString());

		model.addAttribute("mapList",yearRegister);
		model.addAttribute("series",jsonArray);

////		{name: "Product A", data: [44, 55, 41, 67, 22, 43, 21, 49]},
////		{name: "Product B",data: [13, 23, 20, 8, 13, 27, 33, 12]},
////		{name: "Product C", data: [11, 17, 15, 15, 21, 14, 15, 13]}
//		List<Map> systems = memberService.getSystems(null);
//		JSONArray systemJsonArray = new JSONArray();
//		for (Map map : systems) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("name",map.get("system"));
//			jsonObject.put("data",memberService.getSystemRegister(map));
//			systemJsonArray.add(jsonObject);
//		}
//		model.addAttribute("systemJsonArray",systemJsonArray);
//		model.addAttribute("systems",systems);
//
//		List<Map> provinces = memberService.getProvince(null);
//		JSONArray provincesJsonArray = new JSONArray();
//		for (Map map : provinces) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("name",map.get("province"));
//			jsonObject.put("data",memberService.getProvinceRegister(map));
//			provincesJsonArray.add(jsonObject);
//		}
//		model.addAttribute("provincesJsonArray",provincesJsonArray);
//		model.addAttribute("provinces",provinces);
//
//		List<Map> sources = memberService.getSource(null);
//		JSONArray sourcesJsonArray = new JSONArray();
//		for (Map map : sources) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("name",map.get("source"));
//			jsonObject.put("data",memberService.getSourceRegister(map));
//			sourcesJsonArray.add(jsonObject);
//		}
//		model.addAttribute("sourcesJsonArray",sourcesJsonArray);
//		model.addAttribute("sources",sources);

		return "admin/companyStatistics/statistics";
	}

}
