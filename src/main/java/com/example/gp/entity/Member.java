package com.example.gp.entity;

import com.example.gp.constant.Role;
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

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String nick, String password, Role role) {
        this.nick = nick;
        this.password = password;
        this.role = role;
    }

    public static Member createMember(MemberFormDto memberFormDto, String encodedPassword){
        Member member = new Member();
        member.setNick(memberFormDto.getNick());
        member.setPassword(encodedPassword);
        member.setRole(Role.USER);
        return member;
    }
}
