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
 * @Description:余量查询参数实体类
 * @Version: 1.0
 *
 */
@Data
@ApiModel(value = "ESIM卡流量使用情况查询参数实体类", description = "这是ESIM卡流量使用情况查询参数实体类")
public class FlowUsageParam {
	/** 月份 */
	@NotBlank(message = "月份不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "月份，格式：yyyy-MM", name = "queryDate")
	private String queryDate;

	/** ESIM卡唯一的编号 */
	@NotBlank(message = "iccid不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "iccid", name = "iccid", required = true)
	private String iccid;

	/** APN名称 */
	@NotBlank(message = "APN名称不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "APN名称", name = "apn", required = true)
	private String apn;
}
