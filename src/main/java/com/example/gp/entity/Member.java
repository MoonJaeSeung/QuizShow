package com.example.gp.entity;

import jakarta.persistence.*;

@Entity
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
