package com.cyber.core.tool;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 描述：日期工具类
 */
public class DateUtil extends LocalDateTimeUtil {

    /**
     * 获取距离0点的毫秒
     * @param now 现在的时间
     * @return 毫秒
     */
    public static Long getZeroTime(LocalDateTime now) {
        LocalDateTime localDateTime = LocalDate.now().plusDays(1).atStartOfDay();
        return Duration.between(LocalDate.now().plusDays(1).atStartOfDay(), now).toMillis();
    }

}
