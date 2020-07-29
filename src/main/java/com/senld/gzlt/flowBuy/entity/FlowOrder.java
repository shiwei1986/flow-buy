package com.senld.gzlt.flowBuy.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.senld.gzlt.flowBuy.base.BaseModel;

/**
 * 
 * 流量订单表
 * FlowOrder实体对象
 *
 * @author system
 * @since 2020-06-10
 */
@TableName("FLOW_ORDER")
public class FlowOrder extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 设备的iccid号
     */
	@TableField("ICCID")
	private String iccid;
    /**
     * 车架号
     */
	@TableField("VIN")
	private String vin;
    /**
     * 订单所属用户名称
     */
	@TableField("USER_NAME")
	private String userName;
    /**
     * 手机号码
     */
	@TableField("MOBILE")
	private String mobile;
    /**
     * 订单号
     */
	@TableField("ORDERNO")
	private String orderno;
    /**
     * 商品编码
     */
	@TableField("GOODS_CODE")
	private String goodsCode;
    /**
     * 商品名称
     */
	@TableField("GOODS_NAME")
	private String goodsName;
    /**
     * 商品单价
     */
	@TableField("SINGLE_PRICE")
	private String singlePrice;
    /**
     * 支付方式：TENWXPAY-微信 ALIPAY-支付宝
     */
	@TableField("PAY_TYPE")
	private String payType;
    /**
     * 下单时间
     */
	@TableField("ORDER_TIME")
	private String orderTime;
    /**
     * 订单状态 1:未付款 2:已付款 3:支付超时
     */
	@TableField("STATE")
	private Integer state;
	
	/**
	 * 订单状态 1:未付款 2:已付款 3:支付超时
	 */
	@TableField("UPDATE_TIME")
	private Date updateTime;


	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(String singlePrice) {
		this.singlePrice = singlePrice;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "FlowOrder{" +
			", iccid=" + iccid +
			", vin=" + vin +
			", userName=" + userName +
			", mobile=" + mobile +
			", orderno=" + orderno +
			", goodsCode=" + goodsCode +
			", goodsName=" + goodsName +
			", singlePrice=" + singlePrice +
			", payType=" + payType +
			", orderTime=" + orderTime +
			", state=" + state +
			", updateTime=" + updateTime +
			"}";
	}
}