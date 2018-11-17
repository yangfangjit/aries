package com.yangfang.aries.src;


import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * 定时任务分发器
 *
 * @author 幽明
 * @serial 2018/11/16
 */
public class TimerTaskManager {

    /**
     * 任务池数据结构选择
     * 1、队列，common选择
     *
     * 2、map，便于取消与更新定时任务
     */
    private final Map<String, AbstractTimerTask> taskPool = Maps.newConcurrentMap();

    private Executor executor;

    /**
     * scan the task pool, find executable tasks and executed
     *
     * @return num of executed task
     */
    public int scan() {
        if (taskPool.isEmpty()) {
            return 0;
        }

        int count = 0;
        Set<Map.Entry<String, AbstractTimerTask>> entries = taskPool.entrySet();
        Iterator<Map.Entry<String, AbstractTimerTask>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, AbstractTimerTask> entry = iterator.next();
            if (entry.getValue().getTimestamp().getTime() < System.currentTimeMillis()) {
                executor.execute(entry.getValue());
                iterator.remove();
                count++;
            }
        }
        return count;
    }

    public boolean add(AbstractTimerTask task) {
        if (taskPool.containsKey(task.getKey())) {
            throw new RuntimeException("任务池中有相同任务");
        }

        if (task.getTimestamp().getTime() < System.currentTimeMillis()) {

        }

        return Objects.isNull(taskPool.put(task.getKey(), task));
    }

    public boolean update(AbstractTimerTask task) {
        if (!taskPool.containsKey(task.getKey())) {
            throw new RuntimeException("任务池中无该任务");
        }

        return Objects.isNull(taskPool.put(task.getKey(), task));
    }

    public void forceUpdate(AbstractTimerTask task) {
        taskPool.put(task.getKey(), task);
    }

    public boolean cancel(AbstractTimerTask task) {
        if (taskPool.containsKey(task.getKey())) {
            return Objects.nonNull(taskPool.remove(task.getKey()));
        }

        return true;
    }

    public TimerTaskManager getInstance(Executor executor) {
        return new TimerTaskManager(executor);
    }

    private TimerTaskManager(Executor executor) {
        this.executor = executor;
    }
}
