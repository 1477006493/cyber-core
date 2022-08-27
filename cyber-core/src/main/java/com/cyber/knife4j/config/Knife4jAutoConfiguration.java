package com.cyber.knife4j.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


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
@ConditionalOnProperty(name = "cyber.swagger.base.enabled", havingValue = "true", matchIfMissing = true)
public class Knife4jAutoConfiguration extends WebMvcConfigurationSupport {

	public Knife4jAutoConfiguration() {

	}


}
