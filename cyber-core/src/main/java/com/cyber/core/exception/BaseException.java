package com.cyber.core.exception;

import com.cyber.core.enums.Code;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常处理
 *
 * @author cyber
 * @date 2022年5月13日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    /**
     * 请求是否成功
     */
    private Boolean flag = false;

    /**
     * 异常消息
     */
    private String message;

    /**
     * 返回的状态码
     */
    private Integer code;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(Boolean flag, Integer code, String message) {
        super(message);
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
    public BaseException(Code code) {
        super(code.getMessage());
        this.message = code.getMessage();
        this.code = code.getCode();
    }
    public BaseException(Code code, String message) {
        super(code.getMessage());
        this.message = message;
        this.code = code.getCode();
    }
}
