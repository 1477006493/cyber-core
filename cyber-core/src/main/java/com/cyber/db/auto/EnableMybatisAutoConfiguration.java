package com.cyber.db.auto;

import com.cyber.db.config.MybatisPlusConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


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
@ConditionalOnMissingBean({MybatisPlusConfig.class})
@Import(MybatisPlusConfig.class)
public class EnableMybatisAutoConfiguration {

	public EnableMybatisAutoConfiguration() {
	}

	@Bean
	@ConditionalOnMissingBean
	public MybatisPlusConfig mybatisPlusConfig(){
		return new MybatisPlusConfig();
	}

}
