package com.cyber.email.tool;

import java.io.File;

/**
 * @author cyber
 * @Description 邮箱相关自动配置
 * @date 2022年8月15日
 * @Version 1.0
 */
public interface CyberMailService {

    /**
     * 简单邮件发送
     * @param emailBO  发送信息
     */
    void send(EmailBO emailBO);

    /**
     * 携带附件的邮件发送
     * @param emailBO emailBO
     * @param file 文件
     */
    void send(EmailBO emailBO, File[] file);
}
