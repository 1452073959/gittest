package com.example.rsa1.alipaysign;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class Sm2DemoTest2 {

    public static void main(String[] args) throws Exception {
        // 公钥加密 私钥解密，私钥加签 公钥验签
        String boyd = "{\"data\":\"10442140670f7f578f7a2fd2a452dafdf64af01fc0fba2b79231c374bdd60739a0f9114bca493709f29c20b5077d205f0d55866c4b9453f3bd672b2fab088ba11a49e7a358e3b170b42cfa11fc27a79bc69b738e845cc45b0e0f7cb70f5113dbde67362c7b67acca2348bce3bddb61231ab212a5bde9d21412427f8a7fb73600b954cd824\",\"agentNo\":\"KA105612\",\"method\":\"queryMercRate\",\"sign\":\"3045022020eaec06e4f7516d92e2175b6908f18759992fcb93f994edc51968b32d426f77022100f8f549d92f97848212582735700d8f67f72bbfc5adf375a2085c19a232649ce1\",\"requestTime\":1704793658,\"requestId\":\"a788baaff57b9398443d1938256a6c06\"}";
//        String boyd = "{\"data\":\"049b79f57be6ceddb569fbbe3db0c2711cfc8298ac0572275b95398007547da91b746fc253a8de07602bc7aeda1bb6a7a723f1b7bea42dd58e19f4a9b16b2af2ba4e513061e870b027d35b5cfb2a4f474b29005af4c0a74092831ee832769d027b3e28b3ddd8517ace2cd528cb5b8080bad889943415889b816ce475db99518aa1554621c8db5324f9d706b63c06ebc12f5e43a1b4efb6299c05e5f2cc990e8685c551fb0d509ec489246fee16e60d94f2f0d362991d5cc16509f8faf2f3e8af3d489244f2255c1c8c4e5ce1e34ea2cbcef8a362a17915e1ecbee7f184d682c5d62ce2d0e2d51df3b1a62659d713ed067f28e4d8b665ff7d94e25aa7954cc6bcd6125b7c17e3646b9b21b6ed00ea38e093988d0075ad8a417658b9a67443e8b6eb1b4963ef7d79ac6c39\",\"agentNo\":\"ZFA100079\",\"method\":\"mercData\",\"sign\":\"304502207cdf96dca722f11f7d984bb6c394bae9055d6dc6595408b973040b084a4c4f26022100b262b5f3cd0d974326ed9d40f7d4830eb683e735dd6d0d4a11de4f92a6ce7bda\",\"requestTime\":\"1700028952821\",\"requestId\":\"84829c624e1349129b586dddffd2a40e\"}";
        // 服务商私钥
        String agentPrivateKey = "00B793013116A4126E8D06D7CEF96FF50F6B06BE678E3C6F2067B818A96F58B20A";
        String agentPrivateKey2 = "3398E1AC35729B4E27F4EABB36CA4DDC5AEB2625078188ACB02A9E10E37CFC86";
        String agentPublicKey2 = "6B81B7E1CFE613C9B90111C0EDAF8A8B1A4403B6380C0FADB0F77D2547538FB20FE354ED3A5D454EA9997E7B007461228955462361E7638D1CC7366C9745C7F7";
//    // 平台公钥

        // 服务商公钥

        String agentPublicKey = "95C07B2DC3D8493F59A61C27EF797850E37B2E1F8C90F6D8DB8B9EFC803C8B7F982B7C9732D8CD19B76F72D201D0B32B20C830A789BA05FC743884EB9E35D79B";
        // 平台公钥
        //    // 平台公钥
        String platformPublicKey2 = "D77A6BC176D9B95E6C7C3D446CA37E54B4A14FFAD6D470EC26D22DA049F48FE3784A1A7B8CC28BAAE11F95FF08107625F0346934D21BECB156FD20FAAB1626AF";
        String platformPublicKey = "D46B1945C10DD9E75FDE3FE0A3F1D69D373800B892C85F780EE109BB980F11D36ECD22C277A306513DE34B2C89AF36A635B63DE9A2C3FBC08D0D185F1E3A3DCB";
        JSONObject object = JSONObject.parseObject(boyd);
        String method = object.getString("method");
        String data = object.getString("data");
        String agentNo = object.getString("agentNo");
        String sign = object.getString("sign");

        String signData = StrUtil.format("agentNo={}&method={}&data={}", agentNo, method, data);

        // 平台公钥验签
        SM2 sm2 = SmUtil.sm2(null, "04" + platformPublicKey2);
        boolean verifySign = sm2.verifyHex(HexUtil.encodeHexStr(signData), sign);
//        System.out.println(verifySign);
//        System.out.println(data);
        // 服务商公钥加密
//        String content = "{\"amount\":\"199.00\",\"withdrawalFee\":\"0\",\"flowFee\":\"\",\"roseRate\":\"\",\"transTime\":\"2023-10-20 15:28:25\",\"agentNo\":\"ZFA102258\",\"unionpayPosNo\":\"24557168\",\"settleAmount\":\"\",\"fee\":\"1.1000000000\",\"cardType\":\"01\",\"depositFlag\":\"1\",\"transNo\":\"DC202310201528235061\",\"cardNo\":\"6222 **** **** 1366\",\"mercNo\":\"888000000025787\",\"rate\":\"0.55\",\"sn\":\"00003202Q2624750028795\",\"businessType\":\"M015\",\"unionpayMercNo\":\"84773255814000T\"}";
        String content = "{\"mercNo\": \"602000000058457\"}";
        // 使用公钥加密
        sm2 = SmUtil.sm2(null, "04" + platformPublicKey2);
        byte[] encryptedData = sm2.encrypt(content.getBytes(), KeyType.PublicKey);
        String encryptedHex = HexUtil.encodeHexStr(encryptedData);
        System.out.println("加密结果" + encryptedHex);


////        // 服务商私钥解密
//        sm2 = SmUtil.sm2(agentPrivateKey2, null);
////        String dataStr = sm2.decryptStr(data, KeyType.PrivateKey);
//        String dataStr2 = sm2.decryptStr(encryptedHex, KeyType.PrivateKey);
////        System.out.println(dataStr);
//        System.out.println(dataStr2);
////
//        // 返回数据，推送数据data为空，不需要进行加密，只需要添加签名
//        Map<String, String> retData = new HashMap<>();
//        retData.put("retCode", "0000");
//        retData.put("retMessage", "成功");

        String retSignData = StrUtil.format("agentNo=KA105612&method=queryMercRate&data={}", encryptedHex);
//        System.out.println(retSignData);
        // 服务商私钥加签
//        String retSignData = "retCode=" + retData.get("retCode");
        sm2 = SmUtil.sm2(agentPrivateKey2, null);
        String retSign = sm2.signHex(HexUtil.encodeHexStr(retSignData));

//        retData.put("sign", retSign);
        System.out.println("待签名字符串" + retSignData);
        System.out.println("签名" + retSign);
        String url = "https://yfb.cardinfo.com.cn/data/openapi";


        // 测试 服务商公钥验签
        sm2 = SmUtil.sm2(null, "04" + agentPublicKey2);
        boolean testVerifySign = sm2.verifyHex(HexUtil.encodeHexStr(retSignData), retSign);
        System.out.println(testVerifySign);
        // 创建一个 HashMap
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("data", encryptedHex);
        hashMap.put("agentNo", "KA105612");
        hashMap.put("method", "queryMercRate");
        hashMap.put("sign", retSign);
        hashMap.put("requestTime", System.currentTimeMillis());
        hashMap.put("requestId", "f40538f5596d0737bb7f56bf38dd7129");

        // 使用 Jackson ObjectMapper 将 HashMap 转换为 JSON 字符串

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(hashMap);
        ResponseEntity<String> response = sendPostRequest(url, jsonRequest);
        System.out.println(jsonRequest);
        System.out.println(response);
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


    public static ResponseEntity<String> sendPostRequest(String url, String jsonRequest) {
        // 创建RestTemplate对象
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构造HttpEntity对象，包含请求头和请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);

        // 发起POST请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 返回响应
        return responseEntity;
    }


}
