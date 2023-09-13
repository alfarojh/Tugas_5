package com.example.tugas5.model;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
