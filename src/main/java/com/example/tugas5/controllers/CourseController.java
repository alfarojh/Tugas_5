package com.example.tugas5.controllers;

import com.example.tugas5.utilities.Validation;
import com.example.tugas5.dto.Requests.DtoCourseRequest;
import com.example.tugas5.dto.Responses.DtoCourseResponse;
import com.example.tugas5.models.ApiResponse;
import com.example.tugas5.services.CourseService;
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
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("")
    public ResponseEntity getCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, name = "is_active") Boolean isActive,
            @RequestParam int page, @RequestParam int limit) {
        if (name != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            Validation.message("success"),
                            courseService.courseResponseListByName(name, page, limit)
                    ));

        } else if (isActive != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            Validation.message("success"),
                            courseService.courseResponseListByActive(isActive, page, limit)
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            Validation.message("success"),
                            courseService.courseResponseList(page, limit)
                    ));
        }
    }

    @GetMapping("/{idCourse}")
    public ResponseEntity getCourseById(@PathVariable String idCourse) {
        DtoCourseResponse courseResponse = courseService.getCourseResponseByIdCourse(idCourse);

        if (courseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            courseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            courseResponse
                    ));
        }
    }

    @PostMapping("")
    public ResponseEntity addCourse(@RequestBody DtoCourseRequest courseRequest) {
        DtoCourseResponse courseResponse = courseService.add(courseRequest);

        if (courseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            courseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            courseResponse
                    ));
        }
    }

    @PutMapping("")
    public ResponseEntity updateCourse(@RequestBody DtoCourseRequest courseRequest) {
        DtoCourseResponse courseResponse = courseService.updateData(courseRequest);

        if (courseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            courseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            courseResponse
                    ));
        }
    }

    @PatchMapping("")
    public ResponseEntity updateActivated(@RequestBody DtoCourseRequest courseRequest) {
        DtoCourseResponse courseResponse = courseService.updateStatus(courseRequest.getIdCourse(), courseRequest.getActive());

        if (courseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            courseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            courseResponse
                    ));
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteCourse(@RequestBody DtoCourseRequest courseRequest) {
        DtoCourseResponse courseResponse = courseService.delete(courseRequest.getIdCourse());

        if (courseResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            courseService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            courseResponse
                    ));
        }
    }
}
