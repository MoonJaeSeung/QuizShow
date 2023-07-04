package com.example.gp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(){
        return "addCeleb";
    }

    @GetMapping("/test")
    public String test(){return "/game/game2";}

}
