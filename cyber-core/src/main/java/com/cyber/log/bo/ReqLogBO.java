package com.cyber.log.bo;

import com.cyber.core.tool.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请求日志
 *
 * @author cyber
 */
@Data
public class ReqLogBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 正常业务请求
     */
    public static final int NORMAL = 1;

    /**
     * 错误请求
     */
    public static final int ERROR = 2;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * IP归属地
     */
    private String ipCity;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求端口号
     */
    private String requestPort;

    /**
     * 访问完整路径
     */
    private String requestUrl;

    /**
     * 日志类型,1操作日志，2异常
     */
    private Integer type;

    /**
     * 操作人
     */
    private String optUser;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 请求类型,get/post/put/delete
     */
    private String methodType;

    /**
     * 请求参数
     */
    private String args;

    /**
     * 返回值
     */
    private String result;

    /**
     * 异常信息 title
     */
    private String exTitle;

    /**
     * 异常信息详细描述
     */
    private String exDetail;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    private LocalDateTime endTime;

    /**
     * 总耗时
     */
    private Long totalTime;

    /**
     * 浏览器
     */
    private String ua;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * 处理异常信息
     *
     * @param e        Throwable
     * @param reqLogBO reqLogBO
     */
    public static void setErrorMessage(Throwable e, ReqLogBO reqLogBO) {
        reqLogBO.setType(ReqLogBO.ERROR);
        reqLogBO.setExTitle(e.getMessage());
        reqLogBO.setExDetail(StringUtil.getStackTrace(e));
        reqLogBO.setEndTime(LocalDateTime.now());
    }
}
