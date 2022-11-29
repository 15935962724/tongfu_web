package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.Member;
import com.tongfu.service.AdminService;
import com.tongfu.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/member")
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${map-key}")
	private String mapKey;

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberService memberService;

	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}

		if (adminService.selectByUserName(username)==null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 会员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String username,String name,String phone,String email,Integer type) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("username",username);
		map.put("name",name);
		map.put("phone",phone);
		map.put("email",email);
		map.put("type",type);
		Page<Member> page  = PageHelper.startPage(pageNum,pageSize);
		List<Member> all = (List<Member>) memberService.getAll(map);
		PageInfo<Member> pageInfo = new PageInfo<Member>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("username",username);
		model.addAttribute("name",name);
		model.addAttribute("phone",phone);
		model.addAttribute("email",email);
		model.addAttribute("type",type);
		return "admin/member/list";
	}


	/**
	 * 添加管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
//		Collection<Role> roles = roleService.getAll(null);
//		model.addAttribute("roles",roles);
		return "admin/member/add";
	}


	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Member member = memberService.selectByPrimaryKey(id);
		model.addAttribute("member",member);
		return "admin/member/edit";
	}




//	@PostMapping("/save")
//	public String save(Model model,Member member) {
//		member.setModifyDate(new Date());
//		member.setCreateDate(new Date());
//		member.setIsEnabled(true);
//		member.setIsLocked(false);
//		member.setIsDeleted(false);
//		member.setAmount(new BigDecimal(0));
//		member.setPoint(0l);
//		member.setBalance(new BigDecimal(0));
//
//		String encodedPassword = ShiroConfig.shiroEncryption("111111",member.getUsername());
//		System.out.println(member.getUsername()+"的密码是："+encodedPassword);
//		member.setPassword(encodedPassword);
//		member.setLoginFailureCount(0);
//		member.setPhone(member.getMobile());
//		String data= MapUtil.getLatLng(member.getRegisterIp(),mapKey);
//		JSONObject jsonObject=JSONObject.parseObject(data);
//		String result = jsonObject.getString("result");
//		JSONObject resultObject=JSONObject.parseObject(result);
//		String location = resultObject.getString("location");
//		JSONObject locationObject=JSONObject.parseObject(location);
//		String lat =locationObject.getString("lat");//纬度
//		String lng =locationObject.getString("lng");//经度
//		member.setIplat(lat);
//		member.setIplng(lng);
//		String adInfo = resultObject.getString("ad_info");
//		JSONObject adInfoObject=JSONObject.parseObject(adInfo);
//		String nation=adInfoObject.getString("nation");
//		String province=adInfoObject.getString("province");
//		String city=adInfoObject.getString("city");
//		String district=adInfoObject.getString("district");
//		String adcode=adInfoObject.getString("adcode");
//		member.setNation(nation);
//		member.setProvince(province);
//		member.setCity(city);
//		member.setDistrict(district);
//		member.setAdcode(adcode);
//		member.setMemberRank(1l);
//
//		Integer conunt = memberService.insertSelective(member);
//		logger.info("添加结果:"+conunt);
//		return "redirect:list";
//	}


	/**
	 * 编辑
	 * @param model
	 * @param id
	 * @param openId
	 * @param qqId
	 * @param wbId
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model,Long id,String openId,String qqId,String wbId,String password) {
		Member member = memberService.selectByPrimaryKey(id);
		member.setOpenId(openId);
		member.setQqId(qqId);
		member.setWbId(wbId);
		if (password!=null&&!password.equals("")){
			String encodedPassword = ShiroConfig.shiroEncryption(password,member.getUsername());
			System.out.println(member.getUsername()+"的密码是："+encodedPassword);
			member.setPassword(encodedPassword);
		}
		Integer conunt = memberService.updateByPrimaryKeySelective(member);
		return "redirect:list";
	}






	/**
	 * 锁定or解除锁定
	 * @param id
	 * @param isLocked
	 * @return
	 */
	@RequestMapping(value = "/relieve", method = RequestMethod.GET)
	@ResponseBody
	public boolean relieve(Long id ,Boolean isLocked) {
		Member member = new Member();
		member.setId(id);
		member.setIsLocked(isLocked);
		Integer updateMember = memberService.updateByPrimaryKeySelective(member);
		return updateMember==1;
	}






}
