package com.pay.brand.channel.sdk.bean;

import java.util.Objects;

/**
 * 通道请求基础封装
 *
 * @author wangyangyang
 * @date 2021-10-20 17:43:12
 **/
public class ChannelReq {

    /**
     * 接口业务编号
     */
    private String apiCode;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 品牌编号
     */
    private String brandNo;

    /**
     * 签名
     */
    private String sign;

    /**
     * 请求流水号
     */
    private String prNo;

    /**
     * 请求时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String prTime;

    /**
     * 报文数据
     */
    private String data;


    public ChannelReq() {
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public String getPrTime() {
        return prTime;
    }

    public void setPrTime(String prTime) {
        this.prTime = prTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChannelReq)) {
            return false;
        }
        ChannelReq that = (ChannelReq) o;
        return Objects.equals(apiCode, that.apiCode) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(brandNo, that.brandNo) &&
                Objects.equals(sign, that.sign) &&
                Objects.equals(prNo, that.prNo) &&
                Objects.equals(prTime, that.prTime) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiCode, brand, brandNo, sign, prNo, prTime, data);
    }

    @Override
    public String toString() {
        return "ChannelReq{" +
                "apiCode='" + apiCode + '\'' +
                ", brand='" + brand + '\'' +
                ", brandNo='" + brandNo + '\'' +
                ", sign='" + sign + '\'' +
                ", prNo='" + prNo + '\'' +
                ", prTime='" + prTime + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
