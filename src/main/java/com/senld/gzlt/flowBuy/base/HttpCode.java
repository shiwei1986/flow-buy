package com.senld.gzlt.flowBuy.base;

/**
 * Ajax 请求时的自定义查询状态码，主要参考Http状态码，但并不完全对应
 * 
 * @author hy
 */
public enum HttpCode {

	/** 1 请求成功 */
	OK(1),

	/** 207频繁操作 */
	MULTI_STATUS(207),

	/** 400请求参数出错 */
	BAD_REQUEST(400),

	/** 500服务器出错 */
	INTERNAL_SERVER_ERROR(500);

	private final Integer value;

	private HttpCode(Integer value) {
		this.value = value;
	}

	/** Return the integer value of this status code. */
	public Integer value() {
		return this.value;
	}

	public String msg() {
		return Resources.getMessage("HTTPCODE_" + this.value);
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}
