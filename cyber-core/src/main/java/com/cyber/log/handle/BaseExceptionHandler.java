package com.cyber.log.handle;


import com.cyber.core.api.Result;
import com.cyber.core.base.BaseMethod;
import com.cyber.core.enums.Code;
import com.cyber.core.exception.BaseException;
import com.cyber.core.tool.StringUtil;
import com.cyber.core.tool.WebUtil;
import com.cyber.log.bo.ReqLogBO;
import com.cyber.log.event.ReqLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * 统一异常处理类
 *
 * @author cyber
 * @date 2022年7月8日
 */
@ControllerAdvice
@Component
@Slf4j
public class BaseExceptionHandler extends BaseMethod {

    @Autowired
    @Qualifier("asyncExecutor")
    private Executor asyncExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 拦截自定义异常
     *
     * @param e 异常消息
     * @return Result<Object>格式化后的结果集
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result<?> error(BaseException e) {
        log.error("===============发生了异常===============\n", e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 拦截 Throwable
     *
     * @param e 异常消息
     * @return Result<Object>格式化后的结果集
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Result<?> throwableError(Throwable e) {
        log.error("===============发生了Throwable===============\n ", e);
        //如果是自定义校验异常的话，就给予相应的提示
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            String defaultMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
            return Result.fail(Code.PARAM_INVALID.getCode(), defaultMessage);
        }
        HttpServletRequest request = WebUtil.getRequest();
        ReqLogBO logBO = new ReqLogBO();
        if (request != null){
            //确保在web环境下获取数据
            WebUtil.setBaseInfoLog(logBO);
        }
        ReqLogBO.setErrorMessage(e,logBO);
        applicationContext.publishEvent(new ReqLogEvent(logBO));
        return Result.fail(Code.ERROR.getCode(), Code.ERROR.getMessage());
    }

    /**
     * 拦截文件上传闲置异常
     *
     * @param e 异常消息
     * @return Result<Object>格式化后的结果集
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result<?> fileUploadSizeLimitExceededException(MaxUploadSizeExceededException e) {
        log.error("文件异常：", e);
        return Result.fail(Code.FILE_MAX_SIZE_LIMIT.getCode(), Code.FILE_MAX_SIZE_LIMIT.getMessage());
    }
}