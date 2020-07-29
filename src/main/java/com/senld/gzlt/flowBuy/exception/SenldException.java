package com.senld.gzlt.flowBuy.exception;

/**
 * 自定义异常
 * 
 * @author system
 * @Date 2018年4月10日
 */
public class SenldException extends RuntimeException {
	private static final long serialVersionUID = -8497614403215517907L;

	private String msg;
	private int code = 500;

	public SenldException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public SenldException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public SenldException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public SenldException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
