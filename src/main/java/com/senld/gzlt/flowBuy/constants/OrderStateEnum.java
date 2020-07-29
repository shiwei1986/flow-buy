package com.senld.gzlt.flowBuy.constants;

public enum OrderStateEnum {
	// 支付状态 1:未付款 2:已付款 3:支付超时 4:取消
	UNPAID("未付款", 1), PAID("已付款", 2), TIMEOUT("支付超时", 3), CANCEL("取消", 4);
	private String name;
	private Integer value;

	// 构造方法
	private OrderStateEnum(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	// 普通方法
	public static String getName(Integer value) {
		for (OrderStateEnum c : OrderStateEnum.values()) {
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
