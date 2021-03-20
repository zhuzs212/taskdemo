package com.zhuzs.taskdemo.task;

import com.zhuzs.taskdemo.service.AliCloudApiRealtimeStaInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 阿里云实时站数据获取Task
 *
 * @author: zhu_zishuang
 * @date: 2021-03-09
 */
@Component
@Slf4j
public class AliCloudApiRealtimeStaInfoTask {
    /**
     * 注入 AliCloudApiRealtimeStaInfoService
     */
    @Autowired
    private AliCloudApiRealtimeStaInfoService aliCloudApiRealtimeStaInfoService;

    // {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
    @Scheduled(cron = "1 10 0,4,8,12,16,20 * * ?")
    public void realtimeStaInfoTask() {
        log.info("定时任务执行开始...");
        aliCloudApiRealtimeStaInfoService.saveRealtimeStaInfoBatch();
        log.info("定时任务执行结束...");
    }
}

