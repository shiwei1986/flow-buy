package com.senld.gzlt.flowBuy.async.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.senld.gzlt.flowBuy.async.deferredResult.ResponseMsg;
import com.senld.gzlt.flowBuy.async.deferredResult.TaskQueue;

@RestController
@RequestMapping("/task")
public class TaskController {

	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	// 超时结果
	private static final ResponseMsg<String> OUT_OF_TIME_RESULT = new ResponseMsg<>(-1, "超时", "out of time");

	// 超时时间
	private static final long OUT_OF_TIME = 10000L;

	@Autowired
	private TaskQueue taskQueue;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public DeferredResult<ResponseMsg<String>> getResult() {

		log.info("接收请求，开始处理...");

		// 建立DeferredResult对象，设置超时时间，以及超时返回超时结果
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<>(OUT_OF_TIME, OUT_OF_TIME_RESULT);

		result.onTimeout(() -> {
			log.info("调用超时");
		});

		result.onCompletion(() -> {
			log.info("调用完成");
		});

		// 并发，加锁
		synchronized (taskQueue) {

			taskQueue.put(result);

		}

		log.info("接收任务线程完成并退出");

		return result;

	}

}
