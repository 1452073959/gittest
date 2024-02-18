package com.pay.brand.channel.sdk.builder;

import com.pay.brand.channel.sdk.cipher.SM2Utils;
import com.pay.brand.channel.sdk.cipher.SM4Utils;

/**
 * 报文构建器
 *
 * @author wangyangyang
 * @date 2021-10-20 16:34:31
 **/
public class DataBuilderOrg {

    /**
     * 已方私钥
     */
    private String privateKey;

    private static DataBuilderOrg dataBuilder;

    private DataBuilderOrg(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 获取一个单例的实例
     *
     * @param privateKey
     * @return
     */
    public static DataBuilderOrg getInstance(String privateKey) {
        if (dataBuilder == null) {
            synchronized (DataBuilderOrg.class) {
                if (dataBuilder == null) {
                    dataBuilder = new DataBuilderOrg(privateKey);
                }
            }
        }
        return dataBuilder;
    }


    /**
     * 使用工作密钥 加密报文
     *
     * @param data
     */
    public String encrypt(String workKey, String data) {
        String encrypt = SM4Utils.encrypt(workKey, data);

        return encrypt;
    }

    /**
     * 使用私钥进行数据签名
     *
     * @param encryptData
     * @return
     */
    public String sign(String encryptData) {
        String sign = SM2Utils.privateKeySign(privateKey, encryptData);
        return sign;
    }

    /**
     * 使用公钥验证签名
     *
     * @param publicKey
     * @param encryptData
     * @param signature
     * @return
     */
    public boolean signVerify(String publicKey, String encryptData, String signature) {
        return SM2Utils.publicKeyVerify(publicKey, encryptData, signature);
    }

    /**
     * 使用工作密钥解密密文数据
     *
     * @param workKey
     * @param encryptData
     * @return
     */
    public String decrypt(String workKey, String encryptData) {
        String decrypt = SM4Utils.decrypt(workKey, encryptData);
        return decrypt;
    }

    /**
     * 生成工作密钥
     *
     * @param brandNo 品牌方编号（代理商编号）
     * @return
     */
    public String generateWorkKey(String brandNo) {
        return SM2Utils.privateKeySign(privateKey, brandNo);
    }

    /**
     * 使用接入方 公钥加密数据
     *
     * @param pubKey
     * @param data
     * @return
     */
    public String publicKeyEncrypt(String pubKey, String data) {
        return SM2Utils.encrypt(pubKey, data);
    }

    /**
     * 使用本机构私钥解密数据
     *
     * @param encryptData
     */
    public String privateKeyDecrypt(String encryptData) {
        return SM2Utils.decrypt(this.privateKey, encryptData);
    }
}
