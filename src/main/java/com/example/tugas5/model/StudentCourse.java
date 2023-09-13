package com.example.tugas5.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class StudentCourse extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "npm_student", referencedColumnName = "npm")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "id_course", referencedColumnName = "id")
    private Course course;
    @CreationTimestamp
    private Date createdAt;
    private Integer credit;     // Jumlah SKS

    public StudentCourse() {
        // Do Nothing
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

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
