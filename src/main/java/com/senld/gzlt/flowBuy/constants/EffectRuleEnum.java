package com.senld.gzlt.flowBuy.constants;

public enum EffectRuleEnum {
	RULE_1("购买即生效使用", 1), RULE_2("顺延上一次购买商品的失效时间生效", 2), RULE_3("用户在商城指定生效时间生效", 3), RULE_4("次月生效", 4);
	private String name;
	private Integer value;

	// 构造方法
	private EffectRuleEnum(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	// 普通方法
	public static String getName(Integer value) {
		for (EffectRuleEnum c : EffectRuleEnum.values()) {
			if (c.getValue().equals(value)) {
				return c.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
