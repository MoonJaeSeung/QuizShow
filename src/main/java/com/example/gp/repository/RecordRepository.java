package com.example.gp.repository;

import com.example.gp.entity.Member;
import com.example.gp.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record,Long> {
}
