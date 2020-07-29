package com.senld.gzlt.flowBuy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * base服务启动类
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.senld.gzlt.flowBuy"})
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients // feign开启远程调用
@MapperScan("com.senld.gzlt.flowBuy.mapper")
@EnableAsync
@EnableScheduling
@EnableSwagger2
@EnableTransactionManagement
public class FlowBuyApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlowBuyApplication.class, args);
	}
}
