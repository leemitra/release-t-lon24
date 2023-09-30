package com.github.moneproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/checkAvailable")
    public String getMapping(){

        return "completed";

    }
}
