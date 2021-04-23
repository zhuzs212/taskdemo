package com.zhuzs.taskdemo.controller;

import com.zhuzs.taskdemo.service.AliCloudApiRealtimeStaInfoService;
import com.zhuzs.taskdemo.service.AliCloudApiStaInfoService;
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
    private AliCloudApiRealtimeStaInfoService apiRealtimeStaInfoService;

    @Autowired
    private AliCloudApiStaInfoService apiStaInfoService;

    /**
     * 调用阿里API接口 获取站点实时数据
     */
    @RequestMapping("/getRealtimeStaInfo")
    public void getRealtimeStaInfo() {
        apiRealtimeStaInfoService.saveRealtimeStaInfo();
    }


    /**
     * 调用阿里API接口 获取站点列表
     */
    @RequestMapping("/getStationsInfo")
    public void getStaInfo() {
        apiStaInfoService.saveStationsInfo();
    }
}

