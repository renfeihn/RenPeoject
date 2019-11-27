package com.aliyun.toolkit.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
 
    @RequestMapping("/")
    public String sayHello() {
        return "Docker: Hello,World.";
    }
}
