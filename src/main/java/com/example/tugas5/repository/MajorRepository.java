package com.example.tugas5.repository;

import com.example.tugas5.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findAllByIsDeletedIsFalseOrderByIdMajorAsc();
    Optional<Major> findFirstByIdMajorAndIsDeletedIsFalse(String idMajor);
}
