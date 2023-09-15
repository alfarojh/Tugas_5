package com.example.tugas5.dto.Responses;

import com.example.tugas5.models.Student;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoStudentResponse {
    @JsonProperty("npm")
    private String npm;
    @JsonProperty("name_student")
    private String nameStudent;
    @JsonProperty("name_major")
    private String nameMajor;
    @JsonProperty("is_active")
    private Boolean isActive;

    public DtoStudentResponse(Student student) {
        this.npm = student.getNpm();
        this.nameStudent = student.getName();
        this.nameMajor = student.getMajor().getName();
        this.isActive = student.isActive();
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getNameMajor() {
        return nameMajor;
    }

    public void setNameMajor(String nameMajor) {
        this.nameMajor = nameMajor;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
