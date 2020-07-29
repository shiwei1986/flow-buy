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
@ApiModel(value = "订单支付请求参数实体类", description = "这是订单支付请求参数实体类")
public class PayOrderParam {
	/** 订单编号 */
	@NotBlank(message = "订单编号不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "订单编号", name = "orderNo")
	private String orderNo;

	/** 支付方式 */
	@NotBlank(message = "支付方式不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "支付方式：TENWXPAY-微信 ALIPAY-支付宝", name = "payType")
	private String payType;

	/** 订单金额 */
	@ApiModelProperty(value = "订单金额：单位元 精确到分两位小数", name = "totalFee")
	private String totalFee;
}
