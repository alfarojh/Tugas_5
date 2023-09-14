package com.example.tugas5.repository;

import com.example.tugas5.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIsDeletedIsFalseOrderByNameAsc();
    Optional<Course> findFirstByIdCourseAndIsDeletedIsFalse(String idCourse);
    Optional<Course> findFirstByIdCourseContainingIgnoreCaseOrderByIdCourseDesc(String idCourse);
}
