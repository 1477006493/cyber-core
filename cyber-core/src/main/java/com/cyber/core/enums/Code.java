package com.cyber.core.enums;

/**
 * @author cyber
 * @Description HTTP请求响应的状态码
 * @date 2022/5/26
 * @Version 1.0
 */
public enum Code implements ICode {
    /**
     * http状态码枚举所有状态码注解
     */
    OK(2000, "操作成功"),
    ERROR(2001, "请求失败"),
    FAILURE(2002, "业务异常"),
    UN_AUTHORIZED(2003, "请求未授权"),
    FILE_MAX_SIZE_LIMIT(2004, "超过最大文件上传限制"),
    FILE_UPLOAD_FAIL(2005, "文件上传失败"),
    LOGIN_ERROR(2006, "账号或密码错误！"),
    MSG_NOT_READABLE(2007, "消息不能读取"),
    BUSY(2008, "系统繁忙"),
    ROLE_LIST_MISS(2009, "用户角色丢失"),
    REQ_REJECT(403, "请求被拒绝"),
    INTERNAL_SERVER_ERROR(500, "服务器异常"),
    PARAM_MISS(3001, "缺少必要的请求参数"),
    SERVER_PARAM_MISS(4001, "未配置必要参数"),
    CAPTCHA_ERROR(3004, "验证码错误"),

    ACCESS_LIMIT(3003, "令牌已过期，请重新登录"),
    LOGIN_LIMIT(3005, "超过最大登录限制"),
    OPT_LIMIT(3006, "超过最大操作次数"),
    PARAM_INVALID(7001, "非法参数");

    final int code;
    final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    private Code(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
