package com.example.gp.repository;

import com.example.gp.entity.Celeb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebRepository extends JpaRepository<Celeb,Long> {
}
