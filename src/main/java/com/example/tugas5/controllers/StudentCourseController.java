package com.example.tugas5.controllers;

import com.example.tugas5.utilities.Utility;
import com.example.tugas5.dto.Requests.DtoStudentCourseRequest;
import com.example.tugas5.dto.Responses.DtoStudentCourseResponse;
import com.example.tugas5.models.ApiResponse;
import com.example.tugas5.services.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student-courses")
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;

    // API untuk menampilkan daftar relasi Mahasiswa dan Mata Kuliah berdasarkan request.
    @GetMapping("")
    public ResponseEntity getStudentCourses(
            @RequestParam(required = false) String npm,
            @RequestParam(required = false, name = "id_course") String idCourse,
            @RequestParam int page, @RequestParam int limit) {
        if (npm != null && !npm.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            Utility.message("success"),
                            studentCourseService.studentCourseResponseListByNpm(npm.trim(), page, limit)
                    ));
        } else if (idCourse != null && !idCourse.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            Utility.message("success"),
                            studentCourseService.studentCourseResponseListByIdCourse(idCourse.trim(), page, limit)
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            Utility.message("success"),
                            studentCourseService.studentCourseResponseList(page, limit)
                    ));
        }
    }

    // API untuk menampilkan informasi relasi Mahasiswa dan Mata Kuliah berdasarakan ID.
    @GetMapping("/{code}")
    public ResponseEntity getStudentCourseByIdStudentCourse(@PathVariable String code) {
        DtoStudentCourseResponse studentCourseResponse = studentCourseService.getStudentCourseResponseByCode(code);

        if (studentCourseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentCourseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Utility.message("success"),
                            studentCourseResponse
                    ));
        }
    }

    // API untuk menambahkan relasi Mahasiswa dan Mata Kuliah baru berdasarkan request.
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
                            Utility.message("success"),
                            studentCourseResponse
                    ));
        }
    }

    // API untuk menghapus relasi Mahasiswa dan Mata Kuliah berdasarkan ID / code.
    @DeleteMapping("")
    public ResponseEntity deleteStudentCourse(@RequestBody DtoStudentCourseRequest studentCourseRequest) {
        DtoStudentCourseResponse studentCourseResponse = studentCourseService.delete(studentCourseRequest.getCode());

        if (studentCourseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            studentCourseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Utility.message("success"),
                            studentCourseResponse
                    ));
        }
    }
}
