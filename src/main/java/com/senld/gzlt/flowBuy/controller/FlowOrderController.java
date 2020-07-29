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
import com.senld.gzlt.flowBuy.entity.FlowOrder;
import com.senld.gzlt.flowBuy.service.FlowOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * 
 * FlowOrderweb请求处理控制器 流量订单表流量订单
 * 
 *
 * @author system
 * @since 2020-06-10
 */
@RestController
@Api(tags = "流量订单web请求控制器")
@RequestMapping("/flowOrder")
public class FlowOrderController extends BaseController {
	@Autowired
	private FlowOrderService  flowOrderService;
	
    /**
	 * 分页查询FlowOrder对象列表
	 *
	 * @param params
	 *            FlowOrder
	 * @return RespBodyObj<Page<FlowOrder>>
	 */
	@PostMapping(BaseUrl.QUERY_URL)
	@ApiOperation("分页查询流量订单")
	public RespBodyObj<Page<FlowOrder>> list(@RequestBody Map<String, Object>  params){
	   	Page<FlowOrder> page = this.buildPage(params);
		return flowOrderService.queryList(page,params);
	}
	
    /**
     * 查询单个FlowOrder对象
     *
     * @param params
	 *            FlowOrder
     * @return RespBodyObj<FlowOrder>
     */
	@PostMapping(BaseUrl.INFO_URL)
	@ApiOperation("单个查询流量订单")
	public RespBodyObj<FlowOrder> info(@RequestBody FlowOrder flowOrder){
		 return flowOrderService.info(flowOrder);
		 
	}
	
	/**
     * 删除FlowOrder对象
     *
     * @param flowOrder
     * @return RespBodyObj<FlowOrder>
     */
	@PostMapping(BaseUrl.DELETE_URL)
	@ApiOperation("删除流量订单")
	public RespBodyObj<FlowOrder> delete(@RequestBody FlowOrder flowOrder){
		 return flowOrderService.delete(flowOrder);
	}
	
	public void setFlowOrderService(FlowOrderService flowOrderService) {
		this.flowOrderService = flowOrderService;
	}
}
