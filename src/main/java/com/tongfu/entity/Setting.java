package com.tongfu.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 系统设置
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public class Setting implements Serializable {

	private static final long serialVersionUID = -1478999889661796840L;

	/** 缓存名称 */
	public static final String CACHE_NAME = "setting";

	/** 缓存Key */
	public static final Integer CACHE_KEY = 0;

	/** 分隔符 */
	private static final String SEPARATOR = ",";

	/** 短信接口*/
	private String shortMessageUrl;

	/** 开发者主账号（ACCOUNT SID）。开发者账号唯一标识符*/
	private String accountSid;

	/** 子账户ID，具体查看用户中心 短信配置中值，验证码通知和营销不一样*/
	private String accountId;

	/** 短信接口秘钥*/
	private String authToken;


	/** 会员注册赠送积分*/
	private String memberRegisterPoint;

	/** 购买软件赠送积分*/
	private String purchaseProductPoint;

	/**学术活动首页收费类型 1按月收费 2按天收费*/
	private Long type;

	/**学术活动首页收费价格*/
	private BigDecimal price;

	public String getShortMessageUrl() {
		return shortMessageUrl;
	}

	public void setShortMessageUrl(String shortMessageUrl) {
		this.shortMessageUrl = shortMessageUrl;
	}

	public String getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMemberRegisterPoint() {
		return memberRegisterPoint;
	}

	public void setMemberRegisterPoint(String memberRegisterPoint) {
		this.memberRegisterPoint = memberRegisterPoint;
	}

	public String getPurchaseProductPoint() {
		return purchaseProductPoint;
	}

	public void setPurchaseProductPoint(String purchaseProductPoint) {
		this.purchaseProductPoint = purchaseProductPoint;
	}


	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}