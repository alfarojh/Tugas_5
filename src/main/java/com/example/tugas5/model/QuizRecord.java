package com.example.tugas5.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
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
    @Column(name = "id_quiz_record")
    private long id;
    @ManyToOne
//    @JoinColumn(name = "id_student_course", referencedColumnName = "id_student_course")
    @JsonIgnoreProperties({"course", "student", "credit", "quizRecordList", "exam1", "exam2", "active"})
    private StudentCourse studentCourse;
    private Integer score;

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
