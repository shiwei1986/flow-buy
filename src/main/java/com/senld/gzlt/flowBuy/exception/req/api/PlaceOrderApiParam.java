package com.senld.gzlt.flowBuy.exception.req.api;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.senld.gzlt.flowBuy.validator.group.AddGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @Author wusw
 * @Date 2020年6月5日
 * @Description:订单下单请求参数实体
 * @Version: 1.0
 *
 */
@Data
@ApiModel(value = "订单下单请求参数实体类", description = "这是订单下单请求参数实体类")
public class PlaceOrderApiParam {

	/** 订单商品总额 (必填) */
	@NotBlank(message = "订单商品总额不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "订单商品总额", name = "amountMoney", required = true)
	private String amountMoney;

	/** ICCID (必填) */
	@NotBlank(message = "ICCID不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "ICCID", name = "iccid", required = true)
	private String iccid;

	/** 外部单号 */
	@NotBlank(message = "外部单号不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "外部单号", name = "externalCode", required = true)
	private String externalCode;

	/** 下单时间 */
	@NotNull(message = "下单时间不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "下单时间", name = "orderTime", required = true)
	private Long orderTime;

	/** 车机号VIN */
	@NotBlank(message = "车机号VIN不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "车机号VIN", name = "vin", required = true)
	private String vin;
	
	/** 支付方式 */
	@NotBlank(message = "支付方式不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "支付方式：TENWXPAY-微信 ALIPAY-支付宝", name = "payType")
	private String payType;

	/** 商品Code  */
	@NotBlank(message = "商品Code不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "商品Code", name = "goodsCode", required = true)
	private String goodsCode;
	
	/** 商品名称 */
	@NotBlank(message = "商品名称不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "商品名称", name = "goodsName", required = true)
	private String goodsName;
	
	/** 商品单价 */
	@NotBlank(message = "商品单价不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "商品单价", name = "singlePrice", required = true)
	private String singlePrice;
	
	/** 生效时间 */
	@ApiModelProperty(value = "生效时间:时间戳毫秒,当商品生效规则为C用户指定时，此为必填项", name = "effectiveTime")
	private Long effectiveTime;
	
}
