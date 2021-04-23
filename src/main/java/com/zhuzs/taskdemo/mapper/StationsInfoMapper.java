package com.zhuzs.taskdemo.mapper;

import com.zhuzs.taskdemo.entity.param.SaveBatchStationsInfoParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 断面站点信息 Mapper
 *
 * @author zhu_zishuang
 * @date 3/19/21
 */
public interface StationsInfoMapper {
    /**
     * 查询已存在站点断面信息列表
     *
     * @return 已存在站点断面信息列表
     */
    List<SaveBatchStationsInfoParam> getExistStationsInfoList();

    /**
     * 批量新增断面站点信息
     *
     * @param list 批量新增断面站点信息入参
     * @return 受影响的行数
     */
    int addBatch(@Param("list") List<SaveBatchStationsInfoParam> list);

    /**
     * 获取所有省份名称
     *
     * @return 所有省份名称
     */
    List<String> getProvinceNameList();

    /**
     * 从站点信息表同步断面坐标数据（断面表已存在且坐标数据缺失的数据）
     *
     * @param province
     */
    void syncData(String province);
}