package com.senld.gzlt.flowBuy.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senld.gzlt.flowBuy.base.HttpCode;
import com.senld.gzlt.flowBuy.base.RespBodyObj;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(SenldException.class)
	@ResponseBody
	public RespBodyObj<?> handleStudentException(HttpServletRequest request, SenldException ex) {
		log.error("SenldException code:{},msg:{}", ex.getCode(), ex.getMsg());
		return RespBodyObj.error(ex.getCode(), ex.getMsg());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	@ResponseBody
	public RespBodyObj<?> handleException(HttpServletRequest request, Exception ex) {
		log.error("exception error:{}", ex);
		return RespBodyObj.error(HttpCode.INTERNAL_SERVER_ERROR.value(), "服务器运行异常");
	}

}
