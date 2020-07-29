package com.senld.gzlt.flowBuy.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @author 作者 zhuhechao
 * 
 * @version 创建时间：2019年9月27日 上午10:55:48
 * 
 *          类说明 :系统参数配置类
 * 
 */
@Data
@Accessors(chain = true)
@Component("systemConfig")
public class SystemConfig {
	
	@Value("${gzapi.xv.key}")
	private String key;

	@Value("${gzapi.xv.secret}")
	private String secret;

	@Value("${gzapi.xv.productId}")
	private String productId;

	@Value("${gzapi.xv.prodId}")
	private String prodId;
	
	@Value("${gzapi.xv.prefixUrl}")
	private String prefixUrl;
	
	@Value("${senld.auth.username}")
	private String username;
	
	@Value("${senld.auth.password}")
	private String password;
	
	@Value("${senld.auth.secretKey}")
	private String secretKey;

	/**
	 * 初始化时执行的方法
	 */
	@PostConstruct
	public void init() throws Exception {
	}
}
