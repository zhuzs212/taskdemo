package com.zhuzs.taskdemo.controller;

import com.zhuzs.taskdemo.service.AliCloudApiRealtimeStaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author zhu_zishuang
 * @date 3/13/21
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private AliCloudApiRealtimeStaInfoService service;

    /**
     * 调用阿里API接口
     */
    @RequestMapping("/list")
    public void getRealtimeStaInfo() {
        service.saveRealtimeStaInfoBatch();
    }
}

