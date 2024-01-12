package com.example.rsa1.alipaysign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@RestController
public class AlipayRsaSign {
    public final static String publicAgentKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaiMoAl2+/42LiCUB+6RngZJtr\n" +
            "ouRCrolijnvtHol6M7peRDFEJkGpvEP852Ns1Zi8Gc/cKUWfSxuxCP2yBYNIh0fe\n" +
            "a1qOpUGBOgrAcXY95xA6TalfbCun8na3Vzqq2J5FAdWN7+tZgrj5pSAj1mlM7WPS\n" +
            "6o6V5+iE9z8ivqAHWwIDAQAB";
    public final static String privteAgentKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJqIygCXb7/jYuIJ\n" +
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

    public static String sendHttpRequest(PushRequestDTO dto) {
        try {
            // 创建RestTemplate实例
            RestTemplate restTemplate = new RestTemplate();

            // 设置请求头信息
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 打印请求体
            System.out.println("Request JSON:\n" + new ObjectMapper().writeValueAsString(dto));
            // 创建HttpEntity对象，封装请求体和请求头
            HttpEntity<PushRequestDTO> requestEntity = new HttpEntity<>(dto, headers);
//            System.out.println(requestEntity);
            // 发送POST请求
            String url = "https://partner-gateway.test.yoliinfo.com/partner/jhzf/api/update";
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();

        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    // 控制器方法修改为：
    @PostMapping("/zipalign")
    public String sm42(@RequestBody AlipayRsaSign requestBody)throws Exception {
//        // 获取当前时间
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        // 格式化输出
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = currentDateTime.format(formatter);
        PushRequestDTO dto = PushRequestDTO.builder()
                .method("FEE")
                .charset("utf-8")
                .signType("RSA2")
                .timestamp("2023-12-18 19:06:47")
                .version("1.0")
                .bizContent("{\"customerNo\":\"98591533877\",\"agentNo\":\"62391038433\",\"list\":[{\"brandCode\":\"YHK\",\"rateAmount\":\"0.55\",\"estreatAmount\":\"0\"},{\"brandCode\":\"GFTH\",\"rateAmount\":\"0.55\",\"estreatAmount\":\"0\"},{\"brandCode\":\"THSK\",\"rateAmount\":\"0.58\",\"estreatAmount\":\"0\"},{\"brandCode\":\"PATH\",\"rateAmount\":\"0.55\",\"estreatAmount\":\"0\"}]}")
                .appId("62391038433").build();
        System.out.println(dto);
        // 打印请求体
        System.out.println("Request JSON:\n" + new ObjectMapper().writeValueAsString(dto));
         String dto2="{\"method\":\"FEE\",\"app_id\":\"62391038433\",\"charset\":\"utf-8\",\"sign_type\":\"RSA2\",\"sign\":null,\"timestamp\":\"2023-12-18 19:06:47\",\"biz_content\":\"{\\\"customerNo\\\":\\\"98591533877\\\",\\\"agentNo\\\":\\\"62391038433\\\",\\\"list\\\":[{\\\"brandCode\\\":\\\"YHK\\\",\\\"rateAmount\\\":\\\"0.55\\\",\\\"estreatAmount\\\":\\\"0\\\"},{\\\"brandCode\\\":\\\"GFTH\\\",\\\"rateAmount\\\":\\\"0.55\\\",\\\"estreatAmount\\\":\\\"0\\\"},{\\\"brandCode\\\":\\\"THSK\\\",\\\"rateAmount\\\":\\\"0.58\\\",\\\"estreatAmount\\\":\\\"0\\\"},{\\\"brandCode\\\":\\\"PATH\\\",\\\"rateAmount\\\":\\\"0.55\\\",\\\"estreatAmount\\\":\\\"0\\\"}]}\",\"version\":\"1.0\"}";
        System.out.println(dto2);
        String sign2 = AlipaySignature.rsaSign(dto2, privteAgentKey, "utf-8", "RSA2");
        String sign = AlipaySignature.rsaSign(new ObjectMapper().writeValueAsString(dto), privteAgentKey, "utf-8", "RSA2");
        System.out.println(sign);
        System.out.println(sign2);
        dto.setSign(sign);
//        // 创建RestTemplate实例
//        String result=sendHttpRequest(dto);
////        String result2=sendHttpRequest(dto2);
//        System.out.println(result);

        String res1="{\\\"data\\\":\\\"049b79f57be6ceddb569fbbe3db0c2711cfc8298ac0572275b95398007547da91b746fc253a8de07602bc7aeda1bb6a7a723f1b7bea42dd58e19f4a9b16b2af2ba4e513061e870b027d35b5cfb2a4f474b29005af4c0a74092831ee832769d027b3e28b3ddd8517ace2cd528cb5b8080bad889943415889b816ce475db99518aa1554621c8db5324f9d706b63c06ebc12f5e43a1b4efb6299c05e5f2cc990e8685c551fb0d509ec489246fee16e60d94f2f0d362991d5cc16509f8faf2f3e8af3d489244f2255c1c8c4e5ce1e34ea2cbcef8a362a17915e1ecbee7f184d682c5d62ce2d0e2d51df3b1a62659d713ed067f28e4d8b665ff7d94e25aa7954cc6bcd6125b7c17e3646b9b21b6ed00ea38e093988d0075ad8a417658b9a67443e8b6eb1b4963ef7d79ac6c39\\\",\\\"agentNo\\\":\\\"ZFA100079\\\",\\\"method\\\":\\\"mercData\\\",\\\"sign\\\":\\\"304502207cdf96dca722f11f7d984bb6c394bae9055d6dc6595408b973040b084a4c4f26022100b262b5f3cd0d974326ed9d40f7d4830eb683e735dd6d0d4a11de4f92a6ce7bda\\\",\\\"requestTime\\\":\\\"1700028952821\\\",\\\"requestId\\\":\\\"84829c624e1349129b586dddffd2a40e\\\"}";
//        System.out.println(result2);
        return res1;
    }


}
