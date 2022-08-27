package com.cyber.email.config;

import com.cyber.core.tool.StringUtil;
import com.cyber.email.properties.EmailProperties;
import com.cyber.email.tool.CyberMailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author cyber
 * @Description 邮箱相关自动配置
 * @date 2022年8月15日
 * @Version 1.0
 */
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnWebApplication
@EnableConfigurationProperties({EmailProperties.class})
public class EnableEmailConfig {
    @Autowired
    private EmailProperties emailProperties;
    /**
     * 服务器是否要身份认证
     */
    private final String AUTH = "mail.smtp.auth";
    private final String PORT = "port";
    private final String TIME_OUT = "mail.smtp.timeout";

    @Bean
    @ConditionalOnMissingBean
    public CyberMailServiceImpl mailService(){
        return new CyberMailServiceImpl();
    }

    @Bean(name = "javaMailSender")
    @ConditionalOnMissingBean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        if (!checkParam()){
            return javaMailSender;
        }
        javaMailSender.setHost(emailProperties.getHost());
        javaMailSender.setUsername(emailProperties.getUsername());
        javaMailSender.setPassword(emailProperties.getPassword());
        Properties properties = new Properties();
        properties.put(AUTH, emailProperties.getAuth());
        properties.put(PORT, emailProperties.getPort());
        properties.put(TIME_OUT, emailProperties.getTimeout());
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    private boolean checkParam() {
        return StringUtil.isNotBlank(emailProperties.getHost())
                && StringUtil.isNotBlank(emailProperties.getUsername())
                && StringUtil.isNotBlank(emailProperties.getPassword());
    }

}
