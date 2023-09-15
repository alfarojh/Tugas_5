package com.example.tugas5.repositories;

import com.example.tugas5.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    // Mencari Mahasiswa berdasarakan NPM Mahasiswa.
    Optional<Student> findFirstByNpmAndIsDeletedIsFalse(String npm);
    // Mencari Mahasiswa yang paling baru berdasarkan NPM Mahasiswa.
    Optional<Student> findFirstByNpmContainingOrderByNpmDesc(String npm);
    // Mencari daftar Mahasiswa.
    Page<Student> findAllByIsDeletedIsFalseOrderByNpmAsc(Pageable pageable);
    // Mencari daftar Mahasiswa berdasarkan nama Mahasiswa.
    Page<Student> findAllByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByNpmAsc(String name, Pageable pageable);
    // Mencari daftar Mahasiswa berdasarkan rentang tahun masuk Mahasiswa.
    Page<Student> findAllByYearBetweenAndIsDeletedIsFalseOrderByNpmAsc(Integer startYear, Integer endYear, Pageable pageable);
    // Mencari daftar Mahasiswa berdasarkan status aktif Mahasiswa.
    Page<Student> findAllByIsActiveAndIsDeletedIsFalseOrderByNpmAsc(Boolean isActive, Pageable pageable);
    // Mencari daftar Mahasiswa berdasarkan ID Jurusan.
    Page<Student> findAllByMajor_IdMajorAndIsDeletedIsFalseOrderByNpmAsc(String idMajor, Pageable pageable);
}
