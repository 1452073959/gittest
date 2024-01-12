package com.example.rsa1.alipaysign;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;

import java.util.HashMap;
import java.util.Map;

public class Sm2DemoTest {

    public static void main(String[] args) throws Exception {
        // 公钥加密 私钥解密，私钥加签 公钥验签
        String boyd = "{\"data\":\"04dbfbea1d9199694224b9d896d800737e1e18f694d09c61db638e7fe9bbfd8d399d0d572dadd09495d18542f4b800c5c24bf02a25d097e1e2e7fa1a8bc34a135cb42b9a71a66e2e965a7befe899e8e066ebf8061f4c84ce8c732c6331f3a891d75165ec69ffa8beff305ee6a86ef6e5d985a6be2b4ce016490f5a87a1344e72b5b5c8bc896124c3a6a263b560568b556e36f6bd0db79b5a7bb54c739a8c67276f451615b4f35cbce286df6b6fc3bdd8285ddeff3152f27fdc9a17f2c0c54f73a844ac7234ccbd665bcb1cb15cedbaaf2d4ec32de768b09c164384aa331583f168988c4c26beaa9a3ee9e5653c270a370d8db96de41b7cf5ef1b8453a030c4f1fde5108b9b37eedfd929c189dfa2f3af080b91d2a72aa8cb41411004125b3f2755d45930d98c34816b0679894ce0f4c420d68a5ddc700a5309a4cea6335fa7c25f6a00fbda9ec52d1ba1d45c07537c3f7fa7dace1048558e12fc29db9da68d4fccee814248d82c087feb457e009f811a6bdbeea7870010b38c5cbb4b7df6efd2fe8f2373d81926b1cc8cd10e5944088f96cf60ecb531e664a5da582d4be983a9beeaa38817c383dbe8ca14ec1d033f4ced8a17d9458a403de42646745e9c9546e5e05e48f633509fe6bec5fc13f8b2c834ad7e220b0aa31b6d38ab10839ae2438860d6deea5e9bc57b67ebfe21920ffbee75698b239bedc6970e092556d8f4\",\"agentNo\":\"ZFA102258\",\"method\":\"transData\",\"sign\":\"30440220090ef444250e06fbda5f0f6339e4623ff8ec4aae1cf2d2c02febd9f4f8abb42502200d4d0f940e83c4778b1f30a63baadb91287c393d4d16d6c2d72092a5aa5552c6\",\"requestTime\":\"1698051253920\",\"requestId\":\"311da1ffe18e4c5ba937ce9c474a1507\"}";
//        String boyd = "{\"data\":\"049b79f57be6ceddb569fbbe3db0c2711cfc8298ac0572275b95398007547da91b746fc253a8de07602bc7aeda1bb6a7a723f1b7bea42dd58e19f4a9b16b2af2ba4e513061e870b027d35b5cfb2a4f474b29005af4c0a74092831ee832769d027b3e28b3ddd8517ace2cd528cb5b8080bad889943415889b816ce475db99518aa1554621c8db5324f9d706b63c06ebc12f5e43a1b4efb6299c05e5f2cc990e8685c551fb0d509ec489246fee16e60d94f2f0d362991d5cc16509f8faf2f3e8af3d489244f2255c1c8c4e5ce1e34ea2cbcef8a362a17915e1ecbee7f184d682c5d62ce2d0e2d51df3b1a62659d713ed067f28e4d8b665ff7d94e25aa7954cc6bcd6125b7c17e3646b9b21b6ed00ea38e093988d0075ad8a417658b9a67443e8b6eb1b4963ef7d79ac6c39\",\"agentNo\":\"ZFA100079\",\"method\":\"mercData\",\"sign\":\"304502207cdf96dca722f11f7d984bb6c394bae9055d6dc6595408b973040b084a4c4f26022100b262b5f3cd0d974326ed9d40f7d4830eb683e735dd6d0d4a11de4f92a6ce7bda\",\"requestTime\":\"1700028952821\",\"requestId\":\"84829c624e1349129b586dddffd2a40e\"}";
        // 服务商私钥
        String agentPrivateKey = "00B793013116A4126E8D06D7CEF96FF50F6B06BE678E3C6F2067B818A96F58B20A";
        // 服务商公钥
        String agentPublicKey = "95C07B2DC3D8493F59A61C27EF797850E37B2E1F8C90F6D8DB8B9EFC803C8B7F982B7C9732D8CD19B76F72D201D0B32B20C830A789BA05FC743884EB9E35D79B";
        // 平台公钥
        String platformPublicKey = "D46B1945C10DD9E75FDE3FE0A3F1D69D373800B892C85F780EE109BB980F11D36ECD22C277A306513DE34B2C89AF36A635B63DE9A2C3FBC08D0D185F1E3A3DCB";
        JSONObject object = JSONObject.parseObject(boyd);
        String method = object.getString("method");
        String data = object.getString("data");
        String agentNo = object.getString("agentNo");
        String sign = object.getString("sign");

        String signData = StrUtil.format("agentNo={}&method={}&data={}", agentNo, method, data);
        // 平台公钥验签
        SM2 sm2 = SmUtil.sm2(null, "04" + platformPublicKey);
        boolean verifySign = sm2.verifyHex(HexUtil.encodeHexStr(signData), sign);
        System.out.println(verifySign);
        System.out.println(data);
        // 服务商公钥加密
        String content = "{\"amount\":\"199.00\",\"withdrawalFee\":\"0\",\"flowFee\":\"\",\"roseRate\":\"\",\"transTime\":\"2023-10-20 15:28:25\",\"agentNo\":\"ZFA102258\",\"unionpayPosNo\":\"24557168\",\"settleAmount\":\"\",\"fee\":\"1.1000000000\",\"cardType\":\"01\",\"depositFlag\":\"1\",\"transNo\":\"DC202310201528235061\",\"cardNo\":\"6222 **** **** 1366\",\"mercNo\":\"888000000025787\",\"rate\":\"0.55\",\"sn\":\"00003202Q2624750028795\",\"businessType\":\"M015\",\"unionpayMercNo\":\"84773255814000T\"}";
        // 使用公钥加密
        sm2 = SmUtil.sm2(null, "04" + agentPublicKey);
        byte[] encryptedData = sm2.encrypt(content.getBytes(), KeyType.PublicKey);
        String encryptedHex = HexUtil.encodeHexStr(encryptedData);
        System.out.println(encryptedHex);
        // 服务商私钥解密
        sm2 = SmUtil.sm2(agentPrivateKey, null);
        String dataStr = sm2.decryptStr(data, KeyType.PrivateKey);
        String dataStr2 = sm2.decryptStr(encryptedHex, KeyType.PrivateKey);
        System.out.println(dataStr);
        System.out.println(dataStr2);
//
        // 返回数据，推送数据data为空，不需要进行加密，只需要添加签名
        Map<String, String> retData = new HashMap<>();
        retData.put("retCode", "0000");
        retData.put("retMessage", "成功");

        // 服务商私钥加签
        String retSignData = "retCode=" + retData.get("retCode");
        sm2 = SmUtil.sm2(agentPrivateKey, null);
        String retSign = sm2.signHex(HexUtil.encodeHexStr(retSignData));

        retData.put("sign", retSign);
        System.out.println(retSignData);
        System.out.println(retSign);
        // 测试 服务商公钥验签
        sm2 = SmUtil.sm2(null, "04" + agentPublicKey);
        boolean testVerifySign = sm2.verifyHex(HexUtil.encodeHexStr(retSignData), retSign);
        System.out.println(testVerifySign);

//        // 使用Hutool工具库
//        SM2 sm21 = SmUtil.sm2();
//        //这里会自动生成对应的随机秘钥对
//        byte[] privateKey = BCUtil.encodeECPrivateKey(sm21.getPrivateKey());
//        //这里得到未压缩的公钥
//        byte[] publicKey = ((BCECPublicKey) sm21.getPublicKey()).getQ().getEncoded(false);
//        String priKey = HexUtil.encodeHexStr(privateKey).toUpperCase();
//        String pubKey = HexUtil.encodeHexStr(publicKey).toUpperCase();
//        pubKey = pubKey.substring(2);
//        System.out.println(pubKey);
//        System.out.println(priKey);

    }
}
