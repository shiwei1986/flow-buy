package com.senld.gzlt.flowBuy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * 
 * @author system
 *
 */
@Configuration(value = "SwaggerConfig")
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * 定义swagger的版本信息 新版本用ApiInfoBuilder，旧版本用ApiInfo
	 * 
	 * @return
	 */
	@Bean
	public Docket restfulApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.senld.gzlt.flowBuy"))
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())//
				.build();// 添加全局参数

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("流量购买服务api").description("流量购买服务接口文档说明")
				// .contact(new Contact("vker", "", "6492178@gmail.com"))
				.version("1.0").build();
	}

	@SuppressWarnings("deprecation")
	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(null, "list", "alpha", "schema", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}
}
