package com.xujialin.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-13
 */
@Slf4j
@RestController
@RequestMapping("/userinfo")
public class UserinfoController {

    @PostMapping("/a")
    public String test(@RequestBody Map<String,String> map) {
        System.out.println(map);
        return "aaaa";
    }

}
