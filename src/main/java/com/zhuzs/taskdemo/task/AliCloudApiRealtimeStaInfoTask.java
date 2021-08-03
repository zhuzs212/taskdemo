package com.zhuzs.taskdemo.task;

import com.zhuzs.taskdemo.service.AliCloudApiRealtimeStaInfoService;
import com.zhuzs.taskdemo.service.AliCloudApiStaInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 阿里云数据获取Task
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

    @Autowired
    private AliCloudApiStaInfoService apiStaInfoService;

    /**
     * 国控地表水实时数据批量接口
     */
    // {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
    @Scheduled(cron = "1 20 0,4,8,12,16,20 * * ?")
    public void realtimeStaInfoTask() {
//        log.info("【国控地表水实时数据】定时任务执行开始...");
//        aliCloudApiRealtimeStaInfoService.saveRealtimeStaInfo();
//        log.info("【国控地表水实时数据】定时任务执行结束...");
    }

    /**
     * 国控地表水站点列表接口
     */
    // {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
//    @Scheduled(cron = "1 10 0,4,8,12,16,20 * * ?")
    public void staInfoTask() {
//        log.info("【国控地表水站点列表】定时任务执行开始...");
//        apiStaInfoService.saveStationsInfo();
//        log.info("【国控地表水站点列表】定时任务执行结束...");
    }
}

