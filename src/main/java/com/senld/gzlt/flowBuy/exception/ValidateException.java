package com.senld.gzlt.flowBuy.exception;

/**
 * 数据校验自定义异常
 * 
 * @author chao
 *
 */
public class ValidateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidateException(String message) {
		super(message);
	}
}
