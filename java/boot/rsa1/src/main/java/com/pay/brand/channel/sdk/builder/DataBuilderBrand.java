package com.pay.brand.channel.sdk.builder;

import com.pay.brand.channel.sdk.bean.ChannelReq;
import com.pay.brand.channel.sdk.bean.ChannelRes;
import com.pay.brand.channel.sdk.cipher.SM2Utils;
import com.pay.brand.channel.sdk.cipher.SM4Utils;
import com.pay.brand.channel.sdk.enums.ResCode;
import com.pay.brand.channel.sdk.exception.BizSignException;
import com.pay.brand.channel.sdk.exception.CipherException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 报文构建器
 *
 * @author wangyangyang
 * @date 2021-10-20 16:34:31
 **/
public class DataBuilderBrand {

    /**
     * 对方公钥
     */
    private String publicKey;

    /**
     * 已方私钥
     */
    private String privateKey;

    /**
     * 代理编号
     */
    private String brandNo;

    /**
     * 品牌
     */
    private String brand;


    private static DataBuilderBrand dataBuilderBrand;

    private DataBuilderBrand(String publicKey, String privateKey, String brandNo, String brand) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.brandNo = brandNo;
        this.brand = brand;
    }

    /**
     * 获取一个单例的实例
     *
     * @param publicKey
     * @param privateKey
     * @return
     */
    public static DataBuilderBrand getInstance(String publicKey, String privateKey, String brandNo, String brand) {
        if (dataBuilderBrand == null) {
            synchronized (DataBuilderBrand.class) {
                if (dataBuilderBrand == null) {
                    dataBuilderBrand = new DataBuilderBrand(publicKey, privateKey, brandNo, brand);
                }
            }
        }
        return dataBuilderBrand;
    }


    /**
     * 构建签到/签退 请求
     *
     * @param signingReqJson
     * @return ChannelReq
     */
    public ChannelReq buildSignReq(String signingReqJson, String apiCode) {
        ChannelReq channelReq = new ChannelReq();
        try {
            String encrypt = SM2Utils.encrypt(this.publicKey, signingReqJson);
            String sign = this.sign(encrypt);
            channelReq.setData(encrypt);
            channelReq.setSign(sign);
        } catch (Exception e) {
            throw new BizSignException("构建签到请求异常", e);
        }

        channelReq.setBrand(brand);
        channelReq.setBrandNo(brandNo);
        channelReq.setApiCode(apiCode);
        channelReq.setPrNo(generateProcessNo());
        channelReq.setPrTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return channelReq;
    }

    /**
     * 解析签到响应报文，返回工作密钥
     *
     * @return String 工作密钥
     */
    public String parseSignRes(ChannelRes channelRes) {
        if (!ResCode.SUCCESS.getCode().equals(channelRes.getResCode())) {
            throw new BizSignException("响应异常");
        }

        if (!brandNo.equals(channelRes.getBrandNo())) {
            throw new BizSignException("签到业务代理商异常");
        }

        String encryptData = channelRes.getData();
        boolean ver = this.signVer(encryptData, channelRes.getSign());
        if (!ver) {
            throw new BizSignException("解析签到响应，签名验证异常，请检查密钥配置。");
        }

        try {
            return SM2Utils.decrypt(privateKey, encryptData);
        } catch (Exception e) {
            throw new BizSignException("解析签到响应，解密数据异常，请检查密钥配置", e);
        }
    }

    /**
     * 构建业务请求数据
     *
     * @param dataJson
     * @return
     */
    public ChannelReq buildBusinessReq(String workKey, String dataJson, String apiCode) {
        String encrypt = this.encrypt(workKey, dataJson);
        String sign = this.sign(encrypt);
        ChannelReq channelReq = new ChannelReq();
        channelReq.setBrand(brand);
        channelReq.setBrandNo(brandNo);
        channelReq.setApiCode(apiCode);
        channelReq.setPrNo(generateProcessNo());
        channelReq.setPrTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        channelReq.setData(encrypt);
        channelReq.setSign(sign);
        return channelReq;
    }

    /**
     * 构建成功响应数据
     *
     * @param workKey
     * @param apiCode
     * @return
     */
    public ChannelRes buildSuccessRes(String workKey, String jsonData, String apiCode) {
        String encrypt = this.encrypt(workKey, "处理成功");
        String sign = this.sign(encrypt);
        ChannelRes channelRes = new ChannelRes();
        channelRes.setBrand(brand);
        channelRes.setBrandNo(brandNo);
        channelRes.setApiCode(apiCode);
        channelRes.setPrTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        channelRes.setData(encrypt);
        channelRes.setSign(sign);
        channelRes.setResCode("00");
        channelRes.setResMsg("处理成功");
        channelRes.setFailCode("0000");
        return channelRes;
    }

    /**
     * 解析业务响应数据
     *
     * @param workKey
     * @param channelRes
     * @return
     */
    public String parseBusinessRes(String workKey, ChannelRes channelRes) {
        String sign = channelRes.getSign();
        String encryptData = channelRes.getData();
        boolean ver = this.signVer(encryptData, sign);
        if (!ver) {
            throw new CipherException("签名验证失败");
        }

        String decrypt = this.decrypt(workKey, encryptData);

        return decrypt;
    }

    /**
     * 解析业务请求数据（适用于数据推送）
     *
     * @param
     * @return
     */
    public String parseBusinessReq(String workKey, ChannelReq channelReq) {
        String sign = channelReq.getSign();
        String data = channelReq.getData();
        boolean ver = this.signVer(data, sign);
        if (!ver) {
            throw new CipherException("签名验证失败");
        }

        String decrypt = this.decrypt(workKey, data);

        return decrypt;
    }


    /**
     * 生成系统追踪号
     *
     * @return
     */
    private String generateProcessNo() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    private String sign(String data) {
        try {
            return SM2Utils.privateKeySign(this.privateKey, data);
        } catch (Exception e) {
            throw new CipherException("签名失败", e);
        }
    }

    private boolean signVer(String data, String sign) {
        try {
            return SM2Utils.publicKeyVerify(this.publicKey, data, sign);
        } catch (Exception e) {
            throw new CipherException("验证签名异常", e);
        }
    }

    private String encrypt(String workKey, String data) {
        try {
            return SM4Utils.encrypt(workKey, data);
        } catch (Exception e) {
            throw new CipherException("数据加密失败", e);
        }
    }

    private String decrypt(String workKey, String data) {
        try {
            return SM4Utils.decrypt(workKey, data);
        } catch (Exception e) {
            throw new CipherException("数据解密失败", e);
        }
    }
}
