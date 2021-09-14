package com.xujialin.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-13
 */
@RestController
@RequestMapping("/userinfo")
public class UserinfoController {
    @GetMapping("/a")
    public String test(){
        return "aaaa";
    }

}
