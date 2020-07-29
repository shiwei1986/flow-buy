package com.senld.gzlt.flowBuy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.senld.gzlt.flowBuy.base.BaseController;
import com.senld.gzlt.flowBuy.base.BaseUrl;
import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.entity.ErrorLog;
import com.senld.gzlt.flowBuy.service.ErrorLogService;
import com.senld.gzlt.flowBuy.validator.ValidatorUtils;
import com.senld.gzlt.flowBuy.validator.group.AddGroup;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * 
 * ErrorLogweb请求处理控制器 错误请求日志记录表错误请求日志记录
 * 
 *
 * @author system
 * @since 2020-06-09
 */
@RestController
@Api(tags = "错误请求日志记录web请求控制器")
@RequestMapping("/errorLog")
public class ErrorLogController extends BaseController {
	@Autowired
	private ErrorLogService  errorLogService;
	
    /**
	 * 分页查询ErrorLog对象列表
	 *
	 * @param params
	 *            ErrorLog
	 * @return RespBodyObj<Page<ErrorLog>>
	 */
	@PostMapping(BaseUrl.QUERY_URL)
	@ApiOperation("分页查询错误请求日志记录")
	public RespBodyObj<Page<ErrorLog>> list(@RequestBody Map<String, Object>  params){
	   	Page<ErrorLog> page = this.buildPage(params);
		return errorLogService.queryList(page,params);
	}
	
	/**
     * 添加ErrorLog对象
     *
     * @param params
	 *            ErrorLog
     * @return RespBodyObj<ErrorLog>
     */
	@PostMapping(BaseUrl.SAVE_URL)
	@ApiOperation("添加错误请求日志记录")
	public RespBodyObj<ErrorLog> save(@RequestBody ErrorLog errorLog){
	   ValidatorUtils.validateEntity(errorLog, AddGroup.class);
	   return errorLogService.save(errorLog);
	
	}
	
    /**
     * 查询单个ErrorLog对象
     *
     * @param params
	 *            ErrorLog
     * @return RespBodyObj<ErrorLog>
     */
	@PostMapping(BaseUrl.INFO_URL)
	@ApiOperation("单个查询错误请求日志记录")
	public RespBodyObj<ErrorLog> info(@RequestBody ErrorLog errorLog){
		 return errorLogService.info(errorLog);
		 
	}
	
	/**
     * 删除ErrorLog对象
     *
     * @param errorLog
     * @return RespBodyObj<ErrorLog>
     */
	@PostMapping(BaseUrl.DELETE_URL)
	@ApiOperation("删除错误请求日志记录")
	public RespBodyObj<ErrorLog> delete(@RequestBody ErrorLog errorLog){
		 return errorLogService.delete(errorLog);
	}
	
	public void setErrorLogService(ErrorLogService errorLogService) {
		this.errorLogService = errorLogService;
	}
}
