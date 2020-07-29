package com.senld.gzlt.flowBuy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.senld.gzlt.flowBuy.base.BaseServiceImpl;
import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.constants.ErrorCodeEnum;
import com.senld.gzlt.flowBuy.entity.ErrorLog;
import com.senld.gzlt.flowBuy.mapper.ErrorLogMapper;
import com.senld.gzlt.flowBuy.service.ErrorLogService;
/**
 * 错误请求日志记录ErrorLog业务实现类
 *
 * @author system
 * @since 2020-06-09
 */
@Service
public class ErrorLogServiceImpl extends BaseServiceImpl<ErrorLogMapper, ErrorLog> implements ErrorLogService {
    
    /**
	 * 分页查询错误请求日志记录ErrorLog列表
	 * 
	 * @param params
	 *            查询ErrorLog参数
	 * @return RespBodyObj<Page<ErrorLog>>
	 */
	@Override
	public RespBodyObj<Page<ErrorLog>> queryList(Page<ErrorLog> page, Map<String,Object> params) {
		if (page == null||params == null)
			return RespBodyObj.error(ErrorCodeEnum.REQUEST_PARAM_NULL.getCode(),ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
		List<ErrorLog> list = baseMapper.queryList(page, params);
		page.setRecords(list);
		return RespBodyObj.ok(page);
	}

	/**
	 * 根据id查询错误请求日志记录ErrorLog单个对象
	 * 
	 * @param errorLog
	 * @return RespBodyObj<ErrorLog>
	 */
	@Override
	public RespBodyObj<ErrorLog> info(ErrorLog errorLog) {
		return RespBodyObj.ok(baseMapper.selectById(errorLog.getId()));
	}

	/**
	 * 添加错误请求日志记录ErrorLog对象
	 * 
	 * @param errorLog
	 * @return RespBodyObj<ErrorLog>
	 */
	@Override
	public RespBodyObj<ErrorLog> save(ErrorLog errorLog) {
		if (baseMapper.insert(errorLog) > 0) {
			return RespBodyObj.ok(errorLog);
		} else {
			return RespBodyObj.error(ErrorCodeEnum.SQL_SAVE_ERROR.getCode(),ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
		}
	}

	/**
	 * 根据id删除错误请求日志记录ErrorLog对象
	 * 
	 * @param errorLog
	 * @return RespBodyObj<ErrorLog>
	 */
	@Override
	public RespBodyObj<ErrorLog> delete(ErrorLog errorLog) {
		if (baseMapper.deleteById(errorLog.getId()) > 0) {
			return RespBodyObj.ok(errorLog);
		} else {
			return RespBodyObj.error(ErrorCodeEnum.SQL_DELETE_ERROR.getCode(),ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
		}
	}
	
}
