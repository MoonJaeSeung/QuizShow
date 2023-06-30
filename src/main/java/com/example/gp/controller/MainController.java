package com.example.gp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "loginForm";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}
