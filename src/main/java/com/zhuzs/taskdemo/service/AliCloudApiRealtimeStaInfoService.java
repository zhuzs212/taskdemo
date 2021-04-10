package com.zhuzs.taskdemo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuzs.taskdemo.entity.domain.SectionInfoDO;
import com.zhuzs.taskdemo.entity.param.QueryExistDataParam;
import com.zhuzs.taskdemo.entity.param.QuerySectionInfoParam;
import com.zhuzs.taskdemo.entity.param.SaveBatchSectionInfoParam;
import com.zhuzs.taskdemo.entity.param.SaveSectionRealtimeStaInfoParam;
import com.zhuzs.taskdemo.mapper.SectionInfoMapper;
import com.zhuzs.taskdemo.mapper.SectionMapper;
import com.zhuzs.taskdemo.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 阿里云实时站数据获取业务
 *
 * @author zhu_zishuang
 * @date 3/17/21
 */
@Service
@Slf4j
public class AliCloudApiRealtimeStaInfoService {

    private static final String PROVINCE = "江苏省";
    /**
     * 注入断面Mapper
     */
    @Resource
    private SectionMapper sectionMapper;

    /**
     * 注入断面基本信息Mapper
     */
    @Resource
    private SectionInfoMapper sectionInfoMapper;

    /**
     * 同步全国断面信息
     */
    public void saveRealtimeStaInfo(){
        // 获取所有省份信息
        List<String> provinceList = sectionInfoMapper.getProvinceNameList();
        if(CollectionUtils.isEmpty(provinceList)){
            // 默认获取江苏省断面信息
            provinceList.add(PROVINCE);
        }
        // 遍历省份，同步断面信息
        provinceList.forEach(province->saveRealtimeStaInfoBatch(province));
    }
    /**
     * 同步断面信息
     * @param province 省份
     */
    public void saveRealtimeStaInfoBatch(String province) {

        // 调用阿里API
        log.info("开始调用阿里API...");
        JSONArray jsonArray;
        jsonArray = getRealtimeStaInfo(province);
        log.info("调用阿里API结束...");
        if (jsonArray != null ? !jsonArray.isEmpty() : false) {
            long startTime = System.currentTimeMillis();
            // 断面实时数据信息对象 集合
            List<SaveSectionRealtimeStaInfoParam> sourceSectionRealtimeStaInfList = JSONArray.parseArray(jsonArray.toJSONString(), SaveSectionRealtimeStaInfoParam.class);
            if (CollectionUtils.isEmpty(sourceSectionRealtimeStaInfList)) {
                log.info("调用阿里API,返回结果为空！");
                return;
            }
            // 特殊字符处理，如:'(',')','（','）'，' '
            sourceSectionRealtimeStaInfList.forEach(param -> {
                // 时间字段处理
                String staName = param.getStaname();
                if (staName.contains("(") || staName.contains("（")) {
                    param.setStaname(staName.replace("(", "-").replace(")", "").replace("（", "-").replace("）", "").trim());
                }
            });

            /*
                查询是否存在重复数据（断面实时数据信息）
                场景：初始化任务、重启任务时
             */
            List<SaveSectionRealtimeStaInfoParam> existDataList = getExistDataList(new QueryExistDataParam().setSourceSectionList(sourceSectionRealtimeStaInfList).setProvince(province));
            if (!CollectionUtils.isEmpty(existDataList)) {
                // 存在，则过滤掉重复数据
                sourceSectionRealtimeStaInfList.removeAll(existDataList);
            }

            // 数据本地持久化
            if (CollectionUtils.isEmpty(sourceSectionRealtimeStaInfList)) {
                return;
            }

            // 获取所有待新增数据断面名称,用于获取断面的ID信息
            Set<String> sectionInfoNameSet = sourceSectionRealtimeStaInfList.stream().map(SaveSectionRealtimeStaInfoParam::getStaname).collect(Collectors.toSet());

            /*
                检验待新增数据中，是否存在新增的断面。
                存在，则需要将新增的断面维护到本地数据库，并与断面实时数据信息做映射
             */

            // 获取所有断面
            List<SectionInfoDO> sectionInfoList = sectionInfoMapper.getSectionInfoList(new QuerySectionInfoParam().setProvince(province));

            // 过滤所有已存在断面
            List<SaveSectionRealtimeStaInfoParam> newList = new ArrayList<>(sourceSectionRealtimeStaInfList.size());
            Collections.addAll(newList, new SaveSectionRealtimeStaInfoParam[sourceSectionRealtimeStaInfList.size()]);
            Collections.copy(newList, sourceSectionRealtimeStaInfList);
            Iterator<SaveSectionRealtimeStaInfoParam> iterator = newList.iterator();
            while (iterator.hasNext()) {
                SaveSectionRealtimeStaInfoParam item = iterator.next();
                for (SectionInfoDO sectionInfoDO : sectionInfoList) {
                    if (item.getStaname().equals(sectionInfoDO.getStaname())) {
                        iterator.remove();
                    }
                }

            }

            // 有新增断面，则维护到本地数据库
            if (!CollectionUtils.isEmpty(newList)) {
                log.info("存在新增断面，【断面】数据本地持久化开始...");
                sectionInfoMapper.addBatch(new SaveBatchSectionInfoParam().setParamList(newList).setProvince(province));
                log.info("【断面】数据本地持久化结束" + "，新增 " + newList.size() + "条数据！");
            }

            // 根据待新增数据的断面，查询断面
            List<SectionInfoDO> sectionInfoDOList = sectionInfoMapper.getSectionInfoList(new QuerySectionInfoParam().setSectionInfoNameSet(sectionInfoNameSet).setProvince(province));
            // 断面 与 断面实时数据信息做映射处理
            if (!CollectionUtils.isEmpty(sectionInfoDOList)) {
                // 设置断面ID
                sectionInfoDOList.forEach(
                        sectionInfoDO -> sourceSectionRealtimeStaInfList.stream()
                                .filter(saveSectionParam -> saveSectionParam.getStaname().equals(sectionInfoDO.getStaname()))
                                .forEach(saveSectionParam -> saveSectionParam.setSection_info_id(sectionInfoDO.getId()))
                );
            }

            log.info("【断面实时数据信息】数据本地持久化开始...data:{}" + sourceSectionRealtimeStaInfList);
            sectionMapper.addBach(sourceSectionRealtimeStaInfList);
            log.info("【断面实时数据信息】数据本地持久化结束...");
            long endTime = System.currentTimeMillis();
            log.info(" 耗时 ：" + (endTime - startTime) + "ms, 新增 " + sourceSectionRealtimeStaInfList.size() + "条数据！");
        }
    }

    /**
     * 调用阿里API接口
     *
     * @return JSONArray对象
     * @author zhu_zishuang
     * @date 3/19/21
     */
    private JSONArray getRealtimeStaInfo(String province) {
        String host = "https://naswater.market.alicloudapi.com";
        String path = "/api/stainfo/station_realtime";
        String method = "GET";
        String appCode = "bd5f2a00c23e416f9ad325fee007d144";
        Map<String, String> headers = new HashMap<>(10);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> queryParam = new HashMap<>(10);
        queryParam.put("pageNum", "1");
        queryParam.put("pageSize", "2000");
        queryParam.put("province", province);

        try {
            /*
              重要提示如下:
              HttpUtils请从
              https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
              下载

              相应的依赖请参照
              https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);

            //获取返回数据状态，get获取的字段需要根据提供的返回值去获取
            if (200 == response.getStatusLine().getStatusCode()) {
                //转换成JSON格式
                JSONObject dataJson = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                //"data"是根据返回值设定
                JSONObject data = JSONObject.parseObject(dataJson.get("data").toString());
                //"rows"是根据返回值设定
                return JSONObject.parseArray(data.get("rows").toString());
            }

            log.error("调用阿里API接口失败！");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用阿里API接口异常！{}", e);
            throw new RuntimeException("调用阿里API接口异常！{}", e);
        }
    }

    /**
     * 查询断面已存在数据
     *
     * @param queryExistDataParam 查询断面已存在数据参数
     * @return 断面已存在数据
     */
    private List<SaveSectionRealtimeStaInfoParam> getExistDataList(QueryExistDataParam queryExistDataParam) {
        return sectionMapper.getExistDataList(queryExistDataParam);
    }


}

