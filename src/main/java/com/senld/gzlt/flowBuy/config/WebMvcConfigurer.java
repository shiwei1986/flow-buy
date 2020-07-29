package com.senld.gzlt.flowBuy.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration(value="WebMvcConfigurer")
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * 格式转换
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jacksonConverter = null;
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
				ObjectMapper mapper = jacksonConverter.getObjectMapper();
				SimpleModule module = new SimpleModule();
				// 将long类型转为字符串
				module.addSerializer(Long.class, ToStringSerializer.instance);
				module.addSerializer(Long.TYPE, ToStringSerializer.instance);
				mapper.registerModule(module);
				mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);// 不显示为null的字段
				mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);// map类型不显示null的value
				jacksonConverter.setObjectMapper(mapper);
//				converters.add(0, jacksonConverter);// 放到第一个
				break;
			}
		}
	}
	
	/**
	 * 放开静态资源的拦截
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars*").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	/**
	 * 跨域
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
				.allowCredentials(false).maxAge(3600);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		super.addInterceptors(registry);
	}

}
