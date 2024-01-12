package com.example.rsa1.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import com.example.rsa1.utils.RSAUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookController {
    private static String contentType = "application/json";
    private static String url = "https://agtopen.awillpay.com/";
    private static String clientPublicStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqAetEuZujXf9ruXJtGlXt9isXZy/iLg2O3bz95Ms1Gaolha+WicdYLhVqXHQthAXl7d4nMlRPkm0OGES21uAsrUa1L9vbLKmS/G8zFfCPBSeEY57kT292WdBzebHPYyJX7b/VV0GCBDbpzlgQN7v8/RURAXhY9XM4eEOQAKog4Bz1JpDyLloRoKkdQGs4qmQi5GYKrrSC6E8c0d4h2nXhEmMcCZwPiLOC3pQ+zhk+fkcWR7pEZKZ2t/cav3rslB6KxNWIrIE2K6Da/9+Ldd1PrSpm8r66GbxDWehi2Yy4Cs4a0wiPis+ltQUArfrwoTgxRiwYgFQo287NoFlA+3hrQIDAQAB";
    private static String clientPrivateStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDINt+gwOH+9a+ZV65OfS7mDVSCTEe8mvwN/SKfY3DaAHKcpiLdlXFTCKLtu6VxK7c6VwOpo6B9b65w/oU7XWPvOfeD0e4Mzn4BX3Zukl2OZyjVMG/1VdUY5sShWF/RT9rqjph0K94Ki0SKyme426GzRIcpGVIRV2jzbEbcGNi6bnci2XAqrOt9xAHs3z+LKEx5rujogK+VuS1bHi0C0YB5X2+d6nc8hHHBFPpZyiRYmqk+YZzSB+NeC+AI7zRwaZNj8ifH9PrQ3ziJfpTk2w+MIKdPfiWA/IblIunXStzxpIFKAcxzCYmVL51sp2Sd9NZQw/zMC5BPXlVygPw91PqBAgMBAAECggEAMnP5iKXyjXBWLts0lpzfK6PiTu9wCGw55kCK8AV8E8OXHjiTgqk9JIKBpEzsFQfCJZZ12tLcB6AUIm9RHbOcElXWNMa7KUkD4c6vpo+FufPvC0IohHd7usm0dUAy7SklboQ+kM/ZJWkQ/bShzKi0J9u1/7pcvvf6/5SiiqZMPzZSs62zTeYYIrSQ0yIGnyaiMUS8VUHCwnDpZ2pHc4htjYU+a/O1uIsUhOpM+FGwXqTwnsbkZHSWG0ew9a9zlb3/DLuVl32WxQf+tFWwZ8/gCNU50XeSvCWhi8nwSK5pAvKg4yFxVmDQG5f90KHVJeOSg4oanSbVuX6/+PBIISFtAQKBgQDzQLPZUKKGYzzCbZk26hmQMrAqWuA1R71pNLJSHVxIVDd8COecz7h+BdRBNxZowx94OJlp9sY23WRekL8sqX2ozyYF8VK6e0RFNYc0f2uZr3+4XWvKuuAV1sl32QHOyGLqeExJImd9wmPkzoOClzGH8kZmlwO4YvyleH5umTwL0QKBgQDStMzMqYAn7I6Kcw+g+IatZdfjraqFreTD2bobQndTfWQok8Dmupl+wrI1ThBXRXm2/LS6CSvHEs0ratG/rbkrSW/ZboqXuEH2iBccenNSkgiUMQ/bFJpQowcuFswbJT88S/X1h5EAw84CTNO699MAoSqjYQv0AZRmTg2C8NqfsQKBgDG9hEkmpOUn+6+iK5dADVQgGUe/52LdtYyCuwfxewJLPkE9rOayzv3uATYIpqtrYs75e91gGa30XWa84Gqoicpv+U1zyD/bnt0sEeaZO45khZBUggslZVbx4aaBGE6JSTi6upal01Wy3yxVUizs77LlxqGJtbgJfbH0Tp4c9VtRAoGANH7nJ3Zzf37oZIYWNry0kS72grsZ6QRSIpl0TKZapGpxMcreHJ4Q8AwB6lXwrE2a7YQ4YsE2Lvr4MwECEQdvQuGhBEMONI6roIh3eZjocoH/sSwoDyUq7Tww5cK4R2sYPiWOugV0kwQ1p00M02A7KcT9oobjO3/JQVPg+eaYEYECgYEA7uBcunbIPJ4Vd7s0sbC4v1rwkPHIFaqFHFjn0r63Ga+8bxWBUq/rM7kSG5gfMLhkuFkUzxb0YbzP6igBxD8NyC0x5tj2M7I7KetFH/ZDgn3Gkm35J74MR9PpptTsDNsoIy7r2LyTbbLGl1si7kFTZNcQtfBGqGbsVmN6vW8qrsY=";
    private Map<String, String> cusNos;
    @Value("${lesson}")
    public String lesson;
    @Value("${server.port}")
    public String Port;
    @Value("${enterprise.subject[0]}")
    public String subject;

    @Autowired
    public Environment env1;



    @RequestMapping("/book/{name}")

//    @ResponseBody
    public String index(@PathVariable("name") String name) {
        System.out.println(lesson);
        System.out.println(Port);
        System.out.println(subject);
        System.out.println(env1.getProperty("lesson"));

        ArrayList<String> list2=new ArrayList<>();
        list2.add("1111");
        list2.add("你还好吗");
        list2.add(0,"22");
        list2.add(1,"22");
//        list2.toArray();
        System.out.println(list2);
        System.out.println(list2);
        return "Hello " + name;
    }



    // 控制器方法修改为：
    @PostMapping("/rsa")
    public JSONObject sm42(@RequestBody BookController requestBody) {
        Map<String, String> cusNos = requestBody.getCusNos();
        System.out.println(cusNos);
        return getMerInfo(cusNos);
    }
    public Map<String, String> getCusNos() {
        return cusNos;
    }

    public static JSONObject getMerInfo(Map<String, String> cusNos){
        try {
            //请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("longtime", String.valueOf(System.currentTimeMillis()));
//            params.put("cusNo", cusNo);
            // 将 cusNos 转换为键值对
            params.putAll(cusNos);
            String paramsStr = JSON.toJSONString(params);
            System.out.println(paramsStr);
            //封装加密和签名
            String reqBody = RSAUtils.encrypt(paramsStr, clientPublicStr, RSAUtils.KEY_ALGORITHM);
            String reqSign = RSAUtils.sign(paramsStr.getBytes(), clientPrivateStr);
            System.out.println(reqBody);
            System.out.println(reqSign);
            //接口请求
            JSONObject data = new JSONObject();
            data.put("body", reqBody);
            data.put("sign", reqSign);
            return data;

        } catch (Exception e) {
            return null;
        }
    }
}
