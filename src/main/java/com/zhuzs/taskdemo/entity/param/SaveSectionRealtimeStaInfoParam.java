package com.zhuzs.taskdemo.entity.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 断面实时数据信息 新增入参
 *
 * @author zhu_zishuang
 * @date 3/17/21
 */
@Data
public class SaveSectionRealtimeStaInfoParam {
    /**
     * 浊度
     */
    private String turbidity;

    /**
     * 断面数据ID
     */
    private Integer section_info_id;

    /**
     * 断面名称
     */
    private String staname;

    /**
     * 断面类型：1国考 2省考 3其他
     */
    private Integer type;

    /**
     * 高锰酸解指数
     */
    private String pp_v;

    /**
     * 总磷
     */
    private String tp_v;

    /**
     * 总氮
     */
    private String tn_v;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 叶绿素
     */
    private String chlorophyll;

    /**
     * 检测时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date pubtime;

    /**
     * 水质类别
     */
    private String water_l;

    /**
     * 流域
     */
    private String valley;

    /**
     * 氨氮
     */
    private String an_v;

    /**
     * 溶解氧
     */
    private String do_v;

    /**
     * 行政区
     */
    private String province;

    /**
     * 水温
     */
    private String water_temp;

    /**
     * 电导率
     */
    private String conductivity;

    /**
     * 藻密度
     */
    private String algal_density;

    /**
     * PH值
     */
    private String ph_v;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SaveSectionRealtimeStaInfoParam) {
            SaveSectionRealtimeStaInfoParam saveSectionParam = (SaveSectionRealtimeStaInfoParam) obj;
            return staname.equalsIgnoreCase(saveSectionParam.getStaname().trim()) && pubtime.equals(saveSectionParam.getPubtime()
            );
        }
        return false;
    }

}