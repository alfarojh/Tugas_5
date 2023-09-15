package com.example.tugas5.dto.Responses;

import com.example.tugas5.models.QuizRecord;
import com.example.tugas5.models.StudentCourse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DtoStudentCourseResponse {
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd:hh-mm-ss")
    private Date createdAt;
    @JsonProperty("name_student")
    private String nameStudent;
    @JsonProperty("name_course")
    private String nameCourse;
    @JsonProperty("credit")
    private Integer credit;
    @JsonProperty("score")
    private List<Integer> score;

    public DtoStudentCourseResponse(StudentCourse studentCourse) {
        this.nameStudent = studentCourse.getStudent().getName();
        this.nameCourse = studentCourse.getCourse().getName();
        this.credit = studentCourse.getCredit();
        this.createdAt = studentCourse.getCreatedAt();
        score = new ArrayList<>();
        for (QuizRecord quizRecord : studentCourse.getQuizRecordList()) {
            score.add(quizRecord.getScore());
        }
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }
}
