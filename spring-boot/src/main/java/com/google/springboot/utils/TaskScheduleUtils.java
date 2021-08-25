package com.google.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: yangwenkang
 * @date: 2021/08/20
 * @description: 周期任务工具类
 **/
@Slf4j
@Component
public class TaskScheduleUtils {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private static ConcurrentHashMap<String, ScheduledFuture<?>> future2task = new ConcurrentHashMap();

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("TaskScheduleUtils.");
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return taskScheduler;
    }


    public Boolean register(String key, Runnable task, String cron) {
        try {
            this.deleteTask(key);
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(task, new CronTrigger(cron));
            future2task.put(key, future);
            log.info("register success:{}", key);
            return true;
        } catch (Exception e) {
            log.info("register fail:{}, error:{}", key, e);
            return false;
        }
    }

    private Boolean deleteTask(String key) {
        try {
            ScheduledFuture<?> future = future2task.get(key);
            if (null != future) {
                future.cancel(true);
                future2task.remove(key);
            }
            return true;
        } catch (Exception e) {
            log.info("register fail:{}, error:{}", key, e);
            return false;
        }
    }
}
