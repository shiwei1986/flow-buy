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
 * @Description:商品列表查询的请求实体
 * @Version: 1.0
 *
 */
@Data
@ApiModel(value = "客户查询商品列表请求参数实体类", description = "这是客户查询商品列表请求参数实体类")
public class CommodityParam {
	/** ICCID */
	@NotBlank(message = "iccid不能为空", groups = { AddGroup.class })
	@ApiModelProperty(value = "iccid", name = "iccid", required = true)
	private String iccid;
}
