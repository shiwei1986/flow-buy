package com.senld.gzlt.flowBuy.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * 业务实现类的基础接口
 * 
 * @author system
 * @Date 2018年4月10日
 * @param <M>
 * @param <T>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	/**
	 * 缓存服务
	 */
	@Autowired(required = false)
	private RedisTemplate<String, Object> redisTemplate;

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
	 * 根据redisKey获取item数据集合 适合redisTemplate.opsForHash().put(key, item,
	 * value)存放的数据
	 * 
	 * @param redisKey
	 * @param item
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getRedisDataForMap(String redisKey, String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = redisTemplate.opsForHash().get(redisKey, item);
		if (obj != null) {
			map = (Map<String, Object>) obj;
		}
		return map;
	}

	/**
	 * 
	 * @param redisKey
	 * @param item
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> getRedisDataForList(String redisKey, String item) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Object obj = redisTemplate.opsForHash().get(redisKey, item);
		if (obj != null) {
			list = (List<Map<String, Object>>) obj;
		}
		return list;
	}


	/**
	 * 获取Redis的数据，并把key值转换成List数组 得到的数据如： [钛金色,棕色]
	 */
	@SuppressWarnings("unchecked")
	protected List<Object> getRedisDataForList(String redisKey) {
		List<Object> list = new ArrayList<Object>();
		Object obj = redisTemplate.opsForValue().get(redisKey);
		if (obj != null) {// 如果用户Token中有key数据
			Map<String, Object> redisDataMap = (Map<String, Object>) obj;
			list = new ArrayList<Object>(redisDataMap.keySet());
		}
		return list;
	}

	/**
	 * 根据redisKey获取数据集合
	 * 
	 * @param redisKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<Long> getRedisDataForListLong(String redisKey, String key) {
		List<Long> list = new ArrayList<Long>();
		Object obj = redisTemplate.opsForHash().get(redisKey, key);
		if (obj != null) {
			list = (List<Long>) obj;
		}
		return list;
	}

	/**
	 * 获取数据字典的数据，并转换成KEY-VALUE形式
	 * [{keyName=key,valueName=value},{keyName=key,valueName=value}] 得到的数据如：
	 * [{name=钛金色, id=16}, {name=灰色, id=6}]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<Map<String, Object>> getRedisDataForList(String redisKey, String keyName, String valueName) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Object obj = redisTemplate.opsForValue().get(redisKey);
		if (obj != null) {// 如果用户Token中有key数据
			Map<String, Object> redisDataMap = (Map<String, Object>) obj;
			if (redisDataMap != null && !redisDataMap.isEmpty()) {
				Map<String, Object> map = null;
				Iterator it = redisDataMap.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					map = new HashMap<String, Object>();
					map.put(keyName, key);
					map.put(valueName, redisDataMap.get(key));
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * 获取Redis数据，以值List形式返回
	 * 
	 * @param redisKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> getRedisDataList(String redisKey) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Object obj = redisTemplate.opsForHash().values(redisKey);
		if (obj != null) {// 如果用户Token中有key数据
			list = (List<Map<String, Object>>) obj;
		}
		return list;
	}

	/**
	 * 获取Redis数据，以键值对形式返回
	 * 
	 * @param redisKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getRedisDataMap(String redisKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = redisTemplate.opsForHash().entries(redisKey);
		if (obj != null) {// 如果用户Token中有key数据
			map = (Map<String, Object>) obj;
		}
		return map;
	}

	
}
