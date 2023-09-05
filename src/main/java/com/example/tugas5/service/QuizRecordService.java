package com.example.tugas5.service;

import com.example.tugas5.model.QuizRecord;
import com.example.tugas5.model.StudentCourse;
import com.example.tugas5.repository.QuizRecordInterface;
import com.example.tugas5.repository.StudentCourseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizRecordService {
    @Autowired
    private QuizRecordInterface quizRecordInterface;
    @Autowired
    private StudentCourseInterface studentCourseInterface;
    private String message;

    public String getMessage() {
        return message;
    }

    public boolean add(QuizRecord quizRecord) {
        if (quizRecord.getStudentCourse() == null) {
            message = "Input invalid.";
            return false;
        }

        Optional<StudentCourse> studentCourseOptional = studentCourseInterface.findById(quizRecord.getStudentCourse().getId());
        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else if (quizRecord.getScore() == null ||
                quizRecord.getScore() < 0 ||
                quizRecord.getScore() > 100) {
            message = "Input invalid.";
            return false;
        } else {
            quizRecordInterface.save(quizRecord);
            quizRecord.setStudentCourse(studentCourseOptional.get());
            return true;
        }
    }

    public List<QuizRecord> quizRecordList() {
        return quizRecordInterface.findAll();
    }

    public QuizRecord getQuizRecordById(long id) {
        Optional<QuizRecord> quizRecordOptional = quizRecordInterface.findById(id);

        if (quizRecordOptional.isPresent()) {
            message = "StudentCourse ID Found.";
            return quizRecordOptional.get();
        } else {
            message = "StudentCourse ID Not Found.";
            return null;
        }
    }
}
