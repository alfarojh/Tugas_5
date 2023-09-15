package com.example.tugas5.dto.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DtoStudentCourseRequest {
    @JsonProperty("npm")
    private String npm;
    @JsonProperty("id_course")
    private String idCourse;
    @JsonProperty("score")
    private List<Integer> score;

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }
}
