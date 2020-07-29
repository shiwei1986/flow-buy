package com.senld.gzlt.flowBuy.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.senld.gzlt.flowBuy.entity.FlowOrder;

/**
 * 
 * 流量订单FlowOrder dao 数据库操作接口
 * 
 *
 * @author system
 * @since 2020-06-10
 */
public interface FlowOrderMapper extends BaseMapper<FlowOrder> {
	/**
	 * 带分页查询流量订单FlowOrder列表
	 * 
	 * @param page
	 *            分页参数
	 * @param param
	 *            flowOrder参数
	 * @return List<FlowOrder>
	 */
	List<FlowOrder> queryList(Pagination page, Map<String, Object> param);

	int updateStateByOrderNo(FlowOrder flowOrder);
	
	List<FlowOrder> queryUnpaidOrders();
}