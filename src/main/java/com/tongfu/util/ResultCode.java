package com.tongfu.util;

public enum ResultCode {
	// 成功
	SUCCESS(200, "操作成功"),
	// 失败
	FAIL(400, "操作失败"),
	// 未认证（签名错误）
	UNAUTHORIZED(401, "签名错误"),
	// 接口不存在
	NOT_FOUND(404, "接口不存在"),
	// 服务器内部错误
	INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

	UNKNOW_ERROR(-1, "未知错误"),

	/* 参数错误：10001-19999 */
	PARAM_IS_INVALID(10001, "参数无效"), PARAM_IS_BLANK(10002, "参数为空"), PARAM_TYPE_BIND_ERROR(10003,
			"参数类型错误"), PARAM_NOT_COMPLETE(10004, "参数缺失");

	private Integer code;

	private String msg;

	ResultCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}