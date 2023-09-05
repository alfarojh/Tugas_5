package com.example.tugas5.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "student_course")
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student_course")
    private long id;
    @ManyToOne
    @JoinColumn(name = "npm_student", referencedColumnName = "npm")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "id_course", referencedColumnName = "id_course")
    private Course course;
    private int credit;
    @OneToMany(mappedBy = "studentCourse")
    @JsonIgnoreProperties({"id", "studentCourse"})
    private List<QuizRecord> quizRecordList;
    private Integer exam1;
    private Integer exam2;
    private boolean isActive;

    public StudentCourse() {
        // Do Nothing
    }

    public long getId() {
        return id;
    }

    public List<QuizRecord> getQuizRecordList() {
        return quizRecordList;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Integer getExam1() {
        return exam1;
    }

    public void setExam1(Integer exam1) {
        this.exam1 = exam1;
    }

    public Integer getExam2() {
        return exam2;
    }

    public void setExam2(Integer exam2) {
        this.exam2 = exam2;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
