package com.tongfu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Member;
import com.tongfu.entity.Order;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.MemberService;
import com.tongfu.util.HttpUtil;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Receiver;
import java.io.IOException;
import java.util.*;

@Controller("adminlogin")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;


	@PostMapping("/login")
//	@RequestMapping("/login")
//	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login() throws IOException {

		System.out.println("aaa");
		try {
			String params = null;
			JSONObject jsonObject = JSON.parseObject(params);

			String code = jsonObject.getString("code");

			if (!code.equals("1234")){
				return ResultUtil.error("验证码输入有误");
			}

			String username = jsonObject.getString("username");
			String password = jsonObject.getString("password");

			Admin admin = adminService.selectByUserName(username);
			if (admin==null){
				return ResultUtil.error("该用户不存在");
			}


			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
//			String mwpassword = username.equals("admin")?username:"111111";
//			String encodedPassword = ShiroConfig.shiroEncryption(mwpassword,username);
//			System.out.println("密码："+encodedPassword);
			//进行验证，这里可以捕获异常，然后返回对应信息
			subject.login(usernamePasswordToken);
			UUID uuid = UUID.randomUUID();
//			// 把用户登录信息存入缓存 key值为 TOKEN_{用户标识}
//			ehcacheUtil.put(EhCacheConstants.TOKEN_PREFIX + uuid.toString(), token);
			subject.getSession().setAttribute("loginUser",username);

			logger.info("登陆成功");
//			Admin admin = new Admin();
			admin.setUsername(username);
			admin.setLoginDate(new Date());
			adminService.updateByPrimaryKeySelective(admin);
			String redirect_url = (String) subject.getSession().getAttribute("adminRedirectUrl");
			if (redirect_url!=null&&!redirect_url.equals("")){
				return "redirect:"+	redirect_url;
			}
			return ResultUtil.success();
		} catch (IncorrectCredentialsException e) {
			return ResultUtil.error("密码错误");
		} catch (LockedAccountException e) {

			logger.info("该用户已被冻结");
			return ResultUtil.error("该用户已被冻结");
		} catch (AuthenticationException e) {
			logger.info("该用户不存在");
			return ResultUtil.error("该用户不存在");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("未知错误");
			return ResultUtil.error("未知错误请联系管理员！");
		}


	}


//		@RequestMapping( value = "/login",method = RequestMethod.POST)
	@PostMapping(value = "/adminlogin")
    public String list(@RequestParam("username")String username,
					   @RequestParam("password") String password,
					   Map<String,Object> map, HttpSession session) {


		try {
			Admin admin = adminService.selectByUserName(username);
			if (admin==null){
				map.put("msg"	,"该用户不存在");
				return  "admin/index";
			}
			if (admin.getCompanyId()!=null){
				map.put("msg"	,"该用户不存在");
				return  "admin/index";
			}

			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
//			String mwpassword = username.equals("admin")?username:"111111";
//			String encodedPassword = ShiroConfig.shiroEncryption(mwpassword,username);
//			System.out.println("密码："+encodedPassword);
			//进行验证，这里可以捕获异常，然后返回对应信息
			subject.login(usernamePasswordToken);
			UUID uuid = UUID.randomUUID();
//			// 把用户登录信息存入缓存 key值为 TOKEN_{用户标识}
//			ehcacheUtil.put(EhCacheConstants.TOKEN_PREFIX + uuid.toString(), token);
			subject.getSession().setAttribute("loginUser",username);

			logger.info("登陆成功");
//			Admin admin = new Admin();
			admin.setUsername(username);
			admin.setLoginDate(new Date());
			subject.getSession().setAttribute("loginDate",new Date());
//			Company company = companyService.selectByAdminId(adminService.getCurrent().getId());
//			subject.getSession().setAttribute("company",company);
//			subject.getSession().setAttribute("companyId",company==null?0:company.getId());
			adminService.updateByPrimaryKeySelective(admin);
			String redirect_url = (String) subject.getSession().getAttribute("adminRedirectUrl");
			if (redirect_url!=null&&!redirect_url.equals("")){
				return "redirect:"+	redirect_url;
			}
			return "redirect:/admin/main"	;
		} catch (IncorrectCredentialsException e) {
			logger.info("密码错误");
			map.put("msg"	,"密码错误");
		} catch (LockedAccountException e) {
			map.put("msg"	,"该用户已被冻结");
			logger.info("该用户已被冻结");
		} catch (AuthenticationException e) {
			logger.info("该用户不存在");
			map.put("msg"	,"该用户不存在");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("登录错误");
			map.put("msg"	,"登录错误");
		}
		return "admin/index";
    }


	/**
	 * 退出登陆
	 * @return
	 */
	@RequestMapping(value = "/lognout")
	public String lognout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "admin/index";
	}

	/**
	 * 管理员登录页
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index(Model model) {
		logger.info("管理员登录页");
		return "admin/index";
	}

	@RequestMapping("/adminLogin")
	public String adminLogin(Model model) {
		logger.info("admin管理员登录页");
		return "admin/index";
	}


	@RequestMapping(value = "/company")
	public void company(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Company company = companyService.selectByPrimaryKey(id);
		Map<String,Object> query_map = new HashMap<>();
		query_map.put("companyId",id);
		query_map.put("type",1);
		Admin admin = adminService.getCompanyAdmin(query_map);

		String url= request.getContextPath()+"/weblogin";
		HttpUtil http=new HttpUtil(response);
		http.setParameter("username",admin.getUsername());//将参数封装到这个里面,以键值对的形式存在
		http.setParameter("password","111111");
		http.sendByPost(url);//重定向的地址

//
//		String data = HttpUtil.post("",parment);
//		return "redirect:/weblogin?username=\"+admin.getUsername()+\"&password=111111"	;
	}


}
