package com.example.tugas5.repositories;

import com.example.tugas5.models.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findAllByIsDeletedIsFalseOrderByIdMajorAsc();
    Optional<Major> findFirstByIdMajorAndIsDeletedIsFalse(String idMajor);
    Page<Major> findAllByIsDeletedIsFalseOrderByIdMajorAsc(Pageable pageable);
}