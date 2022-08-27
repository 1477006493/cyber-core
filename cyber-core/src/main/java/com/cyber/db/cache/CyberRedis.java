package com.cyber.db.cache;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author cyber
 * @date 2022年6月26日
 */
@Slf4j
@Component
public class CyberRedis {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认过期 时长为 30分钟
     */
    private final Duration defaultTime = Duration.ofMinutes(30L);


    /**
     * 设置一个key
     *
     * @param value    值
     * @param cacheKey key
     * @return true | false
     */
    public Boolean set(CacheKey cacheKey, Object value) {
        Duration expire = cacheKey.getExpire();
        if (expire == null) {
            return setRedisItem(cacheKey.getKey(), value);
        }
        return setRedisItem(cacheKey.getKey(), value, expire);
    }


    /**
     * 设置redis缓存
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时长
     * @return true | false
     */
    private Boolean setRedisItem(String key, Object value, Duration expire) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, expire);
            return true;
        } catch (Exception e) {
            log.error("redis set error", e);
            return false;
        }
    }

    /**
     * 设置redis缓存,使用默认时间
     *
     * @param key   key
     * @param value value
     * @return true | false
     */
    private Boolean setRedisItem(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, this.defaultTime);
            return true;
        } catch (Exception e) {
            log.error("redis set error", e);
            return false;
        }
    }

    /**
     * 获取值
     *
     * @param key key
     * @return String
     */
    public Object get(String key) {
        return StringUtils.isEmpty(key) ? null : redisTemplate.opsForValue().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getObj(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Object obj = redisTemplate.opsForValue().get(key);
        if (obj == null) {
            return null;
        }
        try {
            return (T) obj;
        } catch (Exception e) {
            log.error("数据操作异常!", e);
            throw new RuntimeException("数据操作异常！");
        }
    }

    /**
     * 删除某个key
     *
     * @param key key
     * @return true | false
     */
    public Boolean del(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            log.error("redis del error \n{}", e.getMessage());
            return false;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        try {
            Boolean hasKey = redisTemplate.hasKey(key);
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis hasKey error \n{}", e.getMessage());
            return false;
        }
    }


    /**
     * 记录某个键的插入次数,默认步长为1
     *
     * @return 记录过的次数
     */
    public Long increment(CacheKey cacheKey) {
        return increment(cacheKey, 1);
    }

    /**
     * 记录某个键的插入次数
     *
     * @return 记录过的次数
     */
    public Long increment(CacheKey cacheKey, Integer delta) {
        Duration expire = cacheKey.getExpire();
        if (expire == null) {
            return getIncrementCount(cacheKey.getKey(), this.defaultTime, delta);
        }
        return getIncrementCount(cacheKey.getKey(), expire, delta);
    }

    public Long getIncrementCount(String key, Duration timeout, Integer delta) {
        Long increment = redisTemplate.opsForValue().increment(key, delta);
        redisTemplate.expire(key, timeout);
        return increment;
    }


    /**
     * 设置一个key
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间 毫秒
     * @return true | false
     */
    public Boolean setHash(String key, String item, Object value, Long timeout) {
        try {
            redisTemplate.opsForHash().put(key, item, JSON.toJSONString(value));
            redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            log.error("redis setHash error \n{}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取值
     *
     * @param key key
     * @return String
     */
    public String getHash(String key, String item) {
        Object obj = redisTemplate.opsForHash().get(key, item);
        return obj == null ? null : JSON.toJSONString(obj);
    }

    public Boolean delHash(String key, Object... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            log.error("redis delHash error \n{}", e.getMessage());
            return false;
        }
    }
}
