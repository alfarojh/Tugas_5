package com.example.tugas5.repository;

import com.example.tugas5.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseInterface extends JpaRepository<StudentCourse, Long> {
}
