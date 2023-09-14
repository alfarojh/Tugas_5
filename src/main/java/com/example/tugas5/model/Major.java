package com.example.tugas5.model;

import javax.persistence.Entity;

@Entity
public class Major extends BaseModel{
    String idMajor;
    private String name;

    public Major() {
        super();
    }

    public Major(String idMajor, String name) {
        super();
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
