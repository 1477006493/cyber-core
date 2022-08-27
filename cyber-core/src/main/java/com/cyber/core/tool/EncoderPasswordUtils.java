package com.cyber.core.tool;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 *
 * @author cyber
 * @date 2022年7月9日
 */
public class EncoderPasswordUtils {

    private static final ThreadLocal<BCryptPasswordEncoder> ENCODER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 加密，生成BCryptPasswordEncoder密码
     *
     * @param rawPassword 真实密码
     * @return 加密字符串
     */
    public static String encryptPassword(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = getEncoder();
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密字符串
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = getEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 从ThreadLocal中获取 BCryptPasswordEncoder 对象
     *
     * @return BCryptPasswordEncoder
     */
    private static BCryptPasswordEncoder getEncoder() {
        BCryptPasswordEncoder passwordEncoder = ENCODER_THREAD_LOCAL.get();
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
            ENCODER_THREAD_LOCAL.set(passwordEncoder);
        }
        return passwordEncoder;
    }

    /**
     * 移除encoderThreadLocal里的对象
     */
    public static void remove() {
        ENCODER_THREAD_LOCAL.remove();
    }

}