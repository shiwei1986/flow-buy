package com.senld.gzlt.flowBuy.base;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 数据与java bean基础实体
 * 
 * @author system
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(type = IdType.ID_WORKER)
	@TableField("ID")
	protected Long id;// 主键
	
	public BaseModel() {
	}

	public BaseModel(Long id) {
		this.id = id;
	}

	/**
	 * 获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
