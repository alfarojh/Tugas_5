package com.example.tugas5.repository;

import com.example.tugas5.model.QuizRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRecordInterface extends JpaRepository<QuizRecord, Long> {
}
