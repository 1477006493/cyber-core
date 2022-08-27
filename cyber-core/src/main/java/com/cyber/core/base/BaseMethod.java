package com.cyber.core.base;

import java.io.Serializable;

/**
 * @author cyber
 * @date 2022/5/13
 * @Version 1.0
 */
public class BaseMethod implements Serializable {
    /**
     * 校验id是否有效,无效返回false，有效返回true
     *
     * @return boolean
     * @author tyj
     */
    public static Boolean inInvalidId(Long id) {
        return id != null && id > 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return null或空串都返回 true
     * @author smy
     */
    public static Boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 判断是否不为空，只有当str不为空且不为空串时返回true
     *
     * @param str 需要判断的字符串
     * @return 不为空串时返回true
     */
    public static Boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }


}
