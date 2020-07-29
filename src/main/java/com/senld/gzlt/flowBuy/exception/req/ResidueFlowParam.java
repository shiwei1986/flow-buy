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
@ApiModel(value = "余量查询参数实体类", description = "这是余量查询参数实体类")
public class ResidueFlowParam {
	/** ESIM卡唯一的编号 */
	@NotBlank(message = "iccid不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "iccid", name = "iccid")
	private String iccid;
}
