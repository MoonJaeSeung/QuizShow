package com.example.gp.controller;

import com.example.gp.dto.CelebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class GameController {

    @GetMapping("/game/word")
    public String showGameWord(){
        return "/game/gameWord";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        return "redirect:/admin";
    }

    @PostMapping("/celeb/add")
    public String addCeleb(@RequestParam("itemImgFile") MultipartFile file, @ModelAttribute CelebDto celebDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/admin";
        }

        System.out.println("celebDto = " + celebDto);
        return "loginForm";
    }
}
