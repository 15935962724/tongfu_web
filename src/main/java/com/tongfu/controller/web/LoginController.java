package com.tongfu.controller.web;

import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Company;
import com.tongfu.service.AdminService;
import com.tongfu.service.CompanyService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller("weblogin")
//@RequestMapping("/adminlogin")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;


		//	@RequestMapping( value = "/login",method = RequestMethod.POST)
	@PostMapping(value = "/weblogin")
    public String list(@RequestParam("username")String username,
					   @RequestParam("password") String password,
					   Map<String,Object> map, HttpSession session) {

		try {
			Admin company_admin = adminService.selectByUserName(username);
			if (company_admin==null){
				map.put("msg"	,"该用户不存在");
				return  "web/index";
			}
			if (company_admin.getCompanyId()==null){
				map.put("msg"	,"该用户不存在");
				return  "web/index";
			}
			Subject subject = SecurityUtils.getSubject();
//			String mwpassword = username.equals("admin")?username:"111111";
//			String encodedPassword = ShiroConfig.shiroEncryption(mwpassword,username);
//			System.out.println("密码："+encodedPassword);
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
			//进行验证，这里可以捕获异常，然后返回对应信息
			subject.login(usernamePasswordToken);
			UUID uuid = UUID.randomUUID();
//			// 把用户登录信息存入缓存 key值为 TOKEN_{用户标识}
//			ehcacheUtil.put(EhCacheConstants.TOKEN_PREFIX + uuid.toString(), token);
			subject.getSession().setAttribute("loginUser",username);

			logger.info("登陆成功");
			Admin admin = new Admin();
			admin.setUsername(username);
			admin.setLoginDate(new Date());
			adminService.updateByPrimaryKeySelective(admin);
			subject.getSession().setAttribute("loginDate",new Date());
			Company company = companyService.getCompany();
			subject.getSession().setAttribute("company",company);
			subject.getSession().setAttribute("companyId",company.getId());
			String redirect_url = (String) subject.getSession().getAttribute("webRedirectUrl");
			if (redirect_url!=null&&!redirect_url.equals("")){
				return "redirect:"+	redirect_url;
			}
			return "redirect:/web/main"	;
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
		return "web/index";
    }


	/**
	 * 退出登陆
	 * @return
	 */
	@RequestMapping(value = "/webLognout")
	public String lognout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "web/index";
	}



	/**
	 * 管理员登录页
	 * @param model
	 * @return
	 */
	@RequestMapping("/webindex")
	public String index(Model model) {
		logger.info("供应商登录页");
		return "web/index";
	}




}
