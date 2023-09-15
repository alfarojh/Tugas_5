package com.example.tugas5.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class StudentCourse extends BaseModel{
    private String idStudentCourse;
    @ManyToOne
    @JoinColumn(name = "id_student", referencedColumnName = "id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "id_course", referencedColumnName = "id")
    private Course course;
    private Integer credit;     // Jumlah SKS
    @OneToMany (mappedBy = "studentCourse", cascade = CascadeType.PERSIST)
    private List<QuizRecord> quizRecordList;

    public StudentCourse() {
        // Do Nothing
    }

    public String getIdStudentCourse() {
        return idStudentCourse;
    }

    public void setIdStudentCourse(String idStudentCourse) {
        this.idStudentCourse = idStudentCourse;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public List<QuizRecord> getQuizRecordList() {
        return quizRecordList;
    }

    public void setQuizRecordList(List<QuizRecord> quizRecordList) {
        this.quizRecordList = quizRecordList;
    }
}
