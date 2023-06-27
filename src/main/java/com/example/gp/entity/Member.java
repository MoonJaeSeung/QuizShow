package com.example.gp.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nick")
    private String nick;

    public Member(String nick) {
        this.nick = nick;
    }

    public Member() {

    }
}
