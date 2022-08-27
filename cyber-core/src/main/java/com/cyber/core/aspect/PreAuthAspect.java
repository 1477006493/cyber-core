package com.cyber.core.aspect;

import com.cyber.core.anno.PreAuth;
import com.cyber.core.enums.Code;
import com.cyber.core.exception.BaseException;
import com.cyber.core.tool.StringUtil;
import com.cyber.core.tool.WebUtil;
import com.cyber.jwt.handle.JwtHandle;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义注解，作用于方法上，用于权限校验
 *
 * @author cyber
 * @date 2022年7月12日
 */
@Aspect
@Slf4j
public class PreAuthAspect {

    private final HttpServletRequest request;

    public PreAuthAspect(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 当PreAuth标注时执行
     */
    @Pointcut("@annotation(com.cyber.core.anno.PreAuth)")
    public void execute() {
    }

    /**
     * 前置方法,校验Token权限
     *
     * @param point point
     */
    @Before("execute()")
    public void checkAuth(JoinPoint point) {
        Object attribute = request.getAttribute(WebUtil.USER);
        if (attribute == null) {
            log.warn("PreAuthAspect 请求未授权");
            throw new BaseException(Code.UN_AUTHORIZED);
        }
        PreAuth auth = point.getClass().getAnnotation(PreAuth.class);
        if (auth == null) {
            return;
        }
        Claims claims = (Claims) attribute;
        String role = (String) claims.get(JwtHandle.ROLES);
        if (StringUtil.isBlank(role)) {
            throw new BaseException(Code.UN_AUTHORIZED);
        }
        List<String> roleList = StringUtil.stringToList(role);
        List<String> collect = roleList.stream().filter(role::equals).collect(Collectors.toList());
        if (collect.size() <= 0) {
            throw new BaseException(Code.UN_AUTHORIZED);
        }
    }
}
