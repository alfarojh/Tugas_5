package com.example.tugas5.repository;

import com.example.tugas5.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s WHERE s.isDeleted = false ORDER BY s.npm")
    List<Student> findAllNotDeleted();
}
