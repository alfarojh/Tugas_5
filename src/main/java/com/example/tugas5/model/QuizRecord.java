package com.example.tugas5.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quiz_record")
public class QuizRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;                // ID Kuis
    @ManyToOne
    private StudentCourse studentCourse;    // Relasi untuk nilai kuis Mahasiswa berdasarkan Mata Kuliah
    private Integer score;                  // Nilai Kuis

    public QuizRecord() {
        // Do Nothing
    }

    public long getId() {
        return id;
    }

    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
    }

    public Integer getScore() {
        return score;
    }
}
