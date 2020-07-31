package com.senld.gzlt.flowBuy.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.senld.gzlt.flowBuy.constants.OrderStateEnum;
import com.senld.gzlt.flowBuy.entity.FlowOrder;
import com.senld.gzlt.flowBuy.service.FlowOrderService;
import com.senld.gzlt.flowBuy.utils.DateUtils;

/** 

* @author 作者 zhuhechao 

* @version 创建时间：2019年10月13日 下午5:20:16 

* 类说明 

*/
/**
 * 发送等待确认的消息(位置消息等)定时任务
 * 
 * @author zhuhechao
 * @Date 2019年11月8日
 */
@Component
@Async("SimpleAsyncTaskExecutor")
public class SystemTask {
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	protected static final int MILLISECOND_72H = 72;

	protected static final int MILLISECOND_1H = 1;

	@Autowired
	private FlowOrderService flowOrderService;

	/**
	 * 开启只运行一次的任务
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
	}

	/**
	 * 流量订单定时任务,每隔10秒执行一次任务
	 */
//	@Scheduled(cron = "*/10 * * * * ?")
//	@Scheduled(cron = "0 */5 * * * ?")
	public void flowOrderTask() {
		List<FlowOrder> flowOrders = flowOrderService.queryUnpaidOrders();
		if (null != flowOrders && flowOrders.size() > 0) {
			List<FlowOrder> toUpdateOrders = new ArrayList<>();
			Date compareDate = DateUtils.beforeHourToNowDate(MILLISECOND_1H);
			for (FlowOrder flowOrder : flowOrders) {
				String orderTime = flowOrder.getOrderTime();
				Date orderDate = DateUtils.parse(orderTime);
				if (compareDate.after(orderDate)) {
					// 超时
					flowOrder.setState(OrderStateEnum.TIMEOUT.getValue());
					toUpdateOrders.add(flowOrder);
				}
			}
			if (toUpdateOrders.size() > 0) {
				flowOrderService.updateBatchById(toUpdateOrders);
				LOGGER.info("定时修改超时订单状态{}条", toUpdateOrders.size());
			}
		}
	}

}
