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
@ApiModel(value = "订单支付状态请求参数实体类", description = "这是订单支付状态请求参数实体类")
public class OrderPayStatusParam {
	/** 订单编号 */
	@NotBlank(message = "订单编号不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "订单编号", name = "orderNo")
	private String orderNo;
}
