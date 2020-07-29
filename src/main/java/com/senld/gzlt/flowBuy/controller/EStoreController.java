package com.senld.gzlt.flowBuy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senld.gzlt.flowBuy.base.HttpCode;
import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.config.LimitKey;
import com.senld.gzlt.flowBuy.exception.SenldException;
import com.senld.gzlt.flowBuy.exception.req.CommodityParam;
import com.senld.gzlt.flowBuy.exception.req.FlowUsageParam;
import com.senld.gzlt.flowBuy.exception.req.OrderInfoParam;
import com.senld.gzlt.flowBuy.exception.req.OrderListParam;
import com.senld.gzlt.flowBuy.exception.req.OrderPayStatusParam;
import com.senld.gzlt.flowBuy.exception.req.PayOrderParam;
import com.senld.gzlt.flowBuy.exception.req.ResidueFlowParam;
import com.senld.gzlt.flowBuy.exception.req.api.PlaceOrderApiParam;
import com.senld.gzlt.flowBuy.service.EStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "观致电商接口请求控制器")
@RequestMapping("/eStore")
@Slf4j
public class EStoreController {

	@Autowired
	private EStoreService eStoreService;

	/**
	 * C客户查询商品列表
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/queryCommodityList")
	@ApiOperation("C客户查询商品列表")
	public RespBodyObj<?> queryCommodityList(@RequestBody CommodityParam param) throws Throwable {
		try {
			return eStoreService.queryCommodityList(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * 订单下单
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/placeOrder")
	@ApiOperation("订单下单")
	public RespBodyObj<?> placeOrder(@RequestBody PlaceOrderApiParam param) throws Throwable {
		try {
			return eStoreService.placeOrder(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * 订单列表查询
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/orderList")
	@ApiOperation("订单列表查询")
	public RespBodyObj<?> orderList(@RequestBody OrderListParam param) throws Throwable {
		try {
			return eStoreService.orderList(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * 订单详情查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/orderInfo")
	@ApiOperation("订单详情")
	@LimitKey(frequency = 2, methodName = "orderInfo", url = "/eStore/orderInfo", timeout = 5l)
	public RespBodyObj<?> orderInfo(@RequestBody OrderInfoParam param) throws Throwable {
		try {
			return eStoreService.orderInfo(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * 订单支付
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/payOrder")
	@ApiOperation("订单支付")
	public RespBodyObj<?> payOrder(@RequestBody PayOrderParam param) throws Exception {
		try {
			return eStoreService.payOrder(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * 订单取消
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/cancelOrder")
	@ApiOperation("订单取消")
	public RespBodyObj<?> cancelOrder(@RequestBody OrderInfoParam param) throws Throwable {
		try {
			return eStoreService.cancelOrder(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * 订单支付状态
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/orderPayStatus")
	@ApiOperation("订单支付状态")
	@LimitKey
	public RespBodyObj<?> orderPayStatus(@RequestBody OrderPayStatusParam param) throws Throwable {
		try {
			return eStoreService.orderPayStatus(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * 统一回调接口
	 * 支付回调、订单推送、订单商品推送以及订单商品服务开通推送，原因：订单以及订单商品状态改变了所以会有这两个，服务开通这个是这样的，套餐叠加了，就推送了
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/callback")
	@ApiOperation("订单支付回调接口")
	public RespBodyObj<?> payCallback(@RequestBody Map<String, Object> param, @RequestHeader("X-BUSINESS-TYPE") String bussessType) {
		if ("CALL_PAY_SUCCESS".equals(bussessType)) {
			log.info("订单支付回调成功！");
			eStoreService.payCallback(param.get("orderNo").toString());
		} else {
			log.info("非订单支付请求回调 X-BUSINESS-TYPE:{}" + bussessType);
		}
		return RespBodyObj.ok();
	}

	/**
	 * 余量查询
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/getResidueFlow")
	@ApiOperation("余量查询")
	public RespBodyObj<?> getResidueFlow(@RequestBody ResidueFlowParam param) throws Throwable {
		try {
			return eStoreService.getResidueFlow(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

	/**
	 * ESIM卡流量使用情况
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/getFlowUsage")
	@ApiOperation("ESIM卡流量使用情况查询")
	public RespBodyObj<?> getFlowUsage(@RequestBody FlowUsageParam param) throws Throwable {
		try {
			return eStoreService.getFlowUsage(param);
		} catch (Exception e) {
			if (e instanceof SenldException) {
				throw e;
			} else {
				throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
			}
		}
	}

}
