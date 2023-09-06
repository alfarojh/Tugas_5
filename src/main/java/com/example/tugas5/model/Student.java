package com.example.tugas5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(length = 10, name = "npm")
    private String npm;         // NPM Mahasiswa
    private String name;        // Nama Mahasiswa
    private int year;           // Angkatan
    @ManyToOne
    private Major major;        // Jurusan
    @Column(name = "actived")
    private boolean isActive = true;   // Status aktif Mahasiswa
    @Column(name = "deleted")
    private boolean isDeleted = false;  // Status terhapusnya Mahasiswa

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

    @JsonIgnore
    public boolean isExist() {
        return !isDeleted;
    }

    public void delete() {
        isDeleted = true;
    }

}
