package com.example.tugas5.repositories;

import com.example.tugas5.models.QuizRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRecordRepository extends JpaRepository<QuizRecord, Long> {

}
