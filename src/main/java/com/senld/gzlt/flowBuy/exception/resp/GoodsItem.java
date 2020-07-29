package com.senld.gzlt.flowBuy.exception.resp;

import java.util.List;

import lombok.Data;

@Data
public class GoodsItem {
	private String id;
	private String goodsId;
	private String serviceId;
	private List<AbilityRight> abilityRights;
	private List<ContentRight> contentRights;
}
