package com.cyber.core.tool;

/**
 * 系统参数工具类
 *
 * @author cyber
 */
public class ParamUtil {

    /**
     * 1=a;2=b;3=c;
     * 当key为1时返回a
     *
     * @param paramValue paramValue
     * @param key        key
     * @return 根据key获取value
     */
    public static String getValue(String paramValue, String key) {
        String[] split = paramValue.trim().split(";");
        for (String s : split) {
            String[] strings = s.split("=");
            if (strings[0].equals(key)) {
                return strings[1];
            }
        }
        return "";
    }
}
