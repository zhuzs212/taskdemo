package com.zhuzs.taskdemo.mapper;

import com.zhuzs.taskdemo.entity.domain.SectionInfoDO;
import com.zhuzs.taskdemo.entity.param.QuerySectionInfoParam;
import com.zhuzs.taskdemo.entity.param.SaveBatchSectionInfoParam;
import com.zhuzs.taskdemo.entity.param.SaveSectionRealtimeStaInfoParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 断面信息 Mapper
 *
 * @author zhu_zishuang
 * @date 3/19/21
 */
public interface SectionInfoMapper {
    /**
     * 查询 断面信息
     * @param querySectionInfoParam 查询断面信息 请求参数
     * @return 断面信息
     */
    List<SectionInfoDO> getSectionInfoList(@Param("param") QuerySectionInfoParam querySectionInfoParam);

    /**
     * 批量新增断面信息
     *
     * @param param 批量新增断面信息入参
     * @return 受影响的行数
     */
    int addBatch(@Param("param")SaveBatchSectionInfoParam param);


    /**
     * 获取所有省份名称
     * @return 所有省份名称
     */
    List<String> getProvinceNameList();
}