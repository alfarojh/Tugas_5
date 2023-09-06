package com.example.tugas5.controller;

import com.example.tugas5.model.ApiResponse;
import com.example.tugas5.model.Major;
import com.example.tugas5.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/majors")
public class MajorController {
    @Autowired
    private MajorService majorService;

    @GetMapping("")
    public ResponseEntity getMajors() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "All list Major.",
                        majorService.majorList()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity getMajorById(@PathVariable long id) {
        if (majorService.getMajorById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            majorService.getMessage(),
                            majorService.getMajorById(id)
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        }
    }

    @PostMapping("")
    public ResponseEntity addMajor(@RequestBody Major majorRequest) {
        if (majorService.add(majorRequest)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            majorService.getMessage(),
                            majorRequest
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMajor(@PathVariable long id, @RequestBody Major majorRequest) {
        if (majorService.updateData(id, majorRequest)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            majorService.getMessage(),
                            majorService.getCurrent()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMajor(@PathVariable long id) {
        if (majorService.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            majorService.getMessage(),
                            majorService.getCurrent()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        }
    }
}
