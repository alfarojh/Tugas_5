package com.example.tugas5.controllers;

import com.example.tugas5.Utilities.Validation;
import com.example.tugas5.dto.Requests.DtoStudentRequest;
import com.example.tugas5.dto.Responses.DtoStudentResponse;
import com.example.tugas5.models.ApiResponse;
import com.example.tugas5.services.StudentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public ResponseEntity getStudents(@RequestParam int page, int limit) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        Validation.message("success"),
                        studentService.studentResponseList(page, limit)
                ));
    }

    @GetMapping("/{npm}")
    public ResponseEntity getStudentById(@PathVariable String npm) {
        DtoStudentResponse studentResponse = studentService.getStudentResponseByNpm(npm);

        if (studentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentResponse
                    ));
        }
    }

    @PostMapping("")
    public ResponseEntity addStudent(@RequestBody DtoStudentRequest studentRequest) {
        DtoStudentResponse studentResponse = studentService.add(studentRequest);

        if (studentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentResponse
                    ));
        }
    }

    @PutMapping("")
    public ResponseEntity updateStudent(@RequestBody DtoStudentRequest studentRequest) {
        DtoStudentResponse studentResponse = studentService.updateData(studentRequest);

        if (studentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentResponse
                    ));
        }
    }

    @PatchMapping("")
    public ResponseEntity updateActivated(@RequestBody DtoStudentRequest studentRequest) {
        DtoStudentResponse studentResponse = studentService.updateStatus(studentRequest.getNpm(), studentRequest.getIsActive());

        if (studentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentResponse
                    ));
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteStudent(@RequestBody DtoStudentRequest studentRequest) {
        DtoStudentResponse studentResponse = studentService.delete(studentRequest.getNpm());

        if (studentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            studentResponse
                    ));
        }
    }
}