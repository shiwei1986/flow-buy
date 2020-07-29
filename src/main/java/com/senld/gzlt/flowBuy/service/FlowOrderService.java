package com.senld.gzlt.flowBuy.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.senld.gzlt.flowBuy.base.BaseService;
import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.entity.FlowOrder;
/**
 * 
 * 流量订单FlowOrder业务类接口
 * 
 *
 * @author system
 * @since 2020-06-10
 */
public interface FlowOrderService extends BaseService<FlowOrder> {

	 /**
	 * 分页查询流量订单FlowOrder列表
	 * 
	 * @param params
	 *            查询FlowOrder参数
	 * @return RespBodyObj<Page<FlowOrder>>
	 */
	RespBodyObj<Page<FlowOrder>> queryList(Page<FlowOrder> page,Map<String,Object> params);
	
	/**
	 * 查询所有未支付的订单
	 * @return
	 */
	List<FlowOrder> queryUnpaidOrders();
	
	/**
	 * 根据id查询流量订单FlowOrder单个对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	RespBodyObj<FlowOrder> info(FlowOrder flowOrder);

	/**
	 * 添加流量订单FlowOrder对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	RespBodyObj<FlowOrder> save(FlowOrder flowOrder);

	/**
	 * 修改流量订单FlowOrder对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	RespBodyObj<FlowOrder> update(FlowOrder flowOrder);
	
	/**
	 * 根据订单编码修改订单状态
	 * @param orderNo
	 */
	void updateStateByOrderNo(FlowOrder flowOrder);

	/**
	 * 根据id删除流量订单FlowOrder对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	RespBodyObj<FlowOrder> delete(FlowOrder flowOrder);
	
}
