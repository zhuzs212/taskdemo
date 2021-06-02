package com.zhuzs.taskdemo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuzs.taskdemo.entity.param.SaveBatchStationsInfoParam;
import com.zhuzs.taskdemo.mapper.StationsInfoMapper;
import com.zhuzs.taskdemo.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国控地表水站点列表接口获取业务层
 *
 * @author zhu_zishuang
 * @date 3/17/21
 */
@Service
@Slf4j
public class AliCloudApiStaInfoService {
    private static final String PROVINCE = "江苏省";

    /**
     * 注入断面站点信息Mapper
     */
    @Resource
    private StationsInfoMapper stationsInfoMapper;

    /**
     * 获取全国断面站点信息
     */
    public void saveStationsInfo() {
        // 获取所有省份信息
        List<String> provinceList = stationsInfoMapper.getProvinceNameList();
        if (CollectionUtils.isEmpty(provinceList)) {
            // 默认获取江苏省断面站点信息
            provinceList.add(PROVINCE);
        }
        // 遍历省份，获取断面站点信息
        provinceList.forEach(province -> saveStationsInfo(province));
    }

    public void saveStationsInfo(String province) {
        // 调用阿里API
        log.info("获取【国控地表水站点列表】开始...");
        JSONArray jsonArray;
        jsonArray = getStationsInfo(province);
        log.info("获取【国控地表水站点列表】结束...");
        if (jsonArray != null ? !jsonArray.isEmpty() : false) {
            long startTime = System.currentTimeMillis();
            // 断面实时数据信息对象 集合
            List<SaveBatchStationsInfoParam> sourceStationsInfList = JSONArray.parseArray(jsonArray.toJSONString(), SaveBatchStationsInfoParam.class);
            if (CollectionUtils.isEmpty(sourceStationsInfList)) {
                log.info("【国控地表水站点列表】,返回结果为空！");
                return;
            }
            // 特殊字符处理，如:'(',')','（','）'，' '
            log.info("【国控地表水站点列表】数据,特殊字符处理...");
            sourceStationsInfList.forEach(param -> {
                // 时间字段处理
                String staName = param.getStaname();
                if (staName.contains("(") || staName.contains("（")) {
                    param.setStaname(staName.replace("(", "-").replace(")", "").replace("（", "-").replace("）", "").trim());
                }
            });
            log.info("【国控地表水站点列表】数据，特殊字符处理结束...");

            // 已存在断面站点信息
            List<SaveBatchStationsInfoParam> existStationsInfoList = stationsInfoMapper.getExistStationsInfoList();
            sourceStationsInfList.removeAll(existStationsInfoList);

            // 过滤坐标信息无效的数据
            List<SaveBatchStationsInfoParam> toRemoveStationsInfoList = new ArrayList<>();
            for (SaveBatchStationsInfoParam param : sourceStationsInfList) {
                if (StringUtils.isBlank(param.getLatitude()) || param.getLatitude().equals("0") || StringUtils.isBlank(param.getLongitude()) || param.getLongitude().equals("0")){
                    toRemoveStationsInfoList.add(param);
                }
            }
            sourceStationsInfList.removeAll(toRemoveStationsInfoList);


            if(CollectionUtils.isEmpty(sourceStationsInfList)){
                log.info("无【国控地表水站点列表】数据持久化...");
                return;
            }

            log.info("【国控地表水站点列表】数据本地持久化开始...");
            stationsInfoMapper.addBatch(sourceStationsInfList);
            log.info("【国控地表水站点列表】数据本地持久化结束...");
            long endTime = System.currentTimeMillis();
            log.info(" 耗时 ：" + (endTime - startTime) + "ms, 新增 " + sourceStationsInfList.size() + "条数据！");

            log.info("【断面信息】数据同步，开始...");
            stationsInfoMapper.syncData(province);
            log.info("【断面信息】数据同步，结束...");
        }
    }


    /**
     * 调用阿里API接口 获取断面站点信息
     *
     * @param province 省份名称
     * @return 断面站点信息列表
     * @author zhu_zishuang
     * @date 4/22/21
     */
    private JSONArray getStationsInfo(String province) {
        String host = "https://naswater.market.alicloudapi.com";
        String path = "/api/stainfo/stations";
        String method = "GET";
        String appCode = "bd5f2a00c23e416f9ad325fee007d144";
        Map<String, String> headers = new HashMap<>(10);
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> param = new HashMap<>(10);
        param.put("province", province);

        try {
            //获取返回数据状态，get获取的字段需要根据提供的返回值去获取
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, param);
            //获取返回数据状态，get获取的字段需要根据提供的返回值去获取
            if (200 == response.getStatusLine().getStatusCode()) {
                //转换成JSON格式
                JSONObject dataJson = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));

                //"data"是根据返回值设定
                return JSONObject.parseArray(dataJson.get("data").toString());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用阿里API接口异常！{}", e);
            throw new RuntimeException("调用阿里API接口异常！{}", e);
        }
    }
}

