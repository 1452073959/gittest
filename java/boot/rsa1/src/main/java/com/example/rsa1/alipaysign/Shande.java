package com.example.rsa1.alipaysign;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class Shande {
    private static final Object AGENT_ID = "200";
    private static final String SIGN_KEY = "2681250.ztkj";
    static String PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCARnTgvLUCjxxbMHoKcdJUc/z0jxGeBWBswXhYgNca129wE81cRtmrn0mQzP47TQ/yTf5r9GpLZgfSuKWSzK1gfU7IAuSQ9QYSfd1fx2JFbqtJc6XXIPZj9dLml5XPui1kr3Jgfo6aMsPERwYeGYl8WIxNbmIbONuc0E74pNpB5oemPz+x5bDgX+JBIeXZs5VdposlRtXD5hxFMhriCdSluJFogOKjOC58vvePHiSHGPs7pGzLyHp1zAMxsMLA8hEuXAxExtLpePWQ8MVqp3CvygAyd3hK1LpQtu+uUFmz7n7vTXw44W0FDHOJ/i4D5yTUvaGgXmFOYi0i8Xde5sdzAgMBAAECggEAG/bAHgolb8RhCyQbRtGfZ5fNikbyMT+80JiwsqKLwic6/PTkLPypETftYqq9tZHMKyeDkmc7EOrSQ8bLGaCTH1jR5tpyl4NC6p8TtoCR1M8WejHC8o4WCJ6bdnePSjStFqkExzJvxf/RG6XcGI26oifvliW9L3AKuDnVLXFKv1jCEsHiwRF2aF50BvY5ZJ5Q0+F0IbXljXgtdz7fcF+KnQt4kQiKKdSB3BtogHBsKY1Uk4I+48w3x7YvXaNFSXGY62wVyC6wHskoeS8zVfHeqaZlstTgo3CN+iJzWdbiFl2pJ7YwywRsKBB2YSKyUEo7oaOu9vewWPJFE4C/4l8lAQKBgQC/aMdYMfeuTKZz4W2+fjwL7NmwPBIp/31Z6Zf7XBDCIbEDoHW5kIvla3oBT9UWzCeeKG8B5wBvW6U/kT/reglNLEdKVjS+fdEX+9nUA8Wnh/vnFwNxFUPoq3Eq/VuidcavLCwUONUoR5k6sCOmlSpzdveenRTWORIrO4oKVyrK8wKBgQCrj7uATv2gWfnSnqoYXMe7STs7bzLtnFk5ogh89pu3v6R43JN/eAHjGc/IXGdzSshmXBFpkSLu2JtJOrmAV5NObWTrtnpHWqDWP2ROJRT8UjLrKQ+WUVXzFj2EZRXUjYVDuS351Y8/sj1w6CtHdtOO4C2IjALCkKtoolTtXAoxgQKBgQCw3hMFzfY+GxncDjwoP+Ega9WjYX6vTCP6Rz1myOW7XQKChW0C8Swz7CIqwCf5DuC05kvdCCWAwMBrgr37yyJ5Kycc65eFRiFF9jSMEog7jA4atUs63RmJD5Jc1QwtjILxLvH0JafhtndCoavvq/o0A4RADopIgSfmxveBIpaqPQKBgDcasYTi2j/45m8qG0Q7+MNFIEtUyizbvSTv0H4iz51TZsaTQqT8rM5+6ehXFbtIV9nY1Jbt0SSaWjZJMG/ysRPNu06ii+ADUgm+zr7y1iy9UjGKPMFxWoP7rsKpnTtqQVWhUw0d9SbmXym2yL/pgMYxZy6mmfRYd1XiXhjnKTcBAoGAY4HjYP6stvJnGOuR8jTfTV8msmzm4Blz4lMSfZi+vRTGwgd7snX223WjbpBNcOBdKaIIr3Pjh4GVPHhKbhCch7Z3FT0MjoOxIZiitICppmVMIEcfvIfr4KiaU0wlQ+TsI/zCBk4uxpNQnd934Svmg14tVz8u8FoAtCG5ri5PtR4=";
    static String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYTO/1R5IzCYLGT3bv74/69xqlnYgLavTE8eq/lJz7eQ4Gl1IBpOzi1oWwd1gEmHdc1nSByQvLWsGmqdXUUh6f7jfrOz3amHgTe82YK4VlFi8gyvci2rwGMgzTH88qYBo0D9NkQa4JYFZytzSIF6EFdBnYPBob+ME2dwY5dZ0J+wIDAQAB";
    public static void main(String[] args) {
        encrypt2();
    }
    public static String encrypt2() {
        String data = "{\"deviceNo\":\"J10500639155\",\"consumeType\":\"01\",\"rates\":[{\"type\":\"debit\",\"rate\":0.006,\"singleFee\":\"3000\",\"highestFee\":\"2500\"},{\"type\":\"debit\",\"rate\":0.006,\"singleFee\":\"3000\"},{\"type\":\"ali_pay\",\"rate\":0.006,\"singleFee\":\"3000\"},{\"type\":\"wei_xin\",\"rate\":0.006,\"singleFee\":\"3000\"},{\"type\":\"union\",\"rate\":0.006,\"singleFee\":\"3000\"}],\"agentId\":\"200\",\"version\":\"v1\",\"timestamp\":1707115521000}";
        RSA rsa = SecureUtil.rsa(PRIVATE_KEY, PUBLIC_KEY);
        JSONObject body = JSON.parseObject(data);

        body.put("agentId", AGENT_ID);
        body.put("version", "v1");
        body.put("timestamp", "1707117378000");
        // 加签
        System.out.println("待加签:"+body);
        String signature = sign(body, SIGN_KEY);
        body.put("signature", signature);
        System.out.println("待加密:"+body.toJSONString());
        String encryptData = rsa.encryptBase64(body.toJSONString(), KeyType.PublicKey);

        body.put("data", encryptData);
        body.remove("activityId");
        body.remove("deviceNo");

       String res= sendPostRequest("https://axf.sandpay.com.cn/channel//agent/device/update_rate",body.toJSONString());
        System.out.println(res);
        return res;

    }

    public static String sign(Map<String, Object> params, String aesKey) {
        params.remove("signature");
        // 排序后拼接参数
        String signString = MapUtil.joinIgnoreNull(MapUtil.sort(params), "&", "=");
        System.out.println("签名字符串:"+signString + aesKey);
        // 采用md5加密
        return SecureUtil.md5(signString + aesKey);
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
