package com.example.tugas5.repository;

import com.example.tugas5.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.isDeleted = false ORDER BY c.id")
    List<Course> findAllNotDeleted();
}
