package com.zhuzs.taskdemo.entity.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 断面 映射实体
 *
 * @author zhu_zishuang
 * @date 3/19/21
 */
@Data
public class SectionInfoDO implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 省份名称
     */
    private String province;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 区县名称
     */
    private String district;

    /**
     * 断面名称
     */
    private String staname;

    /**
     * 有效标识
     */
    private Boolean mark;

    private static final long serialVersionUID = 1L;
}