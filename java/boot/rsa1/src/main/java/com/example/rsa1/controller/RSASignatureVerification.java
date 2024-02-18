package com.example.rsa1.controller;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.example.rsa1.controller.RSAUtil.verify;
import static com.example.rsa1.utils.RSAUtils.verify;

public class RSASignatureVerification {
    public static void main(String[] args) throws Exception {
        // 公钥字符串
        String pubKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqrj+K6LZ9VlQgOONssGdDoIhSB4CtNcTBXKdCx5H6sAapNkumef5VGAyo9HPD+bhmVUW98jf9wr0GsnmxOn/RYay2LIyIQsvOY6AkPnEasggSKNGJiTXlP+K6tVM/sJhjeXAZPqUopB+PWjguZvHRYKt14kDG5FDnaYyVlCQIywIDAQAB";

        // 签名
        String signatureString = "K82_lN4IgDdndgm65tf1sUuEJn6or3_yqN7k_N9b0fFFzXhEPilWvw3V1e0OJ4KPK0IJ3mYJ0J6dQiRnsFLl672VWl0IO5_YdaLhcWkXRTaKwuflLNJ5JJdLpdouO20xyESIC7jVwY2_Z3LewiCmw8h3uL59CtXsNctP6BRzF7k";

        // 待验证的数据
        String jsonData = "{\"agentId\":\"CS3514623\",\"merPhone\":\"158****8012\",\"snCode\":\"000000009999990000001\",\"createTime\":\"20211130095413\",\"merLevel\":\"A\",\"merUsername\":\"张三\",\"merName\":\"张三杂货铺\",\"logonMode\":1,\"merId\":\"110000000221903\",\"screenNum\":\"62*************5757\",\"bindStatus\":0,\"status\":0,\"merchantType\":1}\n";

        // 将公钥字符串转换为 PublicKey 对象
        PublicKey publicKey = getPublicKeyFromString(pubKeyString);

        // 将签名字符串解码为字节数组
        byte[] signatureBytes = Base64.getUrlDecoder().decode(signatureString);
        System.out.println(publicKey);
//        verify(jsonData.getBytes(),publicKey,signatureBytes);
        // 使用公钥进行验签
        boolean verificationResult = verifySignature(jsonData.getBytes(), publicKey, signatureBytes);

        System.out.println("Signature Verification Result: " + verificationResult);
    }

    private static PublicKey getPublicKeyFromString(String pubKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(pubKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private static boolean verifySignature(byte[] data, PublicKey publicKey, byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }
}
