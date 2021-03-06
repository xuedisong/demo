package com.cloudhua.imageplatform.service.event;

import com.cloudhua.imageplatform.domain.HttpEnv;
import org.apache.log4j.Logger;

/**
 * Created by yangchao on 2017/8/16.
 */
class TaskRunnable implements Runnable {//Tsak线程
    private static Logger logger = Logger.getLogger(TaskRunnable.class);

    private HttpEnv httpEnv;

    private EventType eventType;

    private Event event;

    private EventListener eventListener;

    public TaskRunnable(HttpEnv httpEnv, EventType eventType, Event event, EventListener eventListener) {
        this.httpEnv = httpEnv;
        this.eventType = eventType;
        this.event = event;
        this.eventListener = eventListener;
    }

    @Override
    public void run() {
        try {
            eventListener.onEvent(httpEnv, eventType, event);
        } catch (Throwable throwable) {
            logger.error("Event execute failed, event name:" + eventType, throwable);
        }
    }
}
