package com.cyber.db.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * redis序列化配置
 *
 * @date 2022年5月18日
 * @author cyber
 */
@Configuration
public class RedisConfig {

    /**
     * 配置redis序列化方式
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return RedisTemplate
     * @throws UnknownHostException UnknownHostException
     */
    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // Json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key 序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value的序列化方式（json序列化）
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式（json序列化）
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}