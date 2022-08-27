package com.cyber.db.auto;


import com.cyber.db.cache.CyberRedis;
import com.cyber.db.cache.RedisLock;
import com.cyber.db.config.RedisConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * spring自动装配
 *
 * @author cyber
 * @date 2022年6月24日
 */
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnWebApplication
@Import({CyberRedis.class, RedisConfig.class})
public class EnableRedisAutoConfiguration {

    public EnableRedisAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate() {
        return new RedisTemplate<>();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisConfig redisConfig() {
        return new RedisConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public CyberRedis cyberRedis() {
        return new CyberRedis();
    }

    public RedisLock cyberRedisLock() {
        return new RedisLock();
    }

}
