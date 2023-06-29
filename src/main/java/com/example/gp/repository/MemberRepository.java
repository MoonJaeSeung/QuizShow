package com.example.gp.repository;

import com.example.gp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByNick(String nick);
}
