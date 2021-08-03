package com.zhuzs.taskdemo.controller.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhu_zishuang
 * @date 7/20/21
 */
public class Provide {
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("121.40.249.52");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/shuiwu");
        connectionFactory.setUsername("shuiwu");
        connectionFactory.setPassword("shuiwu");

        // 创建连接对象
        Connection connection = connectionFactory.newConnection();

        // 创建通道
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello",true,false,false,null);
        channel.basicPublish("","hello",null,"hello rabbit".getBytes());
        channel.close();
        connection.close();
    }
}

