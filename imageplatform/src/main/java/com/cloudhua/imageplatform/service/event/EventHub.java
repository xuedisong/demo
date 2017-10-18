package com.cloudhua.imageplatform.service.event;

import com.cloudhua.imageplatform.domain.HttpEnv;
import com.cloudhua.imageplatform.service.exception.UnInitializeException;
import com.cloudhua.imageplatform.service.utils.ThreadScheduler;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangchao on 2017/8/16.
 * 时间总线，用户发出同步、异步时间信息
 */
public class EventHub {

    private static Logger logger = Logger.getLogger(EventHub.class);

    private static Map<EventType, PriorityBlockingQueue<EventListener>> syncEventQueue;

    private static Map<EventType, PriorityBlockingQueue<EventListener>> asyncEventQueue;

    public static final int MAX_QUEUE_SIZE = 100;

    private static EventHub eventHub;

    private EventHub() {
        syncEventQueue  = new ConcurrentHashMap<>();
        asyncEventQueue = new ConcurrentHashMap<>();
    }

    public static void init() {
        if (eventHub ==null) {
            eventHub = new EventHub();
        }
    }

    public static EventHub getInst() throws UnInitializeException {
        if (eventHub == null) {
            throw new UnInitializeException("UNINIT", "not init eventHub");
        }
        return eventHub;
    }

    public void registerSyncListener(EventType eventType, EventListener eventListener) {
        PriorityBlockingQueue<EventListener> eventQueue;
        if (syncEventQueue.containsKey(eventType)) {
            eventQueue = syncEventQueue.get(eventType);
        } else {
            eventQueue = new PriorityBlockingQueue<>(MAX_QUEUE_SIZE, new EventComparator());
        }
        eventQueue.offer(eventListener, 30, TimeUnit.SECONDS);
    }

    public void registerAsyncListener(EventType eventType, EventListener eventListener) {
        PriorityBlockingQueue<EventListener> eventQueue;
        if (asyncEventQueue.containsKey(eventType)) {
            eventQueue = syncEventQueue.get(eventType);
        } else {
            eventQueue = new PriorityBlockingQueue<>(MAX_QUEUE_SIZE, new EventComparator());
        }
        eventQueue.offer(eventListener, 30, TimeUnit.SECONDS);
    }

    public void dispatchEvent(EventType eventType, HttpEnv httpEnv, Event event) throws UnInitializeException {
        // 执行异步监听器
        if (asyncEventQueue.containsKey(eventType)) {
            PriorityBlockingQueue<EventListener> queue = asyncEventQueue.get(eventType);
            Iterator<EventListener> it = queue.iterator();
            while (it.hasNext()) {
                EventListener listener = it.next();
                Runnable runnable = new TaskRunnable(httpEnv, eventType, event, listener);
                ThreadScheduler.getInst().execute(runnable);
            }
        }

        // 执行同步监听器
        if (syncEventQueue.containsKey(eventType)) {
            PriorityBlockingQueue<EventListener> queue = syncEventQueue.get(eventType);
            Iterator<EventListener> it = queue.iterator();
            while (it.hasNext()) {
                EventListener listener = it.next();
                try {
                    listener.onEvent(httpEnv, eventType, event);
                } catch (Exception e) {
                    logger.error("listener has something error. eventType:" + eventType, e);
                }
                if (!event.isContinue()) {
                    break;
                }
            }
        }
    }
}

class EventComparator implements Comparator<EventListener> {//事件优先级

    @Override
    public int compare(EventListener o1, EventListener o2) {
        int i1 = o1.getPriority();
        int i2 = o2.getPriority();
        if (i1 == i2) {
            return 0;
        } else if (i1 > i2) {
            return 1;
        } else {
            return -1;
        }
    }
}
