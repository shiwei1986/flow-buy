package com.senld.gzlt.flowBuy.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.senld.gzlt.flowBuy.base.BaseModel;

/**
 * 
 * 错误请求日志记录表
 * ErrorLog实体对象
 *
 * @author system
 * @since 2020-06-09
 */
@TableName("ERROR_LOG")
public class ErrorLog extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 接口名称
     */
	@TableField("NAME")
	private String name;
    /**
     * 请求方式：post或get
     */
	@TableField("TYPE")
	private String type;
    /**
     * 请求地址
     */
	@TableField("URL")
	private String url;
    /**
     * 参数
     */
	@TableField("PARAM")
	private String param;
    /**
     * 编码
     */
	@TableField("CODE")
	private String code;
    /**
     * 错误信息
     */
	@TableField("MESSAGE")
	private String message;
    /**
     * 错误编码
     */
	@TableField("ERROR_CODE")
	private String errorCode;
    /**
     * 创建日期
     */
	@TableField("CREATE_TIME")
	private Date createTime;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ErrorLog{" +
			", name=" + name +
			", type=" + type +
			", url=" + url +
			", param=" + param +
			", code=" + code +
			", message=" + message +
			", errorCode=" + errorCode +
			", createTime=" + createTime +
			"}";
	}
}