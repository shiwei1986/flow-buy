package com.senld.gzlt.flowBuy.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 基础控制器
 * 
 * @author system
 * @Date 2018年4月9日
 */
@RestController
public class BaseController {
	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * 缓存服务
	 */
	@Autowired(required = false)
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 封装分页查询参数
	 * 
	 * @param params
	 * @return
	 */
	protected <T> Page<T> buildPage(Map<String, Object> params) {
		Integer currentStr = (Integer) params.get("current");
		Integer sizeStr = (Integer) params.get("size");
		int current = 1;
		int size = 10;
		if (currentStr != null) {
			current = currentStr.intValue();
		}
		if (sizeStr != null) {
			size = sizeStr.intValue();
		}
		Page<T> page = new Page<T>(current, size);
		String sidx = (String) params.get("sidx");
		String order = (String) params.get("order");
		if (StringUtils.isNotBlank(sidx)) {
			// page.setOrderByField(SQLFilter.sqlInject("`" + sidx + "`"));
		}
		if (StringUtils.isNotBlank(order)) {
			// page.setAsc("asc".equalsIgnoreCase(order) ? true : false);
			params.put("order", order.toUpperCase());
		}
		sidx = camelToUnderline(sidx);
		params.put("sidx", sidx);
		if ("normal".equalsIgnoreCase(order)) {
			params.put("sidx", "");
		}
		return page;
	}

	/**
	 * 驼峰法转下划线
	 * 
	 * @param line
	 *            源字符串
	 * @return 转换后的字符串
	 */
	private static String camelToUnderline(String line) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		String underline = "_";
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(underline);
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 根据redisKey和key获取数据
	 * 
	 * @param redisKey
	 * @param key
	 * @return
	 */
	protected Object getRedisDataForObj(String redisKey, String key, String valueKey) {
		Map<String, Object> map = getRedisDataForMap(redisKey, key);
		return map.get(valueKey);
	}

	/**
	 * 根据redisKey获取数据集合
	 * 
	 * @param redisKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getRedisDataForMap(String redisKey, String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = redisTemplate.opsForHash().get(redisKey, key);
		if (obj != null) {
			map = (Map<String, Object>) obj;
		}
		return map;
	}
	/**
	 * 根据redisKey获取数据集合
	 * 
	 * @param redisKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getRedisDataForMap(String redisKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = redisTemplate.opsForValue().get(redisKey);
		if (obj != null) {
			map = (Map<String, Object>) obj;
		}
		return map;
	}
	/**
	 * 根据redisKey获取数据集合
	 * 
	 * @param redisKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<Long> getRedisDataForList(String redisKey, String key) {
		List<Long> list = new ArrayList<Long>();
		Object obj = redisTemplate.opsForHash().get(redisKey, key);
		if (obj != null) {
			list = (List<Long>) obj;
		}
		return list;
	}


	/**
	 * 获取用户类型，并重置失效时间
	 * 
	 * @param token
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Integer getUserStyle(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		Object obj = redisTemplate.opsForHash().get("User:" + userId, "UserInfo");
		if (obj == null || "".equals(obj)) {
			return null;
		}
		Integer userStyle = (Integer) ((Map) obj).get("userStyle");
		return userStyle;
	}

}
