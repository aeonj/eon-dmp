package eon.hg.fap.core.body;

public enum ResultCode {

	SUCCESS(0, "成功"),
	FAILED(500, "失败"),
	USER_LOGIN_ERROR(500201, "登录失败，用户名或密码出错"),
	IDENTITY_FAIL(500401, "身份验证失败"),
	AUTHENTICATION_FAIL(500403, "未被授权的请求资源"),
	REQUEST_TIME_OUT(500408,"由于长时间未操作,空闲会话已超时"),
	CUSTOMIZE_FAIL(500999, "自定义错误");

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
