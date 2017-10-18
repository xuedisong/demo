package com.cloudhua.imageplatform.service.event;

import com.cloudhua.imageplatform.domain.HttpEnv;

/**
 * Created by yangchao on 2017/8/16.
 */
public abstract class EventListener {
    public int priority = 0;

    public EventListener() {}

    public EventListener(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract void onEvent(HttpEnv httpEnv, EventType eventType, Event event) throws Exception;
}
