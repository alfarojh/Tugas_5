package com.example.tugas5.controller;

import com.example.tugas5.model.ApiResponse;
import com.example.tugas5.model.Major;
import com.example.tugas5.model.QuizRecord;
import com.example.tugas5.service.QuizRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz-records")
public class QuizRecordController {
    @Autowired
    private QuizRecordService quizRecordService;

    @GetMapping("")
    public ResponseEntity getScores() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "All list Course.",
                        quizRecordService.quizRecordList()
                ));
    }

    @PostMapping("")
    public ResponseEntity addQuiz(@RequestBody QuizRecord quizRecord) {
        if (quizRecordService.add(quizRecord)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            quizRecordService.getMessage(),
                            quizRecord
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            quizRecordService.getMessage()
                    ));
        }
    }
}
