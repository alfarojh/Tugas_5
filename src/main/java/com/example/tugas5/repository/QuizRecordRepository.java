package com.example.tugas5.repository;

import com.example.tugas5.model.QuizRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRecordRepository extends JpaRepository<QuizRecord, Long> {
    @Query("SELECT qr FROM QuizRecord qr ORDER BY qr.id")
    List<QuizRecord> findAllSorted();
}
