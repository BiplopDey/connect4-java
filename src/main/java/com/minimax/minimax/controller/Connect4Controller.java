package com.minimax.minimax.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connect4")
public class Connect4Controller {

    //get hello world
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
