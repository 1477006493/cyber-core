package com.cyber.email.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cyber
 * @Description 邮箱相关自动配置
 * @date 2022年8月15日
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailBO implements Serializable {
    /**
     * 发送方
     */
    public String from;

    /**
     * 接收方
     */
    public String to;

    /**
     * 抄送
     */
    public String cc;

    /**
     * 标题
     */
    public String subject;

    /**
     * 内容
     */
    public String content;
}
