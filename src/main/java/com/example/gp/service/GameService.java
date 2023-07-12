package com.example.gp.service;

import com.example.gp.entity.Celeb;
import com.example.gp.entity.Word;
import com.example.gp.repository.CelebRepository;
import com.example.gp.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    CelebRepository celebRepository;

    public List<Word> findAllWord() {
        List<Word> all = wordRepository.findAll();
        return all;
    }

    public List<Celeb> findAllCeleb() {
        List<Celeb> all = celebRepository.findAll();
        return all;
    }

    public List<Celeb> findCeleb(int sex) {
        List<Celeb> celeb = celebRepository.findAllBySex(sex);
        return celeb;
    }

    public Word addWord(Word word) {
        Word save = wordRepository.save(word);
        return save;
    }

    public Word findWordById(Long wordId) {
        Word findWord = wordRepository.findWordById(wordId);
        return findWord;
    }



}
