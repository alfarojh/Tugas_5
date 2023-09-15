package com.example.tugas5.controllers;

import com.example.tugas5.models.ApiResponse;
import com.example.tugas5.seeds.SeedData;
import com.example.tugas5.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seeds")
public class SeedController {
    @Autowired
    private SeedData seedData;

    // API untuk membuat seed baru.
    @PostMapping("")
    public ResponseEntity createSeed() {
        seedData.seed();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        Utility.message("success")
                ));
    }
}
