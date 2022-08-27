package com.cyber.jwt.enums;

/**
 * 登录类型
 */

public enum LoginTypeEnum {
    /**
     * 微信登录
     */
    WX(1, "微信登录"),
    WEB(2, "WEB登录"),
    APP(3, "APP登录");


    final int loginType;
    final String loginName;

    LoginTypeEnum(int loginType, String loginName) {
        this.loginType = loginType;
        this.loginName = loginName;
    }

    public int getLoginType() {
        return loginType;
    }

    public String getLoginName() {
        return loginName;
    }
}
