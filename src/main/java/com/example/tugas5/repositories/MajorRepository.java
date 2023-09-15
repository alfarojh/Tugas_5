package com.example.tugas5.repositories;

import com.example.tugas5.models.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    // Mencari Jurusan berdasarkan ID Jurusan.
    Optional<Major> findFirstByIdMajorAndIsDeletedIsFalse(String idMajor);
    // Mencari daftar Jurusan.
    Page<Major> findAllByIsDeletedIsFalseOrderByIdMajorAsc(Pageable pageable);
    // Mencari daftar Jurusan berdasarkan nama Jurusan.
    Page<Major> findAllByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByNameAsc(String name, Pageable pageable);
}
