package com.zhuzs.taskdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    @Bean(name = "getQueue")
    public Queue getQueue() {
        return new Queue("shuiwuDirectQueue", true, false, false);
    }


//    @Bean(name = "getDirectExchange")
//    public DirectExchange getDirectExchange() {
//        return new DirectExchange("shuiwuDirectExchange", true, false);
//    }
//
//    @Bean(name = "getQueue")
//    public Queue getQueue() {
//        return new Queue("shuiwuDirectQueue", true, false, false);
//    }

//    @Bean
//    public Binding getDirectExchangeQueueTx(
//            @Qualifier(value = "getDirectExchange") DirectExchange getDirectExchangeTx,
//            @Qualifier(value = "getQueue") Queue getQueueTx) {
//        return BindingBuilder.bind(getQueueTx).to(getDirectExchangeTx).with("shuiwu");
//    }

}