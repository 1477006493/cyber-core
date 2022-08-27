package com.cyber.log.config;

import com.cyber.log.aspect.MyLogAspect;
import com.cyber.log.handle.BaseExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * spring自动装配
 *
 * @author cyber
 * @date 2022年7月8日
 */
@Configuration(
	proxyBeanMethods = false
)
@EnableAsync
@ConditionalOnWebApplication
@AllArgsConstructor
@ConditionalOnProperty(name = "cyber.log.enabled", havingValue = "true", matchIfMissing = true)
public class EnableLogAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public BaseExceptionHandler handler(){
		return new BaseExceptionHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public MyLogAspect myLogAspect(){
		return new MyLogAspect();
	}


}
