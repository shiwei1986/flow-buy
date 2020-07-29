package com.senld.gzlt.flowBuy.exception.resp;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 
 * @Author wusw
 * @Date 2020年6月5日
 * @Description:商品信息
 * @Version: 1.0
 *
 */
@Data
public class CommodityVO {
	/** 商品编码 */
	private String id;

	/** 商品名称 */
	private String goodsName;

	/** 商品库存属性 */
	private Integer store;

	/** 自动发货: 0 自动发货， 1手动发货 */
	private Integer autoDelivery;

	/** 商品单位 */
	private String unit;

	/** 商品备注 */
	private String remark;

	/** 商品描述 */
	private String description;

	/** 商品图片 */
	private String pic;

	/** 商品审核状态 */
	private Integer verifyState;

	/** 商品上下架：0 下架；1上架 */
	private Integer goodsState;

	/** 商品价格 */
	private String price;

	/** 商品最后更新时间 */
	private String updateTime;

	/** 上架销售时间 */
	private String saleTime;

	/** 下架时间 */
	private String noSaleTime;

	/** 是否有码：0 无码； 1有码 */
	private Integer isCode;

	/** 商品编码 */
	private String goodsCode;

	/**
	 * 商品生效规则：1 购买即生效使用 2 顺延生效：顺延上一次购买商品的失效时间生效，如购买多次，排队顺延 3 C用户在商城指定生效时间生效
	 * 4次月生效：购买的商品次月生效
	 */
	private Integer effectRules;
	
	/** 商品扩展字段:商品的补充信息，根据与接入厂商的约定进行配置 */
	private Map<String, Object> extendField;
	
	/** 英文商品名称 */
	private String goodsNameEn;
	
	/** 英文商品描述 */
	private String descriptionEn;
	
	/** wap版商品图片 */
	private String wapPic;
	
	/** 车机版商品图片 */
	private String vehiclePic;
	
	/** 客户名称 */
	private String customerName;
	
	private List<GoodsItem> goodsItemGroup;
}
