package com.example.tugas5.repositories;

import com.example.tugas5.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Mencari Mata Kuliah berdasarkan ID Mata Kuliah.
    Optional<Course> findFirstByIdCourseAndIsDeletedIsFalse(String idCourse);

    // Mencari Mata Kuliah yang paling baru ditambahkan berdasarkan ID Mata Kuliah.
    Optional<Course> findFirstByIdCourseContainingIgnoreCaseOrderByIdCourseDesc(String idCourse);

    // Mencari daftar Mata Kuliah.
    Page<Course> findAllByIsDeletedIsFalseOrderByNameAsc(Pageable pageable);

    // Mencari daftar Mata Kuliah berdasarkan nama Mata Kuliah.
    Page<Course> findAllByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByNameAsc(String name, Pageable pageable);

    // Mencari daftar Mata Kuliah berdasarkan status aktif Mata Kuliah.
    Page<Course> findAllByIsActiveAndIsDeletedIsFalseOrderByNameAsc(Boolean isActive, Pageable pageable);
}
