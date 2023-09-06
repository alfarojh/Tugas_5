package com.example.tugas5.repository;

import com.example.tugas5.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    @Query("SELECT m FROM Major m WHERE m.isDeleted = false ORDER BY m.id")
    List<Major> findAllNotDeleted();
}
