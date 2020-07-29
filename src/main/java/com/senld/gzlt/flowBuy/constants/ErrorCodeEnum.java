package com.senld.gzlt.flowBuy.constants;

public enum ErrorCodeEnum {
	REQUEST_PARAM_NULL(410, "参数为空"),
	SQL_SAVE_ERROR(411, "数据层接口保存数据异常"),
	SQL_DELETE_ERROR(412, "数据层接口删除数据异常"),
	SQL_UPDATE_ERROR(413, "数据层接口修改数据异常"),
    TIME_OUT(403, "访问超时"),
    SYSTEM_ERR(500, "服务器运行异常");
 
    private int code;
    private String msg;
 
    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
 
    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
