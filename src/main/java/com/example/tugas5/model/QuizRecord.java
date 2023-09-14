package com.example.tugas5.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class QuizRecord extends BaseModel {
    @ManyToOne
    private StudentCourse studentCourse;
    private Integer score;

    public QuizRecord() {
        // Do Nothing
    }

    public QuizRecord(StudentCourse studentCourse, Integer score) {
        this.studentCourse = studentCourse;
        this.score = score;
    }

    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }
}
