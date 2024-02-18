package com.pay.brand.channel.sdk.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * @author wangyangyang
 * @date 2021-10-20 17:43:22
 **/
public class ChannelRes {

    /**
     * 响应码
     */
    private String resCode;

    /**
     * 响应描述
     */
    private String resMsg;

    /**
     * 错误码
     */
    private String failCode;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 品牌编号
     */
    private String brandNo;

    /**
     * 接口业务编号
     */
    private String apiCode;

    /**
     * 签名
     */
    private String sign;
    /**
     * 请求流水号
     */
    private String prNo;
    /**
     * 请求时间 yyyy-MM-dd HH:mm:ss
     */
    private String prTime;

    /**
     * 报文数据(密文)
     */
    private String data;

    public ChannelRes() {
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
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

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
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
        if (!(o instanceof ChannelRes)) {
            return false;
        }
        ChannelRes that = (ChannelRes) o;
        return Objects.equals(resCode, that.resCode) &&
                Objects.equals(resMsg, that.resMsg) &&
                Objects.equals(failCode, that.failCode) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(brandNo, that.brandNo) &&
                Objects.equals(apiCode, that.apiCode) &&
                Objects.equals(sign, that.sign) &&
                Objects.equals(prNo, that.prNo) &&
                Objects.equals(prTime, that.prTime) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resCode, resMsg, brand, brandNo, apiCode, sign, prNo, prTime, data);
    }

    @Override
    public String toString() {
        return "ChannelRes{" +
                "resCode='" + resCode + '\'' +
                ", resMsg='" + resMsg + '\'' +
                ", failCode='" + failCode + '\'' +
                ", brand='" + brand + '\'' +
                ", brandNo='" + brandNo + '\'' +
                ", apiCode='" + apiCode + '\'' +
                ", sign='" + sign + '\'' +
                ", prNo='" + prNo + '\'' +
                ", prTime='" + prTime + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public static ChannelRes SUCCESS(ChannelReq req, String data) {
        ChannelRes channelRes = new ChannelRes();
        channelRes.setBrand(req.getBrand());
        channelRes.setBrandNo(req.getBrandNo());
        channelRes.setApiCode(req.getApiCode());
        channelRes.setPrTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        channelRes.setData(data);
        channelRes.setPrNo(req.getPrNo());
        channelRes.setResCode("00");
        channelRes.setResMsg("处理成功");
        channelRes.setFailCode("0000");
        return channelRes;
    }

    public static ChannelRes FAIL(ChannelReq req,
                                  String failCode,
                                  String failMsg,
                                  String data) {
        ChannelRes res = new ChannelRes();
        res.setResCode("98");
        res.setFailCode(failCode);
        res.setResMsg(failMsg);
        res.setPrTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        res.setPrNo(req.getPrNo());
        res.setBrandNo(req.getBrandNo());
        res.setBrand(req.getBrand());
        res.setApiCode(req.getApiCode());
        res.setData(data);
        return res;
    }

    public static ChannelRes FAIL(String failCode, String msg) {
        ChannelRes res = new ChannelRes();
        res.setResCode("98");
        res.setFailCode(failCode);
        res.setResMsg(msg);
        res.setPrNo(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        res.setPrTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return res;
    }

    public static ChannelRes ERROR(String msg) {
        ChannelRes res = new ChannelRes();
        res.setResCode("99");
        res.setResMsg(msg);
        res.setPrNo(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        return res;
    }
}
