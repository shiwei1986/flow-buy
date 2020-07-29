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
@ApiModel(value = "订单详情请求参数实体类", description = "这是订单详情请请求参数实体类")
public class OrderListParam {
	/** 车辆ICCID卡 */
	@NotBlank(message = "车辆ICCID卡不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "车辆ICCID卡", name = "iccid", required = true)
	private String iccid;

	/** 订单状态 */
	@ApiModelProperty(value = "订单状态 1:未付款 2:已付款 3支付超时4取消", name = "state")
	private Integer state;

	/** 当前页 */
	@ApiModelProperty(value = "当前页，默认值从第一页开始", name = "pageNum")
	private Integer pageNum = 1;

	/** 每页返回条数 */
	@ApiModelProperty(value = "每页返回条数，默认值20", name = "pageSize")
	private Integer pageSize = 20;
}
