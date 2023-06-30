package com.example.gp.controller;

import com.example.gp.entity.Member;
import com.example.gp.service.AdminService;
import com.example.gp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @GetMapping(value="/login")
    public String loginMember(){
        return "/loginForm";
    }

    @PostMapping(value="/login")
    public String login(@RequestParam("nick") String nick, Model model, HttpServletResponse response){

        Cookie nickCookie = new Cookie("nick", nick);
        nickCookie.setMaxAge(3600);  //쿠키 유효시간 설정
        response.addCookie(nickCookie);

        return "choice";


    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/loginForm";
    }

}
