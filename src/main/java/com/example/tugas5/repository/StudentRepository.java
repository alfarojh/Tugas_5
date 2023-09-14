package com.example.tugas5.repository;

import com.example.tugas5.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findAllByIsDeletedIsFalseOrderByNpmAsc();
    Optional<Student> findFirstByNpmAndIsDeletedIsFalse(String npm);
    Optional<Student> findFirstByNpmContainingOrderByNpmDesc(String npm);
}
