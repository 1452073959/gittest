package com.cloudgain.contract;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.cloudgain.base.TokenUtils;

import java.util.Map;

/**
 * @author :  zhangpengfei
 * @description: 商户合同历史数据处理demo-只使用一次
 * @date :  2022/5/13
 */

public class OnlyOnceContractDemo {
    /**
     * 接口地址
     */
    private final static String URL = "http://10.18.6.13:10000/api/business/contractInf/update/history/contract";
    /**
     * 用户名
     */
    private final static String USERNAME = "zhangpengfei@credlink.com";
    /**
     * 密码
     */
    private final static String PASSWORD = "zuihou";

    /**
     * @return java.lang.String
     * @Author huangdaye
     * @Description 获取token（此token可以多次使用，有效期为8小时，不用频繁请求token接口）
     * @Date 15:45 2020/7/9
     * @Param []
     **/
    public static String getToken() {
        //获取token
        return TokenUtils.getToken(USERNAME, PASSWORD);
    }

    public static void apiInvoke() {
        //获取token ,token可以多次使用，不用频繁获取
        String token = getToken();
        //获取Authorization
        String authorization = TokenUtils.getAuthorization();
        // 发送请求
        HttpResponse res = HttpRequest.get(URL)
                .header("content-type", "application/json")
                //clientId和client_secret
                .header("Authorization", authorization)
                //token,有效期8小时
                .header("token", token)
                //tenant固定值，无需替换
                .header("tenant", "MDAwMA==")
                .timeout(20000).execute();
        String body = res.body();
        Map map = JSONObject.parseObject(body, Map.class);
        System.out.println(map);
    }

    public static void main(String[] args) {

        apiInvoke();

    }
}

