package com.example.tugas5.repository;

import com.example.tugas5.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findAllByIsDeletedIsFalseOrderByCreatedAtDesc();
    Optional<StudentCourse> findFirstByIdAndIsDeletedIsFalse(Long id);
}
