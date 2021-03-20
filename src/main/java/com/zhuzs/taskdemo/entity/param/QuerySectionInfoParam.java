package com.zhuzs.taskdemo.entity.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 查询断面信息入参
 *
 * @author zhu_zishuang
 * @date 3/17/21
 */
@Data
@Accessors(chain = true)
public class QuerySectionInfoParam {
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
     * 断面名称集合
     */
    private Set<String> sectionInfoNameSet;
}