package com.example.rsa1.alipaysign;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pay.brand.channel.sdk.cipher.SM2Utils;
//import com.pay.brand.channel.sdk.cipher.SM4Utils;
//import com.pay.brand.channel.sdk.exception.CipherException;
//import hyf.cipher.SM2Utils;
//import hyf.cipher.SM4Utils;
import com.pay.brand.channel.sdk.cipher.SM2Utils;
import com.pay.brand.channel.sdk.cipher.SM4Utils;
import com.pay.brand.channel.sdk.exception.CipherException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class Hyftest {
//    public String orgPublicKey="046398495A306E0F9AD033D2F5C0FAAA223C18A8C84581332B0371005B5E7F7C73166841D11434F93507A9D8217DB3660E207B081EBF24057F2CC3738AA5FA4110";
    public String orgPublicKey="04BB13046343306655150D36B7ABF0B00884A62E5BC5EBD9330E51D8A21B8EC4497A1B530FF39D6213BF6B249FE2C30E7B6A0AB5F7ADED3E1DCDC11D34AA81EEA3";
    private final String privateKey="15B358F412299A5152DE5C96D1405848FD55FB187E92E883519E41E16B19DAB5";
    private final String publicKey="0416FDA4E907DD25062B4B81F51148F2EFD3269D742310BA1F2F5F154AA14586CF24424C7FD6967C7F24EEB8F0A53894C11C7B4DCA67DDB648B7526930D4D2F3E9";
    String signingReqJson="{\n" +
            "    \"brandNo\": \"7062133861\"\n" +
            "}";
    String reqdata="{\"apiCode\":\"3101\",\"brand\":\"HYF\",\"brandNo\":\"7060127303\",\"sign\":\"3046022100c16cb69adb0514b2162110200a16bcac34cba63ea40af71b02bd1dcbd083ebfc0221009af39fd407b1caa47a686eec600e96e8b210b7bbb1072072830dcba0c7a6569f\",\"prNo\":\"800110000100341200000172224068\",\"prTime\":\"2024-01-19 14:43:09\",\"data\":\"8bcd6358400018907c4baced5282978187586ee7479689c3c1c1634b9b4a61a08141e98077995486ba6f6c2bfcbdea61ead7635883525138e36e0985b7ffdde439211c1ae682a0dd03e2fb3f6fe42f1e6fac00d546c56284497c6c7ab00cb973236b73dc92897b5447433740ee4d565a8ae1b716eec4a082a4d67ae0c9773dc0750f32d7779ea3405f295713ef573a42f79c81899e071da8eaab731216eecafac4a1ef8c7295551eb4e9d34b470bc4e9a25b486c68d6b7b70538c1448f0a8dffe0bea66b5a2294960941fa1a86dbb295ef87ac6c56d6d61a8b0f7f600fb1d8ce0f05954a2cbb7a61874b8c76f0b579e64a305c8f22697144c8cbfe8b7c0853140cf5605a0bc39508cc60cbf8acbabed3c4ee5cdeee1c0b3bd39f9933f85e4d0be1f6c641f11980c35c4de0b933cbad72602fb1e7cb68e167e3ec78004d83420c822d78dbc372d9385628af669a2ac1c7b9e1a319f2aca6d42161f15a5d422b8ed39dde49a461f6cfb02d0941ef0fe4a08e3fe0e9d9c33722fa21ace95a271297fb7a3bf5b4d7ed8df0b3e776a42ef111d5c3d58c327ffd10427fafe602a55c56cd96ff79dff8ec2045f7f107fa6ff968af39d78ec870637783b83b46a4bb0c8f48c6c4a9cc14c865d92e641b254267cdc9f8aacf066b2afbf548492a82c6392b34c0337ffc52221a28eadb4433c563a80207786d8764b2aeabac7d598867a371f5ddfb92378f9c21c4733d15a0453bba7e070a736cc5e8f180fe508204ca12b1e36d0b08942ae931dfd73365b623448d\"}";
//    String url="http://219.143.139.165:20002/hpay-brand-channel-gateway/sign/signIn";
    String url="https://hpay.umpay.com/hpay-brand-channel-gateway/sign/signIn";
    String apiCode="0000";
    @PostMapping("/hyf1")
    public String hyf(@RequestBody Map<String, Object> requestBody)throws Exception {
        String encrypt = SM2Utils.encrypt(orgPublicKey, signingReqJson);
        String sign = this.sign(encrypt);
        System.out.println(sign);
        // 创建一个 HashMap
        Map<String, Object> req = new HashMap<>();
        req.put("apiCode", apiCode);
        req.put("brand", "HYF");
        req.put("brandNo", "7062133861");
        req.put("prNo", generateProcessNo());
        req.put("prTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        req.put("data", encrypt);
        req.put("sign", sign);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRes= objectMapper.writeValueAsString(req);
        System.out.println(jsonRes);
       String response = sendPostRequest(url, jsonRes);
        System.out.println(response);
        // 解析 JSON 字符串
        JSONObject jsonObject = JSONObject.parseObject(response);
        String data = jsonObject.getString("data");
      String decrypt= SM2Utils.decrypt(privateKey, data);
        System.out.println(decrypt);
//        JSONObject jsonObject2 = JSONObject.parseObject(decrypt);
//        String workKey = jsonObject2.getString("workKey");
//        System.out.println(workKey);
//        //解析推送数据
//        JSONObject jsonObject3 = JSONObject.parseObject(reqdata);
//        String res_data = jsonObject3.getString("data");
//        System.out.println(res_data);
//        String decrypt_data = decrypt(workKey, res_data);
//        System.out.println(decrypt_data);
        return decrypt;
    }

    @PostMapping("/hyf2")
    public String sm2_3(@RequestBody Map<String, Object> requestBody)throws Exception {
        String res_data = (String) requestBody.get("req-data");
        String workKey = (String) requestBody.get("workKey");
        System.out.println(res_data);
        String decrypt_data = decrypt(workKey, res_data);
        System.out.println(decrypt_data);
        return decrypt_data;
    }


    private String decrypt(String workKey, String data) {
        try {
            System.out.println(workKey);
            System.out.println(data);
            return SM4Utils.decrypt(workKey, data);
        } catch (Exception e) {
            throw new CipherException("数据解密失败", e);
        }
    }

    private String sign(String data) {
        try {
            return SM2Utils.privateKeySign(this.privateKey, data);
        } catch (Exception e) {
            throw new CipherException("签名失败", e);
        }
    }
    private String generateProcessNo() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
    public static String sendPostRequest(String url, String jsonRequest) {
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

        // 获取响应体
        String responseBody = responseEntity.getBody();

        // 返回响应体
        return responseBody;
    }

}
