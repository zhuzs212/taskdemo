package com.zhuzs.taskdemo.entity.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 批量新增断面 入参
 * @author zhu_zishuang
 * @date 3/20/21
 */
@Data
@Accessors(chain = true)
public class SaveBatchSectionInfoParam {
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
     * 断面实时数据信息集合
     */
    List<SaveSectionRealtimeStaInfoParam> paramList;
}

