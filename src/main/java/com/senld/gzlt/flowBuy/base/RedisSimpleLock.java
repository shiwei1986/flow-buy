package com.senld.gzlt.flowBuy.base;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisSimpleLock implements AutoCloseable {
    public static final Logger log = LoggerFactory.getLogger(RedisSimpleLock.class);
    /**
     * 锁key后缀
     */
    public static final String SUFFIX = "~lock";

    private final StringRedisTemplate redisTemplate;
    /**
     * 锁key
     */
    private final String key;
    /**
     * 超时时间
     */
    private final long timeout;
    /**
     * 时间单位
     */
    private final TimeUnit unit;
    /**
     * 锁状态
     */
    private boolean lock;
    /**
     * 是否自动释放
     */
    private boolean autoUnlock;

    public RedisSimpleLock(StringRedisTemplate redisTemplate,String key, long timeout, TimeUnit unit) {
        this(redisTemplate,key, timeout, unit, true);
    }

    public RedisSimpleLock(StringRedisTemplate redisTemplate,String key, long timeout, TimeUnit unit, boolean autoUnlock) {
    	this.redisTemplate = redisTemplate;
        this.key = key + SUFFIX;
        this.timeout = timeout;
        this.unit = unit;
        this.autoUnlock = autoUnlock;
    }



    public boolean lock() throws Exception {
        // 利用key过期时间来完成超时自动解锁
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, "lock");
        if (res == null) {
//            log.info("Redis lock key:{},timeout:{},timeUnit:{} ==> Result:[{}]", key, timeout, unit, "error");
            throw new Exception();
        } else {
//            log.info("Redis lock key:{},timeout:{},timeUnit:{} ==> Result:[{}]", key, timeout, unit, res);
            this.lock = res;
            return res;
        }
    }

    public void unlock() {
        if (lock) {
            try {
                Object currentValue = redisTemplate.opsForValue().get(key);
                if (currentValue != null) {
                    redisTemplate.opsForValue().getOperations().delete(key);
//                    log.info("Redis unlock key:{} ==> done.", key);
                } else {
//                    log.warn("Redis unlock key:{} ==> Key not exists,skip.", key);
                }
            } catch (Exception e) {
//                log.error("Redis unlock key:{} ==> An error has occurred.", key, e);
            }
        } else {
//            log.info("Redis unlock key:{} ==> skip.", key);
        }
    }

    @Override
    public void close() {
        // 实现AutoCloseable接口close方法，简化try(...)...catch的finally部分，自动调用close方法解锁
        if (autoUnlock) {
            unlock();
        }
    }
}
