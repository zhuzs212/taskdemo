package com.zhuzs.taskdemo.controller.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送服务 实现类
 *
 * @author zhu_zishuang
 * @date 7/20/21
 */
@Component
@Slf4j
public class MessageServiceImpl implements IMessageService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String exchange, String routingKey, Object msg) {
//        //消息发送失败返回到队列中, yml需要配置 publisher-returns: true
//        rabbitTemplate.setMandatory(true);
//        //消息消费者确认收到消息后，手动ack回执
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setReturnCallback(this);
        //发送消息
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }

//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        log.info("---- returnedMessage ----replyCode=" + replyCode + " replyText=" + replyText + " ");
//    }
//
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        log.info("---- confirm ----ack=" + ack + "  cause=" + String.valueOf(cause));
//        log.info("correlationData -->" + correlationData.toString());
//        if (ack) {
//            log.info("---- confirm ----ack==true  cause=" + cause);
//        } else {
//            log.info("---- confirm ----ack==false  cause=" + cause);
//        }
//    }

}