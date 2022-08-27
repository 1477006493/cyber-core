package com.cyber.core.config;

import com.cyber.core.aspect.PreAuthAspect;
import com.cyber.core.properties.FileProperties;
import com.cyber.core.properties.SecureProperties;
import com.cyber.core.upload.CyberUploadFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * spring自动装配
 *
 * @author cyber
 * @date 2022年6月24日
 */
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnWebApplication
@EnableConfigurationProperties({FileProperties.class, SecureProperties.class})
public class EnableCoreAutoConfig {
    @Resource
    private HttpServletRequest request;

    public EnableCoreAutoConfig() {

    }

    @Bean
    @ConditionalOnMissingBean
    public PreAuthAspect preAuthAspect() {
        return new PreAuthAspect(request);
    }

    @Bean
    @ConditionalOnMissingBean
    public CyberUploadFile cyberUploadFile() {
        return new CyberUploadFile();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceConfig resourceConfig() {
        return new ResourceConfig();
    }


    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolExecutorConfig threadPoolExecutorConfig() {
        return new ThreadPoolExecutorConfig();
    }


}
