package com.example.tugas5.repositories;

import com.example.tugas5.models.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    // Mencari relasi Mahasiswa dan Mata Kuliah berdasarkan code transaksi.
    Optional<StudentCourse> findFirstByCodeAndIsDeletedIsFalse(String code);
    // Mencari relasi Mahasiswa dan Mata Kuliah yang paling baru berdasarkan code transaksi.
    Optional<StudentCourse> findFirstByCodeContainingOrderByCodeDesc(String code);
    // Mencari daftar relasi Mahasiswa dan Mata Kuliah.
    Page<StudentCourse> findAllByIsDeletedIsFalseOrderByCreatedAtDesc(Pageable pageable);
    // Mencari daftar relasi Mahasiswa dan Mata Kuliah berdasakan NPM Mahasiswa.
    Page<StudentCourse> findAllByStudent_NpmAndIsDeletedIsFalseOrderByCreatedAtDesc(String npm, Pageable pageable);
    // Mencari daftar relasi Mahasiswa dan Mata Kuliah berdasarkan ID Mata Kuliah.
    Page<StudentCourse> findAllByCourse_IdCourseAndIsDeletedIsFalseOrderByCreatedAtDesc(String idCourse, Pageable pageable);
}
