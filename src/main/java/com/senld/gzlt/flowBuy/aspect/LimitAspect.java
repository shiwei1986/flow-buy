//package com.senld.gzlt.flowBuy.aspect;
//
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.senld.gzlt.flowBuy.base.HttpCode;
//import com.senld.gzlt.flowBuy.config.LimitKey;
//import com.senld.gzlt.flowBuy.config.SystemConfig;
//import com.senld.gzlt.flowBuy.exception.SenldException;
//import com.senld.gzlt.flowBuy.redis.RedisService;
//import com.senld.gzlt.flowBuy.utils.DataUtils;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 
// * @Author wusw
// * @Date 2020年6月12日
// * @Description: 切面类，进行接口鉴权以及防止重复提交
// * @Version: 1.0
// *
// */
//@Component
//@Order
//@Aspect
//@Slf4j
//public class LimitAspect {
//
//	@Autowired
//	private RedisService redisService;
//
//	@Autowired
//	private SystemConfig systemConfig;
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(LimitAspect.class);
//
//	@Pointcut("@annotation(limitKey)")
//	public void limit(LimitKey limitKey) {
//	}
//
//	@Pointcut("execution(public * com.senld.gzlt.flowBuy.controller..*.*(..))") // 切入点描述，这个是controller包的切入点
//	public void controllerLog() {
//	}// 签名，可以理解成这个切入点的一个名称
//
//	@Pointcut("execution(public * com.senld.gzlt.flowBuy.service.impl..*.*(..))") // 切入点描述，这个是uiController包的切入点
//	public void uiControllerLog() {
//	}
//
//	@Before("controllerLog()") // 在切入点的方法run之前要干的
//	public void logBeforeController(JoinPoint joinPoint) {
//		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//		// 记录下请求内容
//		log.info("请求地址 : " + request.getRequestURL().toString());
//		log.info("请求方式 : " + request.getMethod());
//		log.info("请求IP : " + request.getRemoteAddr());
//		log.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
//		log.info("请求方法名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//
//	}
//
//	@AfterThrowing(pointcut = "controllerLog()", throwing = "e")
//	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
//		String methodName = joinPoint.getSignature().getName();
//		List<Object> args = Arrays.asList(joinPoint.getArgs());
//		log.info("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + e);
//
//	}
//
//	@Around("limit(limitKey)")
//	public Object aroundLog(ProceedingJoinPoint joinpoint, LimitKey limitKey) throws Throwable {
//		long startTime = System.currentTimeMillis();
//		int frequency = limitKey.frequency();
//		long timeout = limitKey.timeout();
//		String methodName = limitKey.methodName();
//		boolean needchecked = limitKey.needChecked();
//		String url = limitKey.url();
//		// 入参
//		Object[] args = joinpoint.getArgs();
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		if (needchecked) {
//			// 判断是否鉴权
//			String username = request.getHeader("X-V-Username");
//			if (StringUtils.isEmpty(username)) {
//				LOGGER.error("请求头用户名不能为空！");
//				throw new SenldException("请求头用户名不能为空！", HttpCode.BAD_REQUEST.value());
//			}
//			// 判断是否鉴权
//			String password = request.getHeader("X-V-Password");
//			if (StringUtils.isEmpty(password)) {
//				LOGGER.error("请求头用户密码不能为空！");
//				throw new SenldException("请求头用户密码不能为空！", HttpCode.BAD_REQUEST.value());
//			}
//			// 判断是否鉴权
//			String secretKey = request.getHeader("X-V-SecretKey");
//			if (StringUtils.isEmpty(secretKey)) {
//				LOGGER.error("鉴权秘钥不能为空！");
//				throw new SenldException("鉴权秘钥不能为空！", HttpCode.BAD_REQUEST.value());
//			}
//			String sysUsername = systemConfig.getUsername();
//			if (!sysUsername.equals(username)) {
//				LOGGER.error("请求头用户名错误！");
//				throw new SenldException("请求头用户名错误！", HttpCode.BAD_REQUEST.value());
//			}
//			String sysPassword = DataUtils.md5(systemConfig.getPassword());
//			if (!sysPassword.equals(password)) {
//				LOGGER.error("请求头用户密码错误！");
//				throw new SenldException("请求头用户密码错误！", HttpCode.BAD_REQUEST.value());
//			}
//			String sysSecretKey = DataUtils.base64(systemConfig.getSecretKey());
//			if (!sysSecretKey.equals(secretKey)) {
//				LOGGER.error("请求头秘钥错误！");
//				throw new SenldException("请求头秘钥错误！", HttpCode.BAD_REQUEST.value());
//			}
//		}
//		StringBuffer sb = new StringBuffer();
//		sb.append(url).append("/_").append(methodName).append("_").append(Arrays.toString(args)).append("_").append("_key");
//		String key = sb.toString();
//		Integer count = (Integer) redisService.get(key);
//		if (null == count || -1 == count) {
//			redisService.set(key, 1, timeout);
//		} else {
//			redisService.incr(key, 1);
//			if (count >= frequency) {
//				throw new SenldException("访问过于频繁，请稍后再试", HttpCode.MULTI_STATUS.value());
//			}
//		}
//		log.info("耗时 : " + (System.currentTimeMillis() - startTime));
//		return joinpoint.proceed();
//	}
//
//}
