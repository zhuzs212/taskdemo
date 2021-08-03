package com.zhuzs.taskdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动初始化
 *
 * @author: zhu_zishuang
 */
@Component
@Slf4j
public class AdminRunner implements CommandLineRunner {
    /**
     * 注入 AliCloudApiRealtimeStaInfoService
     */
    @Autowired
    private AliCloudApiRealtimeStaInfoService aliCloudApiRealtimeStaInfoService;

    /**
     * 项目启动初始化方法
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
//        log.info("【国控地表水实时数据】定时任务执行开始...");
//        aliCloudApiRealtimeStaInfoService.saveRealtimeStaInfo();
//        log.info("【国控地表水实时数据】定时任务执行结束...");

    }
}

