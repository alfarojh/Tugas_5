package com.example.tugas5.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Major extends BaseModel{
    @Column(unique = true)
    String idMajor;
    private String name;

    public Major() {
        super();
    }

    public Major(String idMajor, String name) {
        this.idMajor = idMajor;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(String idMajor) {
        this.idMajor = idMajor;
    }
}
