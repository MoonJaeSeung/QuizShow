package com.example.gp.service;

import com.example.gp.entity.Member;
import com.example.gp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void save(String nick) {
        Member member = new Member(nick);
        memberRepository.save(member);
    }
}

