package com.example.rsa1.controller;
import cn.hutool.json.JSONUtil;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;

/**
 * 签名  SHA1WithRSA
 */
public class RSAUtil {

    /**
     * RSA最大加密明文大小 2048/8-11
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小 2048/8
     */
    private static final int MAX_DECRYPT_BLOCK = 256;
    private static final String SIGN_TYPE_RSA = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private RSAUtil() {

    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    private static boolean verify(byte[] srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(key);
        signature.update(srcData);
        return signature.verify(Base64.getUrlDecoder().decode(sign));
    }

    public static boolean verify(String srcData, String publicKeystr, String sign) throws Exception {
        System.out.println(publicKeystr);
        PublicKey publicKey = getPublicKeyFromX509(SIGN_TYPE_RSA, publicKeystr);
//        System.out.println(publicKey);
        System.out.println(srcData.getBytes(StandardCharsets.UTF_8));
        return verify(srcData.getBytes(StandardCharsets.UTF_8), publicKey, sign);
    }

    /**
     * 验签-ASCLL排序
     * @param srcData
     * @param publicKeystr
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verifyASCLLJSON(String srcData, String publicKeystr, String sign) throws Exception {
        String jsonStr = getASCLLJSON(srcData);
        PublicKey publicKey = getPublicKeyFromX509(SIGN_TYPE_RSA, publicKeystr);

        return verify(jsonStr.getBytes(StandardCharsets.UTF_8), publicKey, sign);
    }

    public static String getASCLLJSON(String srcData) {
        Map<String, Object> jsonMap= JSONUtil.toBean(srcData, Map.class);
        //将key值单独封装成List
        List<String> keys = new ArrayList(jsonMap.keySet());
        //排序
        Collections.sort(keys);
        //使用LinkedHashMap记录插入顺序
        LinkedHashMap<String, Object> linkmap=new LinkedHashMap();
        //按照key值顺序插入对应的value
        for(int i=0;i<keys.size();i++){
            if(StringUtils.isNotEmpty(keys.get(i))){
                linkmap.put(keys.get(i), jsonMap.get(keys.get(i)));
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(linkmap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过证书获取公钥（需BASE64，X509为通用证书标准）
     *
     * @param algorithm
     * @param base64PubKey
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromX509(String algorithm, String base64PubKey) throws Exception {
        if (algorithm == null || "".equals(algorithm) || base64PubKey == null || "".equals(base64PubKey)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base64PubKey)));
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer, String> genKeyPair() throws NoSuchAlgorithmException {
        Map<Integer, String> keyMap = new HashMap<>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥

        Base64.Encoder encoder = Base64.getEncoder();
        String publicKeyString = encoder.encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = encoder.encodeToString(privateKey.getEncoded());
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString); // 0表示公钥
        keyMap.put(1, privateKeyString); // 1表示私钥
        return keyMap;
    }

    public static byte[] sign(byte[] data, String privateKey) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decoder.decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 生成签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String data, String privateKey) throws Exception {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(sign(data.getBytes(), privateKey));
    }

    /**
     * 生成签名-ASCLL排序
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String signASCLLJSON(String data, String privateKey) throws Exception {
        String jsonStr = getASCLLJSON(data);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(sign(jsonStr.getBytes(), privateKey));
    }

    /**
     * 公钥加密  如果大于245则分段加密
     */
    public static String encryptByPublic(String encryptingStr, String publicKeyStr) {
        try {
            // 将公钥由字符串转为UTF-8格式的字节数组
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            // 获得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory;
            factory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            // 返回加密后由Base64编码的加密信息
            return new String(Base64.getEncoder().encode(encryptedData));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥解密 如果大于256则分段解密
     */
    public static String decryptByPrivate(String encryptedStr, String privateKeyStr) {
        try {
            // 对私钥解密
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 获得待解密数据
            byte[] data = Base64.getDecoder().decode(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            // 返回UTF-8编码的解密信息
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥加密  如果大于245则分段加密
     */
    public static String encryptByPrivate(String encryptingStr, String privateKeyStr) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            // 返回加密后由Base64编码的加密信息
            return new String(Base64.getEncoder().encode(encryptedData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密 如果大于256则分段解密
     */
    public static String decryptByPublic(String encryptedStr, String publicKeyStr) {
        if(StringUtils.isBlank(encryptedStr)){
            return null;
        }
        try {
            // 对公钥解密
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = Base64.getDecoder().decode(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            // 返回UTF-8编码的解密信息
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PublicKey getPublicKeyFromString(String pubKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(pubKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static void main(String[] args) throws Exception {
        // 公钥字符串
//        String pubKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqrj+K6LZ9VlQgOONssGdDoIhSB4CtNcTBXKdCx5H6sAapNkumef5VGAyo9HPD+bhmVUW98jf9wr0GsnmxOn/RYay2LIyIQsvOY6AkPnEasggSKNGJiTXlP+K6tVM/sJhjeXAZPqUopB+PWjguZvHRYKt14kDG5FDnaYyVlCQIywIDAQAB";
        String pubKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT4Y142Te5wx7NIiYOH5+jGnYMfZdmqFPEC0ukiN56uLA6Iil9nKtAzujejFvoCcEbhYT6rcxAF21XccLswRUf6jtTi62DTTc8PUZnEax+LbjxYOJ+xE61hbrwg1n3GDWsT7wgTB3ZcJhn5ESAB+xhhT2KQcFlroekepC2OMkf8wIDAQAB";
        System.out.println("公钥字符串:"+pubKeyString);
        // 将公钥字符串转换为 PublicKey 对象
        PublicKey publicKey = getPublicKeyFromString(pubKeyString);
        // 签名
//        String signatureString = "K82_lN4IgDdndgm65tf1sUuEJn6or3_yqN7k_N9b0fFFzXhEPilWvw3V1e0OJ4KPK0IJ3mYJ0J6dQiRnsFLl672VWl0IO5_YdaLhcWkXRTaKwuflLNJ5JJdLpdouO20xyESIC7jVwY2_Z3LewiCmw8h3uL59CtXsNctP6BRzF7k";
        String signatureString = "kfMCJ7g8zMJwy78_ScFExAIxFFDQrdy_FfQYhIMFNP5K_dR6_SRX__FEfNzW9_GEZAz_y4CIeTC_EQDgUJ7ggjOtvhBXh6Ma69wvbJ_-FoQWcZPSlHKeoOHU1qgitQanwA08xJmNAS20Z3vldEeF3nciNk0n4yK84uKZ6jNW0XY";
        System.out.println("签名字符串:"+signatureString);
        // 待验证的数据
//        String jsonData = "{\"agentId\":\"CS3514623\",\"merPhone\":\"158****8012\",\"snCode\":\"000000009999990000001\",\"createTime\":\"20211130095413\",\"merLevel\":\"A\",\"merUsername\":\"张三\",\"merName\":\"张三杂货铺\",\"logonMode\":1,\"merId\":\"110000000221903\",\"screenNum\":\"62*************5757\",\"bindStatus\":0,\"status\":0,\"merchantType\":1}";
        String jsonData = "{\"agentId\":\"10009272\",\"machineType\":4,\"orderId\":\"20240126160438609944502\",\"sysno\":\"104066270319\",\"merName\":\"大明翰林红木家具\",\"merId\":\"110000008255451\",\"snCode\":\"00005702883072046701\",\"transAmount\":\"1000\",\"merSigeFee\":\"0.38\",\"extractionFee\":\"0\",\"settlementCycle\":0,\"payType\":3,\"cardType\":1,\"transCategory\":4,\"cardNo\":\"6251641086020363\",\"transTime\":\"20240126160438\",\"proceedsTemplateId\":\"435a1629c2e349c29c6fd0a8f20d58b8\",\"proceedsTemplateName\":\"2024联动电签POS-GH\",\"agentCost\":\"0.2700\",\"updateTime\":\"20240126161102\",\"copartnerRate\":\"0\",\"debitT1Fd\":\"0\",\"outState\":1,\"failMsg\":null}";
        System.out.println("json字符串:"+jsonData);
         boolean ver=verify(jsonData.getBytes(),publicKey,signatureString);
         boolean ver2=verify(jsonData,pubKeyString,signatureString);
//         boolean ver3=verifyASCLLJSON(jsonData,pubKeyString,signatureString);
        System.out.println("验签结果"+ver);
        System.out.println("验签结果"+ver2);
        String str="{\"agentId\":\"10009272\",\"time\":1706680259000,\"tusn\":\"00005702883072046701\"}";
        String privateString="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJqIygCXb7/jYuIJ\n" +
                "QH7pGeBkm2ui5EKuiWKOe+0eiXozul5EMUQmQam8Q/znY2zVmLwZz9wpRZ9LG7EI\n" +
                "/bIFg0iHR95rWo6lQYE6CsBxdj3nEDpNqV9sK6fydrdXOqrYnkUB1Y3v61mCuPml\n" +
                "ICPWaUztY9LqjpXn6IT3PyK+oAdbAgMBAAECgYA798kr4ZhkBoFg5eY5WYvJIEGP\n" +
                "y2540fJOZKyCn88JtPkli6DyqUXh8EUR51kV6u2SURHwhbOW1TaxxKNuaeU/q3Hl\n" +
                "F5gN+5voefDc25dsYOIX/X0jDwpqeicm3J6fb1ilsrOh/S6DO1PfkyZiNNOW9rJr\n" +
                "hOTWDU8ABxjCC95mQQJBAOtI4uwWYm420gfRebOT2paWy2FU+xWLRmO/FtNtJPFz\n" +
                "KplqFJmwHw49Emq3VAhmEh7siMR1N8RwsXxmhjZ7Ho8CQQCoI9vqPKCfTpv4toKc\n" +
                "LipgrQsSctOBktZHHHA9htvY6fxBrBdTU8kNaqQTxrwK/q6VW3wp9fxvmN6cqQHz\n" +
                "e/B1AkAZn2Fe9n0XpY6v3UcUoFhgp+FUIy4IxZcPy8KPmcY5KSKNfHjr17s6ELqN\n" +
                "tpNHKt50Uav/QQimsuMROhUJn9TPAkAHq8smvqldW0rGaB1BXuHUE1EEAHUh3j8k\n" +
                "c2mzhOmW1eYKYP2XcaUklY7y0AhYQBJbAheCGpTvynutjhBmuWrZAkAGHMdwS+qN\n" +
                "Kmz497gpxMqtx3+iUtO8LWznDN4yEj47jn9gGoDDjsWRKyZw7jWjVVnLp4PD5tdv\n" +
                "oqrFa7DM2/7q";
        String sign2= sign(str,privateString);
        System.out.println( sign2);
//        System.out.println("验签结果"+ver3);
    }
}
