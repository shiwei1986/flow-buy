package com.senld.gzlt.flowBuy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.constants.EffectRuleEnum;
import com.senld.gzlt.flowBuy.constants.OrderStateEnum;
import com.senld.gzlt.flowBuy.entity.FlowOrder;
import com.senld.gzlt.flowBuy.exception.req.CommodityParam;
import com.senld.gzlt.flowBuy.exception.req.FlowUsageParam;
import com.senld.gzlt.flowBuy.exception.req.OrderInfoParam;
import com.senld.gzlt.flowBuy.exception.req.OrderListParam;
import com.senld.gzlt.flowBuy.exception.req.OrderPayStatusParam;
import com.senld.gzlt.flowBuy.exception.req.PayOrderParam;
import com.senld.gzlt.flowBuy.exception.req.PlaceOrderParam;
import com.senld.gzlt.flowBuy.exception.req.ResidueFlowParam;
import com.senld.gzlt.flowBuy.exception.req.api.PlaceOrderApiParam;
import com.senld.gzlt.flowBuy.exception.req.dto.OrderGoodsDTO;
import com.senld.gzlt.flowBuy.service.EStoreService;
import com.senld.gzlt.flowBuy.service.FlowOrderService;
import com.senld.gzlt.flowBuy.utils.DateUtils;
import com.senld.gzlt.flowBuy.utils.HttpUtils;
import com.senld.gzlt.flowBuy.utils.JsonUtil;
import com.senld.gzlt.flowBuy.validator.ValidatorUtils;
import com.senld.gzlt.flowBuy.validator.group.AddGroup;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EStoreServiceImpl implements EStoreService {

	@Autowired
	private HttpUtils httpUtils;

	@Autowired
	private FlowOrderService flowOrderService;

	@Override
	public RespBodyObj<?> queryCommodityList(CommodityParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String url = "/business/commodity/c-user/list?iccid=" + param.getIccid();
		List<Map<String, Object>> result = httpUtils.getForList(url, "C客户所属车辆可用商品查询", JsonUtil.toJson(param));
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (null != result && result.size() > 0) {
			for (Map<String, Object> map : result) {
				// 结果返回
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("goodsCode", map.get("goodsCode"));
				resultMap.put("goodsName", map.get("goodsName"));
				resultMap.put("price", map.get("price"));
				resultMap.put("effectRules", handelIntStr(map.get("effectRules")));
				resultList.add(resultMap);
			}
		}

		return RespBodyObj.ok(resultList);

	}

	private Integer handelIntStr(Object obj) {
		String objStr = obj.toString();
		return Integer.parseInt(objStr.substring(0, objStr.length() - 2));
	}

	@Override
	public RespBodyObj<?> placeOrder(PlaceOrderApiParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String orderNo = param.getExternalCode();
		// 调用联通"订单下单"接口,验证生效时间是否要填
		Long effectiveTime = param.getEffectiveTime();
		String singleGoodsUrl = "/business/commodity/" + param.getGoodsCode();
		Map<String, Object> singleGoodsMap = httpUtils.get(singleGoodsUrl, "根据商品编码单个查询商品信息", JsonUtil.toJson(param));
		Integer effectRules = handelIntStr(singleGoodsMap.get("effectRules"));
		if (effectRules == 3) {
			if (null == effectiveTime) {
				log.error("当商品生效规则为3:C用户指定时，生效时间不能为空！");
				throw new RuntimeException("当商品生效规则为3:C用户指定时，生效时间不能为空！");
			}
		}
		String jsonParam = changePlaceOrderParam(param);
		String url = "/business/order/order-info";
		Map<String, Object> orderMap = httpUtils.post(url, "订单下单", jsonParam);
		// 保存订单
		FlowOrder flowOrder = new FlowOrder();
		flowOrder.setIccid(param.getIccid());
		flowOrder.setOrderno(orderNo);
		flowOrder.setVin(param.getVin());
		flowOrder.setMobile((String) orderMap.get("mobile"));
		flowOrder.setGoodsCode(param.getGoodsCode());
		flowOrder.setGoodsName(param.getGoodsName());
		flowOrder.setOrderTime(DateUtils.format(param.getOrderTime()));
		flowOrder.setSinglePrice(param.getSinglePrice());
		flowOrder.setPayType(param.getPayType());
		flowOrder.setUserName((String) orderMap.get("userName"));
		// 支付状态 1:未付款 2:已付款 3:支付超时 4:取消
		flowOrder.setState(OrderStateEnum.UNPAID.getValue());
		flowOrderService.save(flowOrder);
		// 结果返回
		Map<String, Object> result = new HashMap<>();
		String description = EffectRuleEnum.getName(effectRules);
		result.put("amountMoney", orderMap.get("amountMoney"));
		result.put("description", description);
		result.put("orderNo", orderNo);
		result.put("paymentMoney", orderMap.get("paymentMoney"));
		// 调用联通"订单支付"接口，获取二维码base64字符串
		PayOrderParam payOrderParam = new PayOrderParam();
		payOrderParam.setOrderNo(orderNo);
		payOrderParam.setPayType(param.getPayType());
		String jsonPayOrderParam = JsonUtil.toJson(payOrderParam);
		String payOrderUrl = "/business/pay/pay-qr";
		Map<String, Object> payOrderMap = httpUtils.post(payOrderUrl, "订单支付", jsonPayOrderParam);
		result.put("qrCodeBase64", payOrderMap.get("qrCodeBase64"));
		return RespBodyObj.ok(result);
	}

	private String changePlaceOrderParam(PlaceOrderApiParam param) throws Exception {
		PlaceOrderParam result = new PlaceOrderParam();
		result.setAmountMoney(param.getAmountMoney());
		result.setExternalCode(param.getExternalCode());
		result.setIccid(param.getIccid());
		result.setOrderTime(param.getOrderTime());
		result.setVin(param.getVin());
		List<OrderGoodsDTO> goodsDTOS = new ArrayList<>();
		OrderGoodsDTO dto = new OrderGoodsDTO();
		// 验证生效时间是否要填
		dto.setEffectiveTime(param.getEffectiveTime());
		// 设置商品编码
		dto.setGoodsCode(param.getGoodsCode());
		dto.setGoodsName(param.getGoodsName());
		dto.setSinglePrice(param.getSinglePrice());
		goodsDTOS.add(dto);
		result.setGoodsDTOS(goodsDTOS);
		return JsonUtil.toJson(result);
	}

	@Override
	public RespBodyObj<?> orderInfo(OrderInfoParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String orderNo = param.getOrderNo();
		String url = "/business/order/order-info/" + orderNo;
		Map<String, Object> result = httpUtils.get(url, "订单详情查询", JsonUtil.toJson(param));
		return RespBodyObj.ok(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RespBodyObj<?> orderList(OrderListParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String jsonParam = JsonUtil.toJson(param);
		String url = "/business/order/order-info/page";
		Map<String, Object> jsonStr = httpUtils.post(url, "订单列表查询", jsonParam);
		List<Map<String, Object>> data = (List<Map<String, Object>>) jsonStr.get("data");
		List<Map<String, Object>> list = new ArrayList<>();
		if (null != data && data.size() > 0) {
			for (Map<String, Object> map : data) {
				Map<String, Object> result = new HashMap<>();
				result.put("orderNo", map.get("orderNo"));
				result.put("vin", map.get("vin"));
				result.put("mobile", map.get("mobile"));
				result.put("state", map.get("state"));
				result.put("iccid", map.get("iccid"));
				result.put("amountMoney", map.get("amountMoney"));
				result.put("orderTime", map.get("orderTime"));
				result.put("createTime", map.get("createTime"));
				list.add(result);
			}
			jsonStr.put("data", list);
		}
		return RespBodyObj.ok(jsonStr);
	}

	@Override
	public RespBodyObj<?> payOrder(PayOrderParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String jsonParam = JsonUtil.toJson(param);
		String url = "/business/pay/pay-qr";
		Map<String, Object> jsonStr = httpUtils.post(url, "订单支付", jsonParam);
		return RespBodyObj.ok(jsonStr);
	}

	@Override
	public RespBodyObj<?> cancelOrder(OrderInfoParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String jsonParam = JsonUtil.toJson(param);
		String url = "/business/order/order-info/" + param.getOrderNo() + "/cancel";
		Map<String, Object> jsonStr = httpUtils.post(url, "订单取消", jsonParam);
		// 对应修改订单状态
		FlowOrder flowOrder = new FlowOrder();
		flowOrder.setOrderno(param.getOrderNo());
		flowOrder.setState(OrderStateEnum.CANCEL.getValue());
		flowOrderService.updateStateByOrderNo(flowOrder);
		return RespBodyObj.ok(jsonStr);
	}

	@Override
	public RespBodyObj<?> orderPayStatus(OrderPayStatusParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String orderNo = param.getOrderNo();
		String url = "/business/pay/order-query?orderNo=" + orderNo;
		List<Map<String, Object>> result = httpUtils.getForList(url, "订单支付状态查询", JsonUtil.toJson(param));
		return RespBodyObj.ok(result);
	}

	@Override
	public RespBodyObj<?> getResidueFlow(ResidueFlowParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String jsonParam = JsonUtil.toJson(param);
		String url = "/flowctrl/business/getTerminalPackageUsage";
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> list = httpUtils.postForList(url, "余量查询", jsonParam);
		if (null != list && list.size() > 0) {
			int totalUsage = 0;
			int usedUsage = 0;
			int remainUsage = 0;
			for (Map<String, Object> map : list) {
				totalUsage += handelIntStr(map.get("totalUsage"));
				usedUsage += handelIntStr(map.get("usedUsage"));
				remainUsage += handelIntStr(map.get("remainUsage"));
			}
			Map<String, Object> map = list.get(0);
			result.put("iccid", map.get("iccid"));
			result.put("msisdn", map.get("msisdn"));
			result.put("totalUsage", totalUsage);
			result.put("usedUsage", usedUsage);
			result.put("remainUsage", remainUsage);
		}
		return RespBodyObj.ok(result);
	}

	@Override
	public RespBodyObj<?> getFlowUsage(FlowUsageParam param) throws Exception {
		ValidatorUtils.validateEntity(param, AddGroup.class);
		String jsonParam = JsonUtil.toJson(param);
		String url = "/flowctrl/message/surCheck";
		Map<String, Object> jsonStr = httpUtils.post(url, "ESIM卡流量使用情况查询", jsonParam);
		return RespBodyObj.ok(jsonStr);
	}

	@Override
	public void payCallback(String orderNo) {
		// 对应修改订单状态
		FlowOrder flowOrder = new FlowOrder();
		flowOrder.setOrderno(orderNo);
		flowOrder.setState(OrderStateEnum.PAID.getValue());
		flowOrderService.updateStateByOrderNo(flowOrder);
	}

}
