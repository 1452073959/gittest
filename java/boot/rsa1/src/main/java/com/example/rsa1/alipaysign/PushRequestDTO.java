package com.example.rsa1.alipaysign;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 推送请求
 */
@Data
@Builder
@JsonSerialize
public class PushRequestDTO implements Serializable {

    /**
     * 应用ID，代理编号
     */
    @JsonProperty("app_id")
    private String appId;

    /**
     * 方法名
     */
    @JsonProperty("method")
    private String method;

    /**
     * 编码
     */
    @JsonProperty("charset")
    private String charset;

    /**
     * 签名类型
     */
    @JsonProperty("sign_type")
    private String signType;

    /**
     * 签名
     */
    @JsonProperty("sign")
    private String sign;

    /**
     * 请求时间戳
     */
    @JsonProperty("timestamp")
    private String timestamp;

    /**
     * 业务类型
     */
    @JsonProperty("biz_content")
    private String bizContent;

    /**
     * 接口版本，默认V1
     */
    @JsonProperty("version")
    private String version;



}
