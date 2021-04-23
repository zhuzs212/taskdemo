package com.zhuzs.taskdemo.entity.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 批量新增断面站点信息 入参
 * @author zhu_zishuang
 * @date 3/20/21
 */
@Data
@Accessors(chain = true)
public class SaveBatchStationsInfoParam {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 断面名称
     */
    private String staname;

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
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SaveBatchStationsInfoParam) {
            SaveBatchStationsInfoParam stationsInfoParam = (SaveBatchStationsInfoParam) obj;
            return province.equals(stationsInfoParam.getProvince()) && staname.equalsIgnoreCase(stationsInfoParam.getStaname().trim());
        }
        return false;
    }
}

