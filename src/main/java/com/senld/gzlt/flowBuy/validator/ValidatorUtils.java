package com.senld.gzlt.flowBuy.validator;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.senld.gzlt.flowBuy.exception.ValidateException;

/**
 * 基于javax.validation实体格式验证
 * 参数校验：spring-boot-starter-web包里面有hibernate-validator包，不需要引用hibernate validator依赖
 * @author zhuhechao
 * @Date 2018年10月19日
 */
public class ValidatorUtils {
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	/**
	 * 基于javax.validation实体格式验证
	 * 
	 * @param t
	 * @param groups
	 * @return
	 */
	public static <T> String validateEntity(T t, Class<?>... groups) {
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> set = validator.validate(t, groups);
		StringBuffer messages = new StringBuffer();
		if (set != null && set.size() > 0) {
			int i = 0;
			Iterator<ConstraintViolation<T>> iterator = set.iterator();
			while (iterator.hasNext()) {
				ConstraintViolation<T> c = iterator.next();
				messages.append((++i) + "、" + c.getMessage() + ";");
			}
			throw new ValidateException(messages.toString());
		}
		return messages.toString();
	}
	/**
	 * 校验参数
	 * @param t
	 * @return
	 */
	public static <T> String validateEntity(T t) {
		StringBuffer messages = new StringBuffer();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> set = validator.validate(t);
		int i=1;
		for (ConstraintViolation<T> constraintViolation : set){
			if(i==1) {
				messages.append("校验不通过:"); 
			}
			messages.append(i++ +"、" + constraintViolation.getMessage()+";"); 
		}
		return messages.toString();
	}
}
