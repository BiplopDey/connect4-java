package com.minimax.minimax.infrastructure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class apiController {

    @GetMapping
    public String health() {
        return "Is healthy";
    }

}


