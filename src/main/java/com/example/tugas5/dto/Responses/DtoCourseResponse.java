package com.example.tugas5.dto.Responses;

import com.example.tugas5.models.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoCourseResponse {
    @JsonProperty("id_course")
    private String idCourse;
    @JsonProperty("name")
    private String name;
    @JsonProperty("credit")
    private Integer credit;
    @JsonProperty("is_active")
    private Boolean isActive;

    public DtoCourseResponse(Course course) {
        this.idCourse = course.getIdCourse();
        this.name = course.getName();
        this.credit = course.getCredit();
        this.isActive = course.getActive();
    }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
