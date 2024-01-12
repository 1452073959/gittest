package com.example.rsa1.alipaysign;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.crypto.asymmetric.SM2;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Sm2Utils {
//    // 公钥加密 私钥解密，私钥加签 公钥验签
//    String boyd = "{\"data\":\"04dbfbea1d9199694224b9d896d800737e1e18f694d09c61db638e7fe9bbfd8d399d0d572dadd09495d18542f4b800c5c24bf02a25d097e1e2e7fa1a8bc34a135cb42b9a71a66e2e965a7befe899e8e066ebf8061f4c84ce8c732c6331f3a891d75165ec69ffa8beff305ee6a86ef6e5d985a6be2b4ce016490f5a87a1344e72b5b5c8bc896124c3a6a263b560568b556e36f6bd0db79b5a7bb54c739a8c67276f451615b4f35cbce286df6b6fc3bdd8285ddeff3152f27fdc9a17f2c0c54f73a844ac7234ccbd665bcb1cb15cedbaaf2d4ec32de768b09c164384aa331583f168988c4c26beaa9a3ee9e5653c270a370d8db96de41b7cf5ef1b8453a030c4f1fde5108b9b37eedfd929c189dfa2f3af080b91d2a72aa8cb41411004125b3f2755d45930d98c34816b0679894ce0f4c420d68a5ddc700a5309a4cea6335fa7c25f6a00fbda9ec52d1ba1d45c07537c3f7fa7dace1048558e12fc29db9da68d4fccee814248d82c087feb457e009f811a6bdbeea7870010b38c5cbb4b7df6efd2fe8f2373d81926b1cc8cd10e5944088f96cf60ecb531e664a5da582d4be983a9beeaa38817c383dbe8ca14ec1d033f4ced8a17d9458a403de42646745e9c9546e5e05e48f633509fe6bec5fc13f8b2c834ad7e220b0aa31b6d38ab10839ae2438860d6deea5e9bc57b67ebfe21920ffbee75698b239bedc6970e092556d8f4\",\"agentNo\":\"ZFA102258\",\"method\":\"transData\",\"sign\":\"30440220090ef444250e06fbda5f0f6339e4623ff8ec4aae1cf2d2c02febd9f4f8abb42502200d4d0f940e83c4778b1f30a63baadb91287c393d4d16d6c2d72092a5aa5552c6\",\"requestTime\":\"1698051253920\",\"requestId\":\"311da1ffe18e4c5ba937ce9c474a1507\"}";
//    // 服务商私钥
    String agentPrivateKey = "3398E1AC35729B4E27F4EABB36CA4DDC5AEB2625078188ACB02A9E10E37CFC86";
//    // 服务商公钥
    String agentPublicKey = "6B81B7E1CFE613C9B90111C0EDAF8A8B1A4403B6380C0FADB0F77D2547538FB20FE354ED3A5D454EA9997E7B007461228955462361E7638D1CC7366C9745C7F7";
//    // 平台公钥
    String platformPublicKey = "D77A6BC176D9B95E6C7C3D446CA37E54B4A14FFAD6D470EC26D22DA049F48FE3784A1A7B8CC28BAAE11F95FF08107625F0346934D21BECB156FD20FAAB1626AF";


    // 公钥加密 私钥解密，私钥加签 公钥验签
    String boyd = "{\"data\":\"04dbfbea1d9199694224b9d896d800737e1e18f694d09c61db638e7fe9bbfd8d399d0d572dadd09495d18542f4b800c5c24bf02a25d097e1e2e7fa1a8bc34a135cb42b9a71a66e2e965a7befe899e8e066ebf8061f4c84ce8c732c6331f3a891d75165ec69ffa8beff305ee6a86ef6e5d985a6be2b4ce016490f5a87a1344e72b5b5c8bc896124c3a6a263b560568b556e36f6bd0db79b5a7bb54c739a8c67276f451615b4f35cbce286df6b6fc3bdd8285ddeff3152f27fdc9a17f2c0c54f73a844ac7234ccbd665bcb1cb15cedbaaf2d4ec32de768b09c164384aa331583f168988c4c26beaa9a3ee9e5653c270a370d8db96de41b7cf5ef1b8453a030c4f1fde5108b9b37eedfd929c189dfa2f3af080b91d2a72aa8cb41411004125b3f2755d45930d98c34816b0679894ce0f4c420d68a5ddc700a5309a4cea6335fa7c25f6a00fbda9ec52d1ba1d45c07537c3f7fa7dace1048558e12fc29db9da68d4fccee814248d82c087feb457e009f811a6bdbeea7870010b38c5cbb4b7df6efd2fe8f2373d81926b1cc8cd10e5944088f96cf60ecb531e664a5da582d4be983a9beeaa38817c383dbe8ca14ec1d033f4ced8a17d9458a403de42646745e9c9546e5e05e48f633509fe6bec5fc13f8b2c834ad7e220b0aa31b6d38ab10839ae2438860d6deea5e9bc57b67ebfe21920ffbee75698b239bedc6970e092556d8f4\",\"agentNo\":\"ZFA102258\",\"method\":\"transData\",\"sign\":\"30440220090ef444250e06fbda5f0f6339e4623ff8ec4aae1cf2d2c02febd9f4f8abb42502200d4d0f940e83c4778b1f30a63baadb91287c393d4d16d6c2d72092a5aa5552c6\",\"requestTime\":\"1698051253920\",\"requestId\":\"311da1ffe18e4c5ba937ce9c474a1507\"}";
//     服务商私钥
//    String agentPrivateKey = "00B793013116A4126E8D06D7CEF96FF50F6B06BE678E3C6F2067B818A96F58B20A";
    // 服务商公钥
//    String agentPublicKey = "6B81B7E1CFE613C9B90111C0EDAF8A8B1A4403B6380C0FADB0F77D2547538FB20FE354ED3A5D454EA9997E7B007461228955462361E7638D1CC7366C9745C7F7";
    // 平台公钥
//    String platformPublicKey = "D77A6BC176D9B95E6C7C3D446CA37E54B4A14FFAD6D470EC26D22DA049F48FE3784A1A7B8CC28BAAE11F95FF08107625F0346934D21BECB156FD20FAAB1626AF";
//    private Map<String, String> cusNos;
//    JSONObject object = JSONObject.parseObject( boyd);
//    String method = object.getString("method");
//    String data = object.getString("data");
//    String agentNo = object.getString("agentNo");
//    String sign = object.getString("sign");

//    String signData = StrUtil.format("agentNo={}&method={}&data={}", agentNo, method, data);
    // 控制器方法修改为：
    @PostMapping("/sm2")
    public String sm2(@RequestBody Map<String, Object> requestBody)throws Exception {
        System.out.println(boyd);
        String reqdata = (String) requestBody.get("req-data");
//        System.out.println(cusNos);
//        // 服务商私钥解密
        SM2 sm2 = SmUtil.sm2(agentPrivateKey, null);
        String dataStr = sm2.decryptStr(reqdata, KeyType.PrivateKey);
        System.out.println(dataStr);
        return dataStr;
    }
    //加签
    @PostMapping("/sm2_sign")
    public String sm2_1(@RequestBody Map<String, Object> requestBody)throws Exception {
        // 服务商私钥加签
        String retSignData = "retCode=0000";
        String reqdata = (String) requestBody.get("req-data");
        //        // 服务商私钥解密
        SM2 sm2 = SmUtil.sm2(agentPrivateKey, null);
        String dataStr = sm2.decryptStr(reqdata, KeyType.PrivateKey);
        System.out.println(dataStr);
        String retSignDatanew = "retCode=0000&data="+dataStr;
        System.out.println(retSignDatanew);
        // 服务商私钥加签
        String retSign = sm2.signHex(HexUtil.encodeHexStr(retSignDatanew));
        // 测试 服务商公钥验签
        sm2 = SmUtil.sm2(null, "04" + agentPublicKey);
        boolean testVerifySign = sm2.verifyHex(HexUtil.encodeHexStr(retSignDatanew), retSign);
        System.out.println(testVerifySign);
        // 转换为 JSON 字符串
        ResponseData responseData = new ResponseData();
        responseData.setSign(retSign);
        String jsonString = responseData.toJsonString();

        return jsonString;
//        return retSign;
//        sm2 = SmUtil.sm2(agentPrivateKey, null);
//        String retSign = sm2.signHex(HexUtil.encodeHexStr(retSignData));
    }
    //加密
    @PostMapping("/sm2_encrypte")
    public String sm2_2(@RequestBody Map<String, Object> requestBody)throws Exception {
        Object reqdata =requestBody.get("req-data");
        System.out.println(reqdata);
        String method = (String) requestBody.get("method");
        // 如果需要将 reqdata 转换为字符串，可以使用如下代码：
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(reqdata);

        // 使用公钥加密
        SM2 sm2 = SmUtil.sm2(null, "04" + platformPublicKey);
        byte[] encryptedData = sm2.encrypt(jsonRequest.getBytes(), KeyType.PublicKey);
        String encryptedHex = HexUtil.encodeHexStr(encryptedData);
        System.out.println("加密结果" + encryptedHex);
        String retSignDatanew = StrUtil.format("agentNo=KA105612&method={}&data={}", method, encryptedHex);
        System.out.println(retSignDatanew);
        sm2 = SmUtil.sm2(agentPrivateKey, null);
        String retSign = sm2.signHex(HexUtil.encodeHexStr(retSignDatanew));
        System.out.println("签名结果" +retSign);
        // 创建一个 HashMap
        Map<String, Object> res = new HashMap<>();
        res.put("data", encryptedHex);
        res.put("sign", retSign);
        String jsonRes= objectMapper.writeValueAsString(res);
        return  jsonRes;
    }

    public class ResponseData {
        private String retCode;
        private String retMessage;
        private String sign;

        // 构造函数、Getter 和 Setter 方法

        public void setSign(String sign) {
            this.sign = sign;
        }
        // 其他方法，例如将对象转换为 JSON 字符串
        public String toJsonString() {
            Map<String, String> data = new HashMap<>();
            data.put("sign", sign);
            return JSON.toJSONString(data);
        }
    }


}
