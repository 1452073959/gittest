package zcc.testbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
public class BookController {
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

    @RequestMapping("/booksm4")
    public String  sm4()
    {
        String str =  SM4Utils.encryptSm4("朱晨晨");
        System.out.println("加密后的字符串为"+str);
        System.out.println("解密后的字符串为"+SM4Utils.decryptSm4(str));
        String str2=SM4Utils.decryptSm4(str);
        return str2;
    }
}
