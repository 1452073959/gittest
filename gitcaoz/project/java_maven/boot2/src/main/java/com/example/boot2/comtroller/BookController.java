package com.example.boot2.comtroller;

import jdk.internal.util.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class BookController {

    @Value("${port}")
    private String server;

    @Autowired
    public Environment env;
    @GetMapping

    public String getUserById() {
        System.out.println(123456);
        System.out.println(env);
        return "测试一下";
    }
}
