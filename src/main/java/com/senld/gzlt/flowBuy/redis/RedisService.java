package com.senld.gzlt.flowBuy.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 封装RedisTemplate的RedisUtil类
 * @author Administrator
 *
 */
@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	/*公共方法*/
	
	/**
	 * 
	 * @param key
	 * @param timeout
	 * @return
	 */
	public boolean expire(String key,long timeout) {
		boolean flag=false;
		try {
			if(timeout>0) {
				redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据key值 获取过期时间
	 * @param key
	 * @return 返回0代表永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key,TimeUnit.SECONDS);
	}
	
	/**
	 * 判断key值是否存在
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 模糊获取key值
	 * @param key
	 * @return
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}
	
	/**
	 * 删除缓存
	 * @param keys
	 */
	@SuppressWarnings("unchecked")
	public void del(String ... keys) {
		if(keys != null && keys.length > 0) {
			if(keys.length==1) {
				redisTemplate.delete(keys[0]);
			}else {
				redisTemplate.delete(CollectionUtils.arrayToList(keys));
			}
		}
	}
	
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return key==null?null:redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 加入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key,Object value) {
		boolean flag=false;
		try {
			redisTemplate.opsForValue().set(key, value);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 加入缓存并设置过期时间
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean set(String key,Object value,long timeout) {
		boolean flag=false;
		try {
			if(timeout>0) {
				redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
			}else {
				set(key,value);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 递增
	 * @param key
	 * @param delta
	 * @return
	 */
	public long incr(String key,long delta) {
		if(delta<0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}
	
	/**
	 * 递减
	 * @param key
	 * @param delta
	 * @return
	 */
	public long decr(String key,long delta) {
		if(delta<0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}
	
	/**
	 * 获取Hash
	 * @param key
	 * @param item
	 * @return
	 */
	public Object hget(String key,String item) {
		return redisTemplate.opsForHash().get(key, item);
	}
	
	/**
	 * 获取Hash
	 * @param key
	 * @return
	 */
	public Object hget(String key) {
		return redisTemplate.opsForHash().values(key);
	}
	
	/**
	 * 获取Hash key对应的所有键值
	 * @param key
	 * @return
	 */
	public Map<Object,Object> hmget(String key){
		return redisTemplate.opsForHash().entries(key);
	}
	
	/**
	 * 加入Hash
	 * @param key
	 * @param map
	 * @return
	 */
	public boolean hmset(String key,Map<String,Object> map) {
		boolean flag=false;
		try {
			redisTemplate.opsForHash().putAll(key,map);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 加入Hash，并设置过期时间
	 * @param key
	 * @param map
	 * @param timeout
	 * @return
	 */
	public boolean hmset(String key,Map<String,Object> map,long timeout) {
		boolean flag=false;
		try {
			redisTemplate.opsForHash().putAll(key,map);
			if(timeout>0) {
				expire(key, timeout);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 向一张hash表中放入数据，如果不存在则创建
	 * @param key
	 * @param item
	 * @param value
	 * @return
	 */
	public boolean hset(String key,String item,Object value) {
		boolean flag=false;
		try {
			redisTemplate.opsForHash().put(key, item, value);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 向一张hash表中放入数据，如果不存在则创建，并设置过期时间
	 * @param key
	 * @param item
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean hset(String key,String item,Object value,long timeout) {
		boolean flag=false;
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if(timeout>0) {
				expire(key,timeout);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 删除hash表中的值
	 * @param key
	 * @param item
	 */
	public void hdel(String key,Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}
	
	/**
	 * 判断hash表中是否有该项的值
	 * @param key
	 * @param item
	 * @return
	 */
	public boolean hHashKey(String key,String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}
	
	/**
	 * hash递增
	 * @param key
	 * @param item
	 * @param delta
	 * @return
	 */
	public double hincr(String key,String item,double delta) {
		return redisTemplate.opsForHash().increment(key, item, delta);
	}
	
	/**
	 * 递减
	 * @param key
	 * @param item
	 * @param delta
	 * @return
	 */
	public double hdecr(String key,String item,double delta) {
		return redisTemplate.opsForHash().increment(key, item, -delta);
	}
	
	/**
	 * 根据key获取Set中的所有值
	 * @param key
	 * @return
	 */
	public Set<Object> sGet(String key){
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据value从一个set中查询，是否存在
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sHashKey(String key,Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}
	
	/**
	 * 将数据放入set缓存
	 * @param key
	 * @param values
	 * @return
	 */
	public long sSet(String key,Object... values) {
		return redisTemplate.opsForSet().add(key, values);
	}
	
	/**
	 * 将数据放入set缓存，并设置过期时间
	 * @param key
	 * @param timeout
	 * @param values
	 * @return
	 */
	public long sSet(String key,long timeout,Object... values) {
		long count = redisTemplate.opsForSet().add(key, values);
		if(timeout>0) {
			expire(key,timeout);
		}
		return count;
	}
	
	/**
	 * 获取set缓存长度
	 * @param key
	 * @return
	 */
	public long sGetSetSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}
	
	/**
	 * 移除
	 * @param key
	 * @param values
	 * @return
	 */
	public long setRemove(String key,Object... values) {
		long count = redisTemplate.opsForSet().remove(key, values);
		return count;
	}
	
	/**
	 * 获取list缓存内容
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> lGet(String key,long start,long end){
		return redisTemplate.opsForList().range(key, start, end);
	}
	
	/**
	 * 获取list缓存长度
	 * @param key
	 * @return
	 */
	public long lGetListSize(String key) {
		return redisTemplate.opsForList().size(key);
	}
	
	/**
	 * 通过索引获取list中的值
	 * @param key
	 * @param index
	 * @return
	 */
	public Object lGetIndex(String key,long index) {
		return redisTemplate.opsForList().index(key, index);
	}
	
	/**
	 * 将list放入缓存:先进后出
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean lSet(String key,Object value) {
		boolean flag=false;
		try {
			redisTemplate.opsForList().rightPush(key, value);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 将list放入缓存，并设置过期时间
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean lSet(String key,List<Object> value,long timeout) {
		boolean flag=false;
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if(timeout>0) {
				expire(key,timeout);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据索引修改list中的某条数据
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public boolean lUpdateIndex(String key,long index,Object value) {
		boolean flag=false;
		try {
			redisTemplate.opsForList().set(key, index, value);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 移除
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public long lRemove(String key,long count,Object value) {
		return redisTemplate.opsForList().remove(key, count, value);
	}
}
