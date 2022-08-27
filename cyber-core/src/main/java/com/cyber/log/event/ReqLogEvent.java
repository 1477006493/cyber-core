package com.cyber.log.event;

import com.cyber.log.bo.ReqLogBO;
import org.springframework.context.ApplicationEvent;

/**
 * spring event 日志收集事件
 * @author cyber
 */
public class ReqLogEvent extends ApplicationEvent {

    public ReqLogEvent(ReqLogBO logDTO) {
        super(logDTO);
    }
}
