package com.senld.gzlt.flowBuy.exception.req;

import org.hibernate.validator.constraints.NotBlank;

import com.senld.gzlt.flowBuy.validator.group.AddGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @Author wusw
 * @Date 2020年6月5日
 * @Description:订单详情请请求实体
 * @Version: 1.0
 *
 */
@Data
@ApiModel(value = "订单支付回调请求参数实体类", description = "这是订单支付回调状态请求参数实体类")
public class PayCallbackParam {
	/** 交易时间 */
	@NotBlank(message = "交易时间不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "交易时间", name = "tradeTime")
	private String tradeTime;
	
	/** 订单编号 */
	@NotBlank(message = "订单编号不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "订单编号", name = "orderNo")
	private String orderNo;
	
	/** 订单金额 */
	@NotBlank(message = "订单金额不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "订单金额", name = "totalFee")
	private String totalFee;
	
	/** 支付中心单号 */
	@NotBlank(message = "支付中心单号不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "支付中心单号", name = "outTradeNo")
	private String outTradeNo;
	
	/** 支付方式 */
	@NotBlank(message = "支付方式不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "支付方式：TENWXPAY-微信 ALIPAY-支付宝", name = "payType")
	private String payType;
	
	/** 交易类型 */
	@NotBlank(message = "交易类型不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "交易类型：APP、WAP、WEB、NATIVE", name = "tradeType")
	private String tradeType;
	
	/** 第三方支付平台单号 */
	@NotBlank(message = "第三方支付平台单号不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "第三方支付平台单号：支付宝或微信返回单号", name = "serialNumber")
	private String serialNumber;
}
