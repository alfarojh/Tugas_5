package com.example.tugas5.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class QuizRecord extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "id_student_course", referencedColumnName = "id")
    private StudentCourse studentCourse;
    private String nameQuiz;
    private Integer score;                  

    public QuizRecord() {
        // Do Nothing
    }

    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
    }

    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }
}
