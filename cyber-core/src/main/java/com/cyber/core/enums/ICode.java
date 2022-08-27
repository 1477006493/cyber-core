package com.cyber.core.enums;

import java.io.Serializable;

/**
 * @author cyber
 * @Description TODO
 * @date 2022/5/26 14:37
 * @Version 1.0
 */
public interface ICode  extends Serializable {
    /**
     * 获取对应code的消息
     * @return String
     */
    String getMessage();

    /**
     * 获取响应代码值
     * @return int
     */
    int getCode();
}
