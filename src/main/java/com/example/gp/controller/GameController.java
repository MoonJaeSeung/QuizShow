package com.example.gp.controller;

import com.example.gp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

    @Autowired
    MemberService memberService;

    @GetMapping("/choice")
    public String showGameChoice() {
        return "choice";
    }

    @PostMapping("/start")
    public String startGame(@RequestParam("nick") String nick) {
        // 게임 시작 로직을 추가하세요.
        memberService.save(nick);
        // 닉네임(nickname)을 이용하여 게임 데이터를 초기화하거나 처리하는 로직을 구현하세요.

        // 게임 선택 화면(choice.html)으로 이동합니다.
        return "redirect:/choice";
    }

    // 다른 컨트롤러 메서드들을 추가할 수 있습니다.
}
