package com.example.gp.repository;

import com.example.gp.entity.Celeb;
import com.example.gp.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word,Long> {
    Word findWordById(Long wordId);
}
