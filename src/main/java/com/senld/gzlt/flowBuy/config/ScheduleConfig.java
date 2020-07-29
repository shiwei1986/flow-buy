package com.senld.gzlt.flowBuy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 系统任务执行工厂配置类
 * 
 * @author zhuhechao
 * @Date 2019年10月12日
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
	/**
	 * 线程池配置
	 * 
	 * @return
	 */
	@Bean("SimpleAsyncTaskExecutor")
	public ThreadPoolTaskExecutor simpleAsyncExecutor() {
		// 默认的SimpleAsyncTaskExecutor每个任务会新建一个线程去运行。
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();// 可以重复利用已建的空闲的线程。
		executor.setCorePoolSize(100);// 核心线程数
		executor.setMaxPoolSize(150);// 最大线程数
		executor.setQueueCapacity(2000);// 等待任务数
		executor.setThreadNamePrefix("任务工厂-");// 前缀
		executor.initialize();
		return executor;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar registrar) {
		// 只使用@Scheduled不加@Async,默认使用一个线程去同步执行任务，各任务之间会阻塞相互影响。
		// taskRegistrar添加不添加线程池无影响，会使用指定的，不必registrar里注册添加。
		// registrar.setScheduler(Executors.newScheduledThreadPool(10));//线程池数量
	}
	
}
