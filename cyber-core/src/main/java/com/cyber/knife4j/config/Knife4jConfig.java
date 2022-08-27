package com.cyber.knife4j.config;

import com.cyber.knife4j.properties.Router;
import com.cyber.knife4j.properties.SwaggerBaseInfoProperties;
import com.cyber.knife4j.properties.SwaggerConfigProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smy
 * @Description knife4j配置
 * @date 2022年6月28日
 * @Version 1.0
 */
@Configuration(
        proxyBeanMethods = false
)
@EnableKnife4j
@EnableConfigurationProperties({SwaggerBaseInfoProperties.class, SwaggerConfigProperties.class})
@ConditionalOnWebApplication
@EnableWebMvc
@ConditionalOnProperty(name = "cyber.swagger.base.enabled", havingValue = "true", matchIfMissing = true)
public class Knife4jConfig implements BeanFactoryAware {
    @Autowired
    private SwaggerBaseInfoProperties baseInfoProperties;
    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private SwaggerConfigProperties configProperties;
    @Autowired
    private BeanFactory beanFactory;

    private static final String AUTH_KEY = "token";

    final String BASE_PACKAGE = "com.cyber.blog.modules.";
    final String AUTH_PACKAGE = BASE_PACKAGE + "auth";
    final String DESC_PACKAGE = BASE_PACKAGE + "desk";
    final String WEB_PACKAGE = BASE_PACKAGE + "web";
    final String SYSTEM_PACKAGE = BASE_PACKAGE + "system";
    final String DEVELOP = BASE_PACKAGE + "develop";

/*    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "cyber.swagger.base.enabled", havingValue = "true", matchIfMissing = true)
    public List<Docket> createRestApi() {
        if (configProperties == null || configProperties.getRouters() == null) {
            throw new RuntimeException("缺少Swagger路由配置信息");
        }
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Router> routers = configProperties.getRouters();
        List<Docket> docketList = new ArrayList<>();
        for (Router router : routers) {
            Docket docket = docket(router.getName(), router.getLocation());
            configurableBeanFactory.registerSingleton(router.getLocation(), docket);
            docketList.add(docket);
        }
        return docketList;
    }*/

    @Bean
    public Docket createAuthDevelopRestApi() {
        return docket("快速开发", DEVELOP);
    }

    @Bean
    public Docket createAuthRestApi() {
        return docket("授权模块", AUTH_PACKAGE);
    }

    @Bean
    public Docket createUserRestApi() {
        return docket("系统模块", SYSTEM_PACKAGE);
    }

    @Bean
    public Docket createDeskRestApi() {
        return docket("工作台", DESC_PACKAGE);
    }

    @Bean
    public Docket createWebRestApi() {
        return docket("用户端", WEB_PACKAGE);
    }

    private Docket docket(String groupName, String basePackages) {
        List<RequestParameter> requestParameters = new ArrayList<>();
        requestParameters.add(new RequestParameterBuilder()
                .name(AUTHORIZATION)
                .description("Token")
                .in(ParameterType.HEADER)
                .query(parameterSpecificationBuilder -> parameterSpecificationBuilder.defaultValue(" ")
                        .allowEmptyValue(true))
                .required(false)
                .build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackages))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(requestParameters);
    }


    private ApiInfo apiInfo() {
        if (baseInfoProperties == null) {
            throw new RuntimeException("缺少Swagger文档基础配置信息");
        }
        return new ApiInfoBuilder()
                .description(baseInfoProperties.getDescription())
                .contact(new Contact(baseInfoProperties.getAuthor(), baseInfoProperties.getUrl(), baseInfoProperties.getEmail()))
                .version(baseInfoProperties.getVersion())
                .title(baseInfoProperties.getTitle())
                .build();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
