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
    public String startGame(@RequestParam("nick") String nick, Model model) {
        // 사용할 닉네임 선택
        memberService.save(nick);
        // 닉네임(nickname)을 이용하여 게임 데이터를 초기화하거나 처리하는 로직을 구현하세요.
        List<Member> members = memberService.getAllMembers(); // 회원 리스트를 가져오는 메서드
        model.addAttribute("members", members); // 모델에 회원 리스트를 추가


// 게임 선택 화면(choice.html)으로 이동합니다.
        return "choice";
    }
}
