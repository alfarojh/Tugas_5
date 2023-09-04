package com.example.tugas5.repository;

import com.example.tugas5.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorInterface extends JpaRepository<Major, Long> {

}
