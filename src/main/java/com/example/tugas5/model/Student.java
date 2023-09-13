package com.example.tugas5.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Student extends BaseModel{
    @Column(length = 10, unique = true)
    private String npm;
    private String name;
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "id_major", referencedColumnName = "id")
    private Major major;
    private boolean isActive = true;

    public Student() {
        // Do Nothing
    }

    public String getNpm() {
        return npm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
