package com.example.gp.service;

import com.example.gp.entity.Member;
import com.example.gp.repository.MemberRepository;
import com.example.gp.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member login(String nick, String rawPassword) {
        Member member = memberRepository.findByNick(nick);
        if(member == null){
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }


        if(!passwordEncoder.matches(rawPassword,member.getPassword())){
            throw new IllegalArgumentException("비밀번호를 다시 입력해주세요");
        }

        return member;

    }
}

