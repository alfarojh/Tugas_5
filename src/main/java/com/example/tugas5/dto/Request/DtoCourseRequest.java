package com.example.tugas5.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoCourseRequest {
    @JsonProperty("id_course")
    private String idCourse;
    @JsonProperty("name")
    private String name;
    @JsonProperty("credit")
    private Integer credit;
    @JsonProperty("is_active")
    private Boolean isActive;

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
