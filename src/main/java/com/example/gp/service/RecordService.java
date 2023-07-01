package com.example.gp.service;

import com.example.gp.entity.Record;
import com.example.gp.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    public void save(Record record){
        recordRepository.save(record);
    }
}
