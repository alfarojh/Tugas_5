package com.example.tugas5.repositories;

import com.example.tugas5.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findAllByIsDeletedIsFalseOrderByNpmAsc();
    Optional<Student> findFirstByNpmAndIsDeletedIsFalse(String npm);
    Optional<Student> findFirstByNpmContainingOrderByNpmDesc(String npm);
    Page<Student> findAllByIsDeletedIsFalseOrderByNpmAsc(Pageable pageable);
    Page<Student> findAllByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByNpmAsc(String name, Pageable pageable);
    Page<Student> findAllByYearBetweenAndIsDeletedIsFalseOrderByNpmAsc(Integer startYear, Integer endYear, Pageable pageable);
    Page<Student> findAllByIsActiveAndIsDeletedIsFalseOrderByNpmAsc(Boolean isActive, Pageable pageable);
    Page<Student> findAllByMajor_IdMajorAndIsDeletedIsFalseOrderByNpmAsc(String idMajor, Pageable pageable);
}
