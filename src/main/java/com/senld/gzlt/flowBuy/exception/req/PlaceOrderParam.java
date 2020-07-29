package com.senld.gzlt.flowBuy.exception.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.senld.gzlt.flowBuy.exception.req.dto.OrderGoodsDTO;
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
public class PlaceOrderParam {

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
	private String externalCode;

	/** 下单时间 */
	@NotNull(message = "下单时间不能为空", groups = { AddGroup.class })
	private Long orderTime;

	/** 车机号VIN */
	private String vin;

	/** 订单商品集合(必填) */
	@NotNull(message = "订单商品集合不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "订单商品集合", name = "goodsDTOS", required = true)
	private List<OrderGoodsDTO> goodsDTOS;
}
