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
    private String phoneNumber;
    private String address;
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "id_major", referencedColumnName = "id")
    private Major major;
    private Boolean isActive = true;

    public Student() {
        // Do Nothing
    }

    public Student(String npm, String name, String phoneNumber, String address, Integer year, Major major) {
        this.npm = npm;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.year = year;
        this.major = major;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
