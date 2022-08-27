package com.cyber.log.enums;

/**
 * 日志类型枚举
 *
 * @author cyber
 * @date 2022年7月12日
 */
public enum OptTypeEnum {
    /**
     * 日志类型枚举
     */
    OPT(1, "业务操作"),
    ERROR(2, "失败操作");

    final int code;
    final String name;


    public int getCode() {
        return this.code;
    }


    public String getMessage() {
        return this.name;
    }

    private OptTypeEnum(final int code, final String name) {
        this.code = code;
        this.name = name;
    }
}
