package com.example.gp.controller;

import com.example.gp.entity.Member;
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

@RequestMapping
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/choice")
    public String showGameChoice() {
        return "choice";
    }

    @PostMapping("/start")
    public String startGame(@RequestParam("nick") String nick, Model model, HttpServletResponse response) {
        // 사용할 닉네임 설정
        memberService.save(nick);

        // 닉네임 쿠키에 담기
        Cookie nickCookie = new Cookie("nick", nick);
        nickCookie.setMaxAge(3600);  //쿠키 유효시간 설정
        response.addCookie(nickCookie);

        log.info("abcd");

// 게임 선택 화면(choice.html)으로 이동합니다.
        return "redirect:/choice";
    }
}
