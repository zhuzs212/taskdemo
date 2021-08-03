package com.zhuzs.taskdemo.controller.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消费者 1
 *
 * @author zhu_zishuang
 * @date 7/20/21
 */
@Component
@RabbitListener(queues = "shuiwuDirectQueue")
public class Consumer1 {
    @RabbitHandler
    public void process(String msg, Channel channel, Message message) throws IOException {
        try {
            //TODO 业务处理

            String numstr = msg.substring(3);
            Integer num = Integer.parseInt(numstr);


            /**
             * 确认一条消息：
             * channel.basicAck(deliveryTag, false)
             *      deliveryTag:  该消息的index
             *      multiple： 是否批量.true:将一次性ack所有小于deliveryTag的消息
             */
            if(num%2==0){
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                        false, true);
            }else{
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("get msg1 success msg = " + msg);
            }


        } catch (Exception e) {
            //消费者处理出了问题，需要告诉队列信息消费失败
            /**
             * 拒绝确认消息:
             * channel.basicNack(long deliveryTag, boolean multiple, boolean requeue) ;
             *      deliveryTag:  该消息的index
             *      multiple： 是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
             *      requeue： 被拒绝的是否重新入队列
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            System.err.println("get msg1 failed msg = " + msg);

            /**
             * 拒绝一条消息：
             * channel.basicReject(long deliveryTag, boolean requeue);
             *      deliveryTag:  该消息的index
             *      requeue： 被拒绝的是否重新入队列
             */
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}