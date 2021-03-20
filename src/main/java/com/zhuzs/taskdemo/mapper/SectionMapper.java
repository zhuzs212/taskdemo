package com.zhuzs.taskdemo.mapper;

import com.zhuzs.taskdemo.entity.param.QueryExistDataParam;
import com.zhuzs.taskdemo.entity.param.SaveSectionRealtimeStaInfoParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 断面 mapper
 *
 * @author zhu_zishuang
 * @date 3/17/21
 */
public interface SectionMapper {
    /**
     * 批量新增断面信息
     *
     * @param listParam 批量新增断面信息入参
     * @return 受影响的行数
     */
    int addBach(@Param("listParam") List<SaveSectionRealtimeStaInfoParam> listParam);

    /**
     * 查询断面已存在数据
     *
     * @param queryExistDataParam 查询断面已存在数据参数
     * @return 断面已存在数据
     */
    List<SaveSectionRealtimeStaInfoParam> getExistDataList(@Param("param") QueryExistDataParam queryExistDataParam);
}