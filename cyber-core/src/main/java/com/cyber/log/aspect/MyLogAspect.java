package com.cyber.log.aspect;

import com.alibaba.fastjson2.JSON;
import com.cyber.core.api.Result;
import com.cyber.core.tool.WebUtil;
import com.cyber.log.bo.ReqLogBO;
import com.cyber.log.event.ReqLogEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executor;

/**
 * 日志收集切面
 *
 * @author cyber
 */
@Aspect
public class MyLogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("asyncExecutor")
    private Executor asyncExecutor;

    private final ThreadLocal<ReqLogBO> reqLogBOThreadLocal = new ThreadLocal<>();


    /**
     * 当MyLog标注时执行
     */
    @Pointcut("@annotation(com.cyber.log.anno.MyLog)")
    public void execute() {
    }

    /**
     * 前置方法,校验Token权限
     *
     * @param point point
     */
    @Before("execute()")
    public void beforeLog(JoinPoint point) {
        ReqLogBO logBO = get();
        WebUtil.setBaseInfoLog(logBO);
        Signature signature = point.getSignature();
        Object[] args = point.getArgs();
        if (args.length > 0) {
            logBO.setArgs(JSON.toJSONString(args));
        }
        logBO.setMethodName(signature.getName());
        logBO.setClassPath(signature.getDeclaringTypeName());
        reqLogBOThreadLocal.set(logBO);
    }


    @AfterReturning(value = "execute()", returning = "obj")
    public void afterReturnLog(Object obj) {
        ReqLogBO reqLogBO = get();
        asyncExecutor.execute(() -> {
            if (obj instanceof Result) {
                Result<?> r = (Result<?>) obj;
                reqLogBO.setResult(JSON.toJSONString(r));
            }
            reqLogBO.setType(1);
            //耗时
            LocalDateTime end = LocalDateTime.now();
            end.toEpochSecond(ZoneOffset.of("+8"));
            reqLogBO.setEndTime(end);
            LocalDateTime startTime = reqLogBO.getStartTime();
            Duration duration = Duration.between(startTime, end);
            reqLogBO.setTotalTime(duration.toMillis());
            applicationContext.publishEvent(new ReqLogEvent(reqLogBO));
        });
        reqLogBOThreadLocal.remove();
    }

    /**
     * 异常通知
     *
     * @param e
     */
    @AfterThrowing(pointcut = "execute()", throwing = "e")
    public void doAfterThrowable(Throwable e) {
        ReqLogBO reqLogBO = get();
        ReqLogBO.setErrorMessage(e, reqLogBO);
        applicationContext.publishEvent(new ReqLogEvent(reqLogBO));
    }


    /**
     * 获取请求日志对象
     *
     * @return ReqLogBO
     */
    public ReqLogBO get() {
        ReqLogBO logBO = reqLogBOThreadLocal.get();
        if (logBO == null) {
            logBO = new ReqLogBO();
        }
        return logBO;
    }


}
