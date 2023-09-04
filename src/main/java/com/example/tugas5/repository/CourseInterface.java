package com.example.tugas5.repository;

import com.example.tugas5.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseInterface extends JpaRepository<Course, Long> {
}
