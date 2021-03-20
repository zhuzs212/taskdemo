package com.zhuzs.taskdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zhuzs.taskdemo.mapper")
@EnableScheduling
public class TaskdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskdemoApplication.class, args);
    }

}
