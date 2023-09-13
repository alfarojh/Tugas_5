package com.example.tugas5.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Major extends BaseModel{
    @Column(unique = true)
    String idMajor;
    private String name;

    public Major() {
        // Do Nothing
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }
}
