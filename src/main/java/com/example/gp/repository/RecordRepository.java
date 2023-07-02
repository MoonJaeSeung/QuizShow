package com.example.gp.repository;

import com.example.gp.entity.Member;
import com.example.gp.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Long> {
    List<Record> findAllByGame(int i);

    @Query("select r from Record r " +
            "where r.game = :gameId " +
            "order by r.score desc ")
    List<Record> find10ByGame(@Param("gameId") int gameId);


}
