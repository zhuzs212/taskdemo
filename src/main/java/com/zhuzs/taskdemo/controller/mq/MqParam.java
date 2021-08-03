package com.zhuzs.taskdemo.controller.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhu_zishuang
 * @date 7/13/21
 */
@Data
public class MqParam implements Serializable {
    private static final long serialVersionUID = -5877207787524413292L;
    private  String fileBase64;
    private String fileName;
}

