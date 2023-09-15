package com.example.tugas5.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Student extends BaseModel{
    @Column(unique = true)
    private String npm;
    private String name;
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "id_major", referencedColumnName = "id")
    private Major major;
    private Boolean isActive = true;

    public Student() {
        // Do Nothing
    }

    public Student(String npm, String name, Integer year, Major major) {
        this.npm = npm;
        this.name = name;
        this.year = year;
        this.major = major;
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

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
