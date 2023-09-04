package com.example.tugas5.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import java.util.List;

@Entity
@Table(name = "major")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_major")
    private long id;                // ID Jurusan
    private String name;            // Nama Jurusan
    @Column(name = "deleted")
    private boolean isDeleted = false;  // Status terhapusnya jurusan
    @JsonIgnore
    @OneToMany(mappedBy = "major")
    private List<Student> studentList;

    public Major() {
        // Do Nothing
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    @JsonIgnore
    public boolean isExist() {
        return !isDeleted;
    }

    public void delete() {
        this.isDeleted = true;
    }

}
