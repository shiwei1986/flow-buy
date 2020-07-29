package com.senld.gzlt.flowBuy.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitKey {
	// 方法名称
	String methodName() default "";

	// 访问次数
	int frequency() default 10;

	boolean needChecked() default true;

	// 方法请求地址
	String url() default "";

	// 过期时间(单位秒)
	long timeout() default 5l;
}
