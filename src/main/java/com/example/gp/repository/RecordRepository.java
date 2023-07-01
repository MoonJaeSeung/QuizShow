package com.example.gp.repository;

import com.example.gp.entity.Member;
import com.example.gp.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Long> {
    List<Record> findAllByGame(int i);
}
