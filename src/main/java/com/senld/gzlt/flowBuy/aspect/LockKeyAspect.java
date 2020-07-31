package com.senld.gzlt.flowBuy.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.senld.gzlt.flowBuy.config.CacheLock;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class LockKeyAspect {

	@Autowired
	public LockKeyAspect(StringRedisTemplate lockRedisTemplate, CacheKeyGenerator cacheKeyGenerator) {
		this.lockRedisTemplate = lockRedisTemplate;
		this.cacheKeyGenerator = cacheKeyGenerator;
	}

	private final StringRedisTemplate lockRedisTemplate;
	private final CacheKeyGenerator cacheKeyGenerator;
	
	@Pointcut("execution(public * com.senld.gzlt.flowBuy.controller..*.*(..))") // 切入点描述，这个是controller包的切入点
	public void controllerLog() {
	}// 签名，可以理解成这个切入点的一个名称

	
	@Before("controllerLog()") // 在切入点的方法run之前要干的
	public void logBeforeController(JoinPoint joinPoint) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		// 记录下请求内容
//		log.info("请求地址 : " + request.getRequestURL().toString());
//		log.info("请求方式 : " + request.getMethod());
//		log.info("请求IP : " + request.getRemoteAddr());
//		log.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
//		log.info("请求方法名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

	}

	@Around("execution(public * *(..)) && @annotation(com.senld.gzlt.flowBuy.config.CacheLock)")
	public Object interceptor(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		CacheLock lock = method.getAnnotation(CacheLock.class);
		if (StringUtils.isEmpty(lock.prefix())) {
			throw new RuntimeException("lock key can't be null...");
		}
		final String lockKey = cacheKeyGenerator.getLockKey(pjp);
		try {
			// key不存在才能设置成功
			final Boolean success = lockRedisTemplate.opsForValue().setIfAbsent(lockKey, "");
			if (success) {
				lockRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
			} else {
				// 按理来说 我们应该抛出一个自定义的 CacheLockException 异常;
				throw new RuntimeException("请勿重复请求");
			}
			try {
				return pjp.proceed();
			} catch (Throwable throwable) {
				throw new RuntimeException("系统异常");
			}
		} finally {
			// 如果演示的话需要注释该代码;实际应该放开
			// lockRedisTemplate.delete(lockKey);
		}
	}
}
