package com.tongfu.controller.web;

import com.tongfu.entity.Admin;
import com.tongfu.entity.Area;
import com.tongfu.entity.Company;
import com.tongfu.service.AdminService;
import com.tongfu.service.AreaService;
import com.tongfu.service.CompanyService;
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

@Controller("webcompany")
@RequestMapping("/web/company")
public class CompanyController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AreaService areaService;

	@Value("${company-logo}")
	private String company_logo;

	@Value("${qr-code}")
	private String qr_code;

	@Value("${company-background-img}")
	private String company_background_img;

	@Value("${company-license}")
	private String company_license;



	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String add(Model model) {
		Admin admin = adminService.getCurrent();
		Company company = companyService.selectByPrimaryKey(admin.getCompanyId());
		Area area = areaService.selectByPrimaryKey(company.getAreaId());
		model.addAttribute("company",company);
		model.addAttribute("area",area);
		return "web/company/edit";
	}


	/**
	 * 编辑
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

		company.setStatus(1l);
		Integer updateCompany = companyService.updateByPrimaryKeySelective(company);
		logger.info("修改结果:"+updateCompany);
		return "redirect:edit";
	}



}
