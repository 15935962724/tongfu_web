package com.tongfu.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/admin/company")
public class CompanyController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductService productService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private JournalismService  journalismService;

	@Autowired
	private LearningService learningService;

	@Autowired
	private ExhibitionService exhibitionService;

	@Autowired
	private AreaService areaService;

	@Value("${company-role-authority}")
	private String company_role_authority;

	@Value("${company-logo}")
	private String company_logo;

	@Value("${qr-code}")
	private String qr_code;

	@Value("${company-background-img}")
	private String company_background_img;

	@Value("${company-license}")
	private String company_license;


	/**
	 * 供应商公司
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize,
					   String cname,String adminName,String adminUserName,String adminEmail,Long status,String mobile) {
		Map<String,Object> map = new HashMap<>(	);
		map.put("cname",cname);
		map.put("adminName",adminName);
		map.put("adminEmail",adminEmail);
		map.put("status",status);
		map.put("mobile",mobile);
		map.put("adminUserName",adminUserName);
		Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> all = (List<Map<String,Object>>)companyService.getAll(map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPages(page.getPages());
		pageInfo.setList(all);
		model.addAttribute("page",pageInfo);
		model.addAttribute("cname",cname);
		model.addAttribute("adminName",adminName);
		model.addAttribute("adminEmail",adminEmail);
		model.addAttribute("adminUserName",adminUserName);
		model.addAttribute("status",status);
		model.addAttribute("mobile",mobile);
		return "admin/company/list";
	}

	/**
	 * 审核
	 * @param model
	 * @param company
	 * @return
	 */
	@PostMapping("/updateCompanyStatus")
	public String updateStatus(Model model, Company company
	){
		System.out.println(">>>>>>");
		if (company.getStatus()==2){
			Scanner sc = new Scanner(System.in);
			Calendar curr = Calendar.getInstance();
			curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
			Date endDate=curr.getTime();
			company.setEndDate(endDate);
		}
		companyService.updateByPrimaryKeySelective(company);
		return "redirect:list";
	}

	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Long id) {
		Admin admin = adminService.getCurrent();
		Company company = companyService.selectByPrimaryKey(id);
		Area area = areaService.selectByPrimaryKey(company.getAreaId());
		model.addAttribute("company",company);
		model.addAttribute("area",area);
		return "admin/company/edit";
	}


	/**
	 * 添加供应商页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		return "admin/company/add";
	}


	/**
	 * 添加供应商
	 * @param model
	 * @param company
	 * @param adminName
	 * @param username
	 * @return
	 */
	@PostMapping("/save")
	public String save(Model model,Company company,String adminName,String username) {
		String []companyRoleAuthority = company_role_authority.split(",");
		System.out.println(companyRoleAuthority);
		Map<String,Object> query_map =  new HashMap<>();
		query_map.put("company",company);
		query_map.put("adminName",adminName);
		query_map.put("username",username);
		companyService.insertSelective(query_map);
		return  "redirect:list";
	}


	/**
	 * 编辑供应商信息
	 * @param model
	 * @param company
	 * @param companyLogoFile
	 * @param companyBackgroundImgFile
	 * @param companyLicenseFile
	 * @param qrCodeFile
	 * @return
	 */
	@PostMapping("/update")
	public String update(Model model, Company company,
						 @RequestParam("companyLogo") MultipartFile companyLogoFile,
						 @RequestParam("companybackgroundimg") MultipartFile companyBackgroundImgFile,
						 @RequestParam("companylicense") MultipartFile companyLicenseFile,
						 @RequestParam("qrCodeLogo") MultipartFile qrCodeFile
	) {

		String companyLogo = FileUtils.upload(companyLogoFile,company_logo);
		System.out.println("供应商公司logo:"+companyLogo);
		if (companyLogo!=null){
			company.setLogo(companyLogo);
		}
		String companyBackgroundImg = FileUtils.upload(companyBackgroundImgFile,company_background_img);
		if (companyBackgroundImg!=null){//背景图
			company.setBackgroundimg(companyBackgroundImg);
		}

		String companyLicense = FileUtils.upload(companyLicenseFile,company_license);
		if (companyLicense!=null){//营业执照
			company.setLicense(companyLicense);
		}

		String qrCode = FileUtils.upload(qrCodeFile,qr_code);
		if (qrCode!=null){//公众号二维码
			company.setQrCode(qrCode);
		}
		company.setStatus(2l);
		Integer updateCompany = companyService.updateByPrimaryKeySelective(company);
		logger.info("修改结果:"+updateCompany);
		return  "redirect:list";
	}


	/**
         * 查看
         * @param model
         * @param id
         * @return
         */
	@RequestMapping("/view")
	public String view(Model model ,Long id) {
		Admin admin = adminService.getCurrent();
		Company company = companyService.selectByPrimaryKey(id);
		model.addAttribute("company",company);
		Map query_map = new HashMap();
		query_map.put("companyid",company.getId());
		query_map.put("companyId",company.getId());
		List<Product> products = productService.getAll(query_map);
		List<Journalism> journalisms = journalismService.getAll(query_map);//新闻动态
		List<Learning> learnings = learningService.getAll(query_map);//学术活动
		List<Media> medias = mediaService.getAll(query_map);//媒体报道
		List<Exhibition> exhibitions = exhibitionService.getAll(query_map);//会议展览
		model.addAttribute("company",company);
		model.addAttribute("products",products);
		model.addAttribute("journalisms",journalisms);
		model.addAttribute("learnings",learnings);
		model.addAttribute("medias",medias);
		model.addAttribute("exhibitions",exhibitions);
		return "admin/company/view";
	}



}
