package com.senld.gzlt.flowBuy.exception.req.dto;

import lombok.Data;

/**
 * 
 * @Author wusw
 * @Date 2020年6月8日
 * @Description:下单请求参数实体之OrderGoodsDTO参数
 * @Version: 1.0
 *
 */
@Data
public class OrderGoodsDTO {
	/** 商品Code  */
	private String goodsCode;
	
	/** 商品名称 */
	private String goodsName;
	
	/** 单价 */
	private String singlePrice;
	
	/** 生效时间 */
	private Long effectiveTime;
}
