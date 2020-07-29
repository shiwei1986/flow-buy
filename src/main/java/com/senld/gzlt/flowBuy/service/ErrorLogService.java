package com.senld.gzlt.flowBuy.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.senld.gzlt.flowBuy.base.BaseService;
import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.entity.ErrorLog;
/**
 * 
 * 错误请求日志记录ErrorLog业务类接口
 * 
 *
 * @author system
 * @since 2020-06-09
 */
public interface ErrorLogService extends BaseService<ErrorLog> {

	 /**
	 * 分页查询错误请求日志记录ErrorLog列表
	 * 
	 * @param params
	 *            查询ErrorLog参数
	 * @return RespBodyObj<Page<ErrorLog>>
	 */
	RespBodyObj<Page<ErrorLog>> queryList(Page<ErrorLog> page,Map<String,Object> params);
	
	/**
	 * 根据id查询错误请求日志记录ErrorLog单个对象
	 * 
	 * @param errorLog
	 * @return RespBodyObj<ErrorLog>
	 */
	RespBodyObj<ErrorLog> info(ErrorLog errorLog);

	/**
	 * 添加错误请求日志记录ErrorLog对象
	 * 
	 * @param errorLog
	 * @return RespBodyObj<ErrorLog>
	 */
	RespBodyObj<ErrorLog> save(ErrorLog errorLog);

	/**
	 * 根据id删除错误请求日志记录ErrorLog对象
	 * 
	 * @param errorLog
	 * @return RespBodyObj<ErrorLog>
	 */
	RespBodyObj<ErrorLog> delete(ErrorLog errorLog);
	
}
