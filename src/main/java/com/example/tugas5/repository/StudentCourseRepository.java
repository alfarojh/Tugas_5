package com.example.tugas5.repository;

import com.example.tugas5.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    @Query("SELECT sc FROM StudentCourse sc ORDER BY sc.id")
    List<StudentCourse> findAllSorted();
}
