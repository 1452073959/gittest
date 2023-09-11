package com.example.boot2.comtroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class BookController {
    @GetMapping
    public String getUserById() {
        System.out.println(123456);
        return "测试一下";
    }
}
