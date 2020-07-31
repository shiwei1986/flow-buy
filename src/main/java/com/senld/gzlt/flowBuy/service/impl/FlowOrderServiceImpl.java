package com.senld.gzlt.flowBuy.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.senld.gzlt.flowBuy.base.BaseServiceImpl;
import com.senld.gzlt.flowBuy.base.RedisSimpleLock;
import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.constants.ErrorCodeEnum;
import com.senld.gzlt.flowBuy.entity.FlowOrder;
import com.senld.gzlt.flowBuy.mapper.FlowOrderMapper;
import com.senld.gzlt.flowBuy.service.FlowOrderService;

/**
 * 流量订单FlowOrder业务实现类
 *
 * @author system
 * @since 2020-06-10
 */
@Service
public class FlowOrderServiceImpl extends BaseServiceImpl<FlowOrderMapper, FlowOrder> implements FlowOrderService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 分页查询流量订单FlowOrder列表
	 * 
	 * @param params
	 *            查询FlowOrder参数
	 * @return RespBodyObj<Page<FlowOrder>>
	 */
	@Override
	public RespBodyObj<Page<FlowOrder>> queryList(Page<FlowOrder> page, Map<String, Object> params) {
		if (page == null || params == null)
			return RespBodyObj.error(ErrorCodeEnum.REQUEST_PARAM_NULL.getCode(), ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
		List<FlowOrder> list = baseMapper.queryList(page, params);
		page.setRecords(list);
		return RespBodyObj.ok(page);
	}

	@Override
	public List<FlowOrder> queryUnpaidOrders() {
		return baseMapper.queryUnpaidOrders();
	}

	/**
	 * 根据id查询流量订单FlowOrder单个对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	@Override
	public RespBodyObj<FlowOrder> info(FlowOrder flowOrder) {
		return RespBodyObj.ok(baseMapper.selectById(flowOrder.getId()));
	}

	/**
	 * 添加流量订单FlowOrder对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	@Override
	public RespBodyObj<FlowOrder> save(FlowOrder flowOrder) {
		if (baseMapper.insert(flowOrder) > 0) {
			return RespBodyObj.ok(flowOrder);
		} else {
			return RespBodyObj.error(ErrorCodeEnum.SQL_SAVE_ERROR.getCode(), ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
		}
	}

	/**
	 * 修改流量订单FlowOrder对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	@Override
	public RespBodyObj<FlowOrder> update2(Map<String, Object> params) {
		Object object = params.get("content");
		if (null == object) {
			LOGGER.error("content参数不能为空！");
			return RespBodyObj.error("content参数不能为空！");
		}
		String c = object.toString();
		Long id = 1271034218614534146L;
		Integer i = Integer.parseInt(c);
		FlowOrder flowOrder = selectById(id);
		Integer state = flowOrder.getState();
		LOGGER.info("从数据库获取的state值为{},content为{}", state, c);
		Integer result = state - i;
		try {
			if (result < 0) {
				Thread.sleep(1000); // 模拟耗时操作
				LOGGER.error("state不够扣除，不能继续执行！");
				return RespBodyObj.error("state不够扣除，不能继续执行！");
			}
			flowOrder.setState(result);
			if (baseMapper.updateAllColumnById(flowOrder) > 0) {
				Thread.sleep(1000); // 模拟耗时操作
				LOGGER.info("业务逻辑执行完毕");
				LOGGER.info("从数据库获取的state值为{}", selectById(id).getState());
				return RespBodyObj.ok();
			} else {
				return RespBodyObj.error(ErrorCodeEnum.SQL_UPDATE_ERROR.getCode(), ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
			}
		} catch (Exception e) {
			LOGGER.error("系统异常：{}", e);
			return RespBodyObj.error("系统异常！");
		}
	}

	@Override
	public RespBodyObj<FlowOrder> update(Map<String, Object> params) {
		try (RedisSimpleLock lock = new RedisSimpleLock(redisTemplate, "TESTLOCK", 5, TimeUnit.SECONDS)) {
			if (lock.lock()) {
				LOGGER.info("获取锁成功,执行业务逻辑");
				Object object = params.get("content");
				if (null == object) {
					LOGGER.error("content参数不能为空！");
					return RespBodyObj.error("content参数不能为空！");
				}
				String c = object.toString();
				Long id = 1271034218614534146L;
				Integer i = Integer.parseInt(c);
				FlowOrder flowOrder = selectById(id);
				Integer state = flowOrder.getState();
				LOGGER.info("从数据库获取的state值为{},content为{}", state, c);
				Integer result = state - i;
				if (result < 0) {
					Thread.sleep(1000); // 模拟耗时操作
					LOGGER.error("state不够扣除，不能继续执行！");
					return RespBodyObj.error("state不够扣除，不能继续执行！");
				}
				flowOrder.setState(result);
				if (baseMapper.updateAllColumnById(flowOrder) > 0) {
					Thread.sleep(1000); // 模拟耗时操作
					LOGGER.info("业务逻辑执行完毕");
					LOGGER.info("从数据库获取的state值为{}", selectById(id).getState());
					// 释放锁
					lock.unlock();
					return RespBodyObj.ok();
				} else {
					return RespBodyObj.error(ErrorCodeEnum.SQL_UPDATE_ERROR.getCode(), ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
				}
			} else {
				LOGGER.info("未获取到锁");
				return RespBodyObj.error("未获取到锁");
			}
		} catch (Exception e) {
			LOGGER.info("系统异常:{}", e);
			return RespBodyObj.error("系统异常");
		}
	}

	@Override
	public void updateStateByOrderNo(FlowOrder flowOrder) {
		if (null == flowOrder.getState() || StringUtils.isEmpty(flowOrder.getOrderno())) {
			LOGGER.error("修改订单状态时，订单编号以及订单状态均不能为空！");
		}
		baseMapper.updateStateByOrderNo(flowOrder);
	}

	/**
	 * 根据id删除流量订单FlowOrder对象
	 * 
	 * @param flowOrder
	 * @return RespBodyObj<FlowOrder>
	 */
	@Override
	public RespBodyObj<FlowOrder> delete(FlowOrder flowOrder) {
		if (baseMapper.deleteById(flowOrder.getId()) > 0) {
			return RespBodyObj.ok(flowOrder);
		} else {
			return RespBodyObj.error(ErrorCodeEnum.SQL_DELETE_ERROR.getCode(), ErrorCodeEnum.SQL_DELETE_ERROR.getMsg());
		}
	}

}
