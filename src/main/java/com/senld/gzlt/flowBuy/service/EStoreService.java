package com.senld.gzlt.flowBuy.service;

import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.exception.req.CommodityParam;
import com.senld.gzlt.flowBuy.exception.req.FlowUsageParam;
import com.senld.gzlt.flowBuy.exception.req.OrderInfoParam;
import com.senld.gzlt.flowBuy.exception.req.OrderListParam;
import com.senld.gzlt.flowBuy.exception.req.OrderPayStatusParam;
import com.senld.gzlt.flowBuy.exception.req.PayOrderParam;
import com.senld.gzlt.flowBuy.exception.req.ResidueFlowParam;
import com.senld.gzlt.flowBuy.exception.req.api.PlaceOrderApiParam;

/**
 * 
 * @Author wusw
 * @Date 2020年6月5日
 * @Description:观致电商接口
 * @Version: 1.0
 *
 */
public interface EStoreService {

	/**
	 * C客户所属车辆可用商品查询
	 * 
	 * @param params
	 * @return
	 */
	RespBodyObj<?> queryCommodityList(CommodityParam param) throws Exception;

	/**
	 * 订单下单
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> placeOrder(PlaceOrderApiParam param) throws Exception;

	/**
	 * 订单下单
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> orderInfo(OrderInfoParam param) throws Exception;
	
	/**
	 * 订单下单
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> orderList(OrderListParam param) throws Exception;

	/**
	 * 订单支付
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> payOrder(PayOrderParam param) throws Exception;
	
	/**
	 * 取消订单支付
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> cancelOrder(OrderInfoParam param) throws Exception;
	
	/**
	 * 订单支付状态查询
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> orderPayStatus(OrderPayStatusParam param) throws Exception;
	
	/**
	 * 余量查询
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> getResidueFlow(ResidueFlowParam param) throws Exception;
	
	/**
	 * ESIM卡流量使用情况查询
	 * 
	 * @param param
	 * @return
	 */
	RespBodyObj<?> getFlowUsage(FlowUsageParam param) throws Exception;
	
	/**
	 * 支付回调
	 * @param orderNo
	 */
	void payCallback(String orderNo);
}
