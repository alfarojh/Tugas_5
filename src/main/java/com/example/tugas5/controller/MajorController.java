package com.example.tugas5.controller;

import com.example.tugas5.Utility.Validation;
import com.example.tugas5.dto.Request.DtoMajorRequest;
import com.example.tugas5.dto.Response.DtoMajorResponse;
import com.example.tugas5.model.ApiResponse;
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
                        Validation.message("success"),
                        majorService.majorListResponse()
                ));
    }

    @GetMapping("/{idMajor}")
    public ResponseEntity getMajorById(@PathVariable String idMajor) {
        DtoMajorResponse majorResponse = majorService.getMajorResponseByIdMajor(idMajor);

        if (majorResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            majorResponse
                    ));
        }
    }

    @PostMapping("")
    public ResponseEntity addMajor(@RequestBody DtoMajorRequest dtoMajorRequest) {
        DtoMajorResponse majorResponse = majorService.add(dtoMajorRequest);

        if (majorResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            majorResponse
                    ));
        }
    }

    @PutMapping("")
    public ResponseEntity updateMajor(@RequestBody DtoMajorRequest majorRequest) {
        DtoMajorResponse majorResponse = majorService.updateData(majorRequest);

        if (majorResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            majorResponse
                    ));
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteMajor(@RequestBody DtoMajorRequest majorRequest) {
        DtoMajorResponse majorResponse = majorService.delete(majorRequest.getIdMajor());

        if (majorResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            majorService.getMessage()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            Validation.message("success"),
                            majorResponse
                    ));
        }
    }
}
