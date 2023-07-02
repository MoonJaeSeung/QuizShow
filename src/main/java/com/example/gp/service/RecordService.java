package com.example.gp.service;

import com.example.gp.entity.Record;
import com.example.gp.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    public void save(Record record){
        recordRepository.save(record);
    }

    public List<Record> find10ByGame(int i) {
        List<Record> allByGame = recordRepository.find10ByGame(i);
        List<Record> top10 = new ArrayList<>();
        for(int n=0; n<allByGame.size(); n++){
            top10.add(allByGame.get(n));
            if(top10.size() == 10){
                return top10;
            }
        }
        return top10;
    }
}
