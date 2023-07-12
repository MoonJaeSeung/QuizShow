package com.example.gp.repository;

import com.example.gp.entity.Celeb;
import com.example.gp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CelebRepository extends JpaRepository<Celeb,Long> {

    public List<Celeb> findAllBySex(int sex);
}
