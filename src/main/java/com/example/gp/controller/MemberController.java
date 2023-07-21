package com.example.gp.controller;

import com.example.gp.security.JwtTokenProvider;
import com.example.gp.dto.MemberFormDto;
import com.example.gp.entity.Member;
import com.example.gp.security.PasswordEncoder;
import com.example.gp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    // 시작 화면 · 임시회원으로 로그인 화면
    @GetMapping(value={"/login","/"})
    public String loginAsTemp(MemberFormDto memberFormDto,Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/nickForm";
    }

    //회원가입 화면
    @GetMapping(value="/join")
    public String joinMember(MemberFormDto memberFormDto,Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/joinForm";
    }

    //로그인 화면
    @GetMapping(value="/login3")
    public String loginByMember(MemberFormDto memberFormDto,Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/loginForm";
    }


    //임시회원으로 로그인
    @PostMapping(value="/login")
    public String loginByTemp(@RequestParam("nick") String nick, Model model, HttpServletResponse response){

        if(StringUtils.isBlank(nick)){
            model.addAttribute("errors", Collections.singletonList("이름에 공백은 불가합니다"));
            return "member/nickForm";
        }

        nick = nick.replaceAll("\\s+", "");

        Cookie nickCookie = new Cookie("nick", nick);
        nickCookie.setMaxAge(3600);  //쿠키 유효시간 설정
        response.addCookie(nickCookie);

        return "choice";
    }

    //공식 회원으로 회원가입
    @PostMapping(value="/register")
    public String register(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            // 유효성 검사 실패시 처리
            return "member/joinForm";
        }

        try {
            String encodedPassword = PasswordEncoder.encode(memberFormDto.getPassword());
            Member member = Member.createMember(memberFormDto, encodedPassword);
            memberService.save(member);
        } catch(IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "member/joinForm";
        }
        return "member/loginForm";
    }



    //공식 회원으로 로그인
    @PostMapping(value="/login2")
    public String loginBymember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, HttpServletResponse response){

        if(bindingResult.hasErrors()){
            return "member/loginForm";
        }

        try {
            String nick = memberFormDto.getNick();
            String password = memberFormDto.getPassword();

            //사용자 검증
            Member member = memberService.login(nick, password);

            if(member ==null){
                return "member/loginForm";
            }

            String token = jwtTokenProvider.createToken(nick);
            // 닉네임 쿠키 생성
            Cookie nickCookie = new Cookie("jwt", token);
            nickCookie.setMaxAge(3600);  //쿠키 유효시간 설정
            response.addCookie(nickCookie);

        } catch(IllegalArgumentException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "member/loginForm";
        }
        return "choice";
    }
}



