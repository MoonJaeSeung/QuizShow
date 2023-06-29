package com.example.gp.entity;

import com.example.gp.constant.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Getter
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
}
