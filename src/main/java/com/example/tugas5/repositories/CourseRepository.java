package com.example.tugas5.repositories;

import com.example.tugas5.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIsDeletedIsFalseOrderByNameAsc();
    Optional<Course> findFirstByIdCourseAndIsDeletedIsFalse(String idCourse);
    Optional<Course> findFirstByIdCourseContainingIgnoreCaseOrderByIdCourseDesc(String idCourse);
    Page<Course> findAllByIsDeletedIsFalseOrderByNameAsc(Pageable pageable);
    Page<Course> findAllByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByNameAsc(String name, Pageable pageable);
    Page<Course> findAllByIsActiveAndIsDeletedIsFalseOrderByNameAsc(Boolean isActive, Pageable pageable);
}
