package com.example.tugas5.controller;

import com.example.tugas5.Utility.Validation;
import com.example.tugas5.dto.Request.DtoStudentCourseRequest;
import com.example.tugas5.dto.Response.DtoStudentCourseResponse;
import com.example.tugas5.model.ApiResponse;
import com.example.tugas5.model.StudentCourse;
import com.example.tugas5.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student-courses")
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("")
    public ResponseEntity getStudentCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "All list StudentCourse.",
                        studentCourseService.studentCourseResponseList()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity getStudentCourseById(@PathVariable long id) {
        DtoStudentCourseResponse studentCourseResponse = studentCourseService.getStudentCourseResponseById(id);

        if (studentCourseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentCourseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentCourseResponse
                    ));
        }
    }

    @PostMapping("")
    public ResponseEntity addStudentCourse(@RequestBody DtoStudentCourseRequest studentCourseRequest) {
        DtoStudentCourseResponse studentCourseResponse = studentCourseService.add(studentCourseRequest);

        if (studentCourseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentCourseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentCourseResponse
                    ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudentCourse(@PathVariable Long id) {
        DtoStudentCourseResponse studentCourseResponse = studentCourseService.delete(id);

        if (studentCourseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentCourseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentCourseResponse
                    ));
        }
    }
}
