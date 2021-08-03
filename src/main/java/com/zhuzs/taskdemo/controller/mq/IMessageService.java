package com.zhuzs.taskdemo.controller.mq;

/**
 * 消息发送服务
 *
 * @author zhu_zishuang
 * @date 7/20/21
 */
public interface IMessageService {
    void sendMessage(String exchange, String routingKey, Object msg);
}