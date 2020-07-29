package com.senld.gzlt.flowBuy.exception.resp;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ContentRight {
	private String code;
	private String name;
	private String type;
	private List<Map<String, Object>> extend;
}
