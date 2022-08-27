package com.cyber.core.api;

import com.cyber.core.enums.Code;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 统一请求响应,新增枚举类HTTPCode的支持
 *
 * @author cyber
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 是否成功
     */
    private boolean flag;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;


    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = Code.OK.getCode();
        this.message = "操作成功!";
    }


    /**
     * 操作成功！
     */
    public static <T> Result<T> data(T data) {
        return new Result<>(true, Code.OK.getCode(), Code.OK.getMessage(), data);
    }

    /**
     * 操作成功！
     */
    public static <T> Result<T> success() {
        return new Result<>(true, Code.OK.getCode(), Code.OK.getMessage());
    }

    /**
     * 操作成功！
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(true, Code.OK.getCode(), msg);
    }

    /**
     * 操作成功，分页
     */
    public static <T> Result<T> page(Long total, List<T> data) {
        return new Result<T>(true, Code.OK.getCode(), Code.OK.getMessage(), new PageResult<T>(total, data));
    }

    /**
     * 操作失败！
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(false, Code.ERROR.getCode(), message);
    }

    /**
     * 操作失败！
     */
    public static <T> Result<T> fail() {
        return new Result<>(false, Code.ERROR.getCode(), "操作失败！");
    }

    /**
     * 操作失败！
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(false, code, message);
    }

    public static <T> Result<T> fail(Code code) {
        return fail(code.getCode(), code.getMessage());
    }

    public static <T> Result<T> fail(Code code, String msg) {
        return fail(code.getCode(), msg);
    }


}