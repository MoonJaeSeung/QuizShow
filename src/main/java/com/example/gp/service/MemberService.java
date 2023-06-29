package com.example.gp.service;

import com.example.gp.entity.Member;
import com.example.gp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    @Autowired
    private final MemberRepository memberRepository;

    //로그인
    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {

        Member member = memberRepository.findByNick(nick);

        if(member == null){
            throw new UsernameNotFoundException(nick);
        }

        System.out.println("nick = " + nick);
        return User.builder()
                .username(member.getNick())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}

