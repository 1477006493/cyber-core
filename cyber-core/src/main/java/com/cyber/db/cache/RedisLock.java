package com.cyber.db.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 使用RedisTemplate实现分布式锁
 * @author cyber
 */
@Component
public class RedisLock {

    // 锁名称 prefix expire 锁对象
    public static final String LOCK_PREFIX = "RedisLock";
    // 加锁失效时间，毫秒
    public static final int LOCK_EXPIRE = 2000;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 加锁操作
     *
     * @param key 锁的名称
     * @return 尝试上锁结果
     */
    private Boolean setLock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, LOCK_PREFIX, LOCK_EXPIRE, TimeUnit.MILLISECONDS);
    }

    /**
     * 释放锁
     *
     * @return true or false
     */
    public Boolean deleteLock(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 记录某个键的插入次数
     * 时间单位为分钟
     *
     * @return 记录某个键的插入次数
     */
    public Long increment(String key, Integer timeout) {
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expire(key, timeout, TimeUnit.MINUTES); // 设置过期时间 单位为分钟
        return increment;

    }

    /**
     * 采用轮询的方式去加锁
     * 尝试去获得锁
     *
     * @param key key
     * @return 超过时间返回false
     */
    public Boolean tryLock(String key) {
        long start = System.currentTimeMillis();
        while(true){
            //超时返回false
            if (System.currentTimeMillis() - start > LOCK_EXPIRE){
                return false;
            }
            if (setLock(key)) {
                return true;
            }
        }
    }


}
