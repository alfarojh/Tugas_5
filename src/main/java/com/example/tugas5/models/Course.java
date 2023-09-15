package com.example.tugas5.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Course extends BaseModel{
    @Column(unique = true)
    private String idCourse;
    private String name;
    private Integer credit;
    private Boolean isActive = true;

    public Course() {
        // Do Nothing
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
