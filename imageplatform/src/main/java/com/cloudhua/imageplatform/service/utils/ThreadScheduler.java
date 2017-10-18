package com.cloudhua.imageplatform.service.utils;

import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.StatusCode;
import com.cloudhua.imageplatform.service.exception.UnInitializeException;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Created by yangchao on 2017/8/16.
 * 线程池调度器，包装ScheduledThreadPoolExecutor对外提供方法
 */
public class ThreadScheduler {//线程时间表

    private Logger logger = Logger.getLogger(ThreadScheduler.class);//日志

    private static ScheduledExecutorService scheduledExecutorService;//并发包中的  时间表执行服务

    private static ThreadScheduler threadScheduler;//线程时间表

    private static ConcurrentMap<String, ScheduledFuture> features = new ConcurrentHashMap<>();//特征

    private ThreadScheduler(int corePoolSize) {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(corePoolSize);//时间线程池执行
    }

    public static void init() {//线程时间表《——核心池大小，默认是20
        if (threadScheduler == null)
            threadScheduler = new ThreadScheduler(20);
    }

    public static void init(int corePoolSize) {
        if (threadScheduler == null)
            threadScheduler = new ThreadScheduler(corePoolSize);
    }

    public static ThreadScheduler getInst() throws UnInitializeException {//获取线程时间表
        if (threadScheduler == null) {
            throw new UnInitializeException("UNTINIT", "not init threadScheduler");
        }
        return threadScheduler;
    }

    public void execute(Runnable runnable) {
        scheduledExecutorService.execute(runnable);
    }//执行 一个线程

    public void schedule(Runnable runnable, long delay, TimeUnit unit) {//时间表《——线程，延迟，时间单元
        scheduledExecutorService.schedule(runnable, delay, unit);
    }

    public void scheduleAtFixedRate(Runnable runnable, long initialDelay, long period, TimeUnit unit) {//以固定速率的时间表《——线程，初始化延迟，周期，时间单元
        scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, unit);
    }

    public void scheduleWithFixedDelay(Runnable runnable, long initialDelay, long period, TimeUnit unit) {//时间表未来，时间表有固定延迟《——线程，初始化延迟，周期，时间单元
        ScheduledFuture<?> future = scheduledExecutorService.scheduleWithFixedDelay(runnable, initialDelay, period, unit);
    }

    public void scheduleAtFixedRate(String taskName, boolean cancelOldTask, Runnable runnable, long initialDelay, long period, TimeUnit unit) throws LogicalException {//以固定速率的时间表《——任务名，取消旧任务，线程，初始延迟，周期，时间单元
        attemptCancelTask(taskName, cancelOldTask);
        ScheduledFuture future = scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, unit);
        features.put(taskName, future);
    }

    public void scheduleWithFixedDelay(String taskName, boolean cancelOldTask, Runnable runnable, long initialDelay, long period, TimeUnit unit) throws LogicalException {//时间表有固定拖延《———任务名称，取消就任务，线程，初始延迟，周期，基本单元
        attemptCancelTask(taskName, cancelOldTask);//尝试取消任务或否，然后以固定速率运行时间表，特征要更新
        ScheduledFuture future = scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, unit);
        features.put(taskName, future);
    }

    private void attemptCancelTask(String taskName, boolean cancelOldTask) throws LogicalException {//尝试取消任务《——任务名，取消旧任务
        if (features.containsKey(taskName)) {//如果特征 包含任务名 键，也要取消旧任务时，如果任务还没做，就打断任务
            if (cancelOldTask) {
                ScheduledFuture future = features.get(taskName);
                if (!future.isDone()) {
                    future.cancel(true);
                }
            } else {
                logger.warn("cancel schedule task failed, taskName:" + taskName);
                throw new LogicalException(StatusCode.SCHEDULE_TASK_EXIST, StatusCode.SCHEDULE_TASK_EXIST);
            }
        }
    }
}
