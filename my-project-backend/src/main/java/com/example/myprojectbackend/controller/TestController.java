package com.example.myprojectbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController
 * Package:com.example.myprojectbackend.controller
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 10:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/hello")
    public String test(){
        return "Hello world";
    }
}
