package com.zhuzs.taskdemo.controller.mq;

import com.zhuzs.taskdemo.utils.Base64Utils;
import com.zhuzs.taskdemo.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author zhu_zishuang
 * @date 7/13/21
 */
@RestController
@RequestMapping(value = "/mq")
@Slf4j
public class MqController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/test1")
    public void test1(String message) {
        // 队列名称：QueueConstant.QUEUE_NOTIFY_HELLO --> com.queue.notify.hello
        amqpTemplate.convertAndSend("shuiwu", message);
        // rabbitTemplate是AmqpTemplate的实现类,可以用rabbitTemplate
//        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_NOTIFY_HELLO, "测试数据！！！！！");
    }

    @GetMapping("/test2")
    public void test2() {
        byte[] bytes = FileUtil.getBytesByFile("/Users/zhuzs/Downloads/Resources/picture/1.xuejing.jpg");

        amqpTemplate.convertAndSend("shuiwu", bytes);
        // rabbitTemplate是AmqpTemplate的实现类,可以用rabbitTemplate
//        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_NOTIFY_HELLO, "测试数据！！！！！");
    }

    @RequestMapping("/test3")
    public void test3(@RequestParam("file") MultipartFile mFile) {
//        byte[] bytes = FileUtil.getBytesByFile("/Users/zhuzs/Downloads/Resources/picture/1.xuejing.jpg");
        try {
            String fileName = mFile.getOriginalFilename();
            String mFileBase64 = Base64Utils.generateBase64(mFile);
            MqParam mqParam = new MqParam();
            mqParam.setFileBase64(mFileBase64);
            mqParam.setFileName(fileName);


//            String fileName = mFile.getOriginalFilename();
//            File file = FileUtil.multipartFileToFi/Users/zhuzs/Downloads/Resources/le(mFile, "/Users/zhuzs/Downloads/Resources/");
//            String fileBase64 = Base64Utils.encode(file);
//            MqParam mqParam = new MqParam();
//            mqParam.setFileBase64(fileBase64);
//            mqParam.setFileName(fileName);

            System.out.println("mqParam: " + mqParam);
            amqpTemplate.convertAndSend("", "shuiwu", mqParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // rabbitTemplate是AmqpTemplate的实现类,可以用rabbitTemplate
//        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_NOTIFY_HELLO, "测试数据！！！！！");
    }

//    @RabbitListener(queues = {"shuiwu"})
    public void receiveSmsCodeQueue(MqParam mqPara) throws Exception {
//        log.info("------hello：消费者处理消息------");
//        String mFileBase64 = map.get("mFileBase64").toString();
//        String fileName = map.get("fileName").toString();
//        log.debug("这是文件：", mFileBase64);
//        Base64Utils.decode(mFileBase64, "/Users/zhuzs/Downloads/Resources/" + fileName);

        log.info("------hello：消费者处理消息------");
        String fileBase64 = mqPara.getFileBase64();
        String fileName = mqPara.getFileName();
        log.debug("这是文件：", fileBase64);
        MultipartFile mFile = Base64Utils.base64ToMultipart(fileBase64);
        File file = FileUtil.multipartFileToFile(mFile, "/Users/zhuzs/Downloads/" + fileName);
        FileUtil.getFileByBytes(FileUtil.getBytesByFile(new FileInputStream(file)), "/Users/zhuzs/Downloads/Resources/", fileName);
    }


}

