package com.senld.gzlt.flowBuy.mapper;

import com.senld.gzlt.flowBuy.entity.ErrorLog;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * 
 * 错误请求日志记录ErrorLog dao 数据库操作接口
 * 
 *
 * @author system
 * @since 2020-06-09
 */
public interface ErrorLogMapper extends BaseMapper<ErrorLog> {
	/**
	 * 带分页查询错误请求日志记录ErrorLog列表
	 * 
	 * @param page
	 *            分页参数
	 * @param param
	 *            errorLog参数
	 * @return List<ErrorLog>
	 */
	List<ErrorLog> queryList(Pagination page, Map<String, Object> param);

	/**
	 * 不带分页查询错误请求日志记录ErrorLog列表
	 * 
	 * @param param
	 *            ErrorLog参数
	 * @return List<ErrorLog>
	 */
	List<ErrorLog> queryList(Map<String, Object> param);
	
	/**
	 * 不带分页查询错误请求日志记录ErrorLog列表
	 * 
	 * @param errorLog
	 *            ErrorLog参数
	 * @return List<ErrorLog>
	 */
	List<ErrorLog> queryList(ErrorLog errorLog);
}