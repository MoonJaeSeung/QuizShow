package com.example.gp.entity;


import com.example.gp.dto.MemberFormDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nick", unique = true)
    private String nick;

    private String password;


    public static Member createMember(MemberFormDto memberFormDto, String encodedPassword){
        Member member = new Member();
        member.setNick(memberFormDto.getNick());
        member.setPassword(encodedPassword);
        return member;
    }
}
