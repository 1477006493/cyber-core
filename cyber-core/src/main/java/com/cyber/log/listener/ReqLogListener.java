package com.cyber.log.listener;

import com.cyber.log.bo.ReqLogBO;
import com.cyber.log.event.ReqLogEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import java.util.function.Consumer;

/**
 * 异步监听日志事件
 * 采用函数式接口的形式，发布日志
 * @author cyber
 */
@AllArgsConstructor
public class ReqLogListener {

    private Consumer<ReqLogBO> consumer;

    @Async
    @Order
    @EventListener(ReqLogEvent.class)
    public void saveSysLog(ReqLogEvent event) {
        ReqLogBO optLog = (ReqLogBO) event.getSource();
        consumer.accept(optLog);
    }
}
