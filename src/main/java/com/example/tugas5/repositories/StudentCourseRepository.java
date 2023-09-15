package com.example.tugas5.repositories;

import com.example.tugas5.models.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findAllByIsDeletedIsFalseOrderByCreatedAtDesc();
    Page<StudentCourse> findAllByIsDeletedIsFalseOrderByCreatedAtDesc(Pageable pageable);
    Optional<StudentCourse> findFirstByIdStudentCourseAndIsDeletedIsFalse(String idStudentCourse);
    Optional<StudentCourse> findFirstByIdStudentCourseContainingOrderByIdStudentCourseDesc(String idStudentCourse);
}
