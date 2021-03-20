package com.zhuzs.taskdemo.entity.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 查询是否已存在断面信息入参
 *
 * @author zhu_zishuang
 * @date 3/20/21
 */
@Data
@Accessors(chain = true)
public class QueryExistDataParam {
    /**
     * 行政区
     */
    private String province;

    /**
     * 待新增断面集合
     */
    List<SaveSectionRealtimeStaInfoParam> sourceSectionList;
}

