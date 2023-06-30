package com.example.gp.service;

import com.example.gp.entity.Word;
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

    public List<Word> findAll() {
        List<Word> all = wordRepository.findAll();
        return all;
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
