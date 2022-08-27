package com.cyber.db.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * Redis 缓存key对象
 *
 * @author cyber
 * @date 2022年6月26日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CacheKey {
    /**
     * 存储的key对象
     */
    private String key;
    /**
     * 过期时间
     */
    @Nullable
    private Duration expire;
}
