package com.cyber.core.tool;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 集合相关工具类
 *
 * @author cyber
 * @date 2022年7月9日
 */
public class CollectionUtil extends CollectionUtils {


    public static boolean isArray(Object obj) {
        return null != obj && obj.getClass().isArray();
    }

    public static boolean isNotEmpty(@Nullable Collection<?> coll) {
        return !CollectionUtils.isEmpty(coll);
    }

    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !CollectionUtils.isEmpty(map);
    }
}
