package com.example.tugas5.service;

import com.example.tugas5.model.QuizRecord;
import com.example.tugas5.model.StudentCourse;
import com.example.tugas5.repository.QuizRecordRepository;
import com.example.tugas5.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizRecordService {
    @Autowired
    private QuizRecordRepository quizRecordRepository;
    @Autowired
    private StudentCourseRepository studentCourseRepository;
    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * Menambahkan nilai kuis untuk mahasiswa berdasarkan mata kuliah.
     *
     * @param quizRecordRequest Nilai kuis yang akan ditambahkan.
     * @return                  True jika berhasil ditambahkan, dan false jika gagal.
     */
    public boolean add(QuizRecord quizRecordRequest) {
        if (quizRecordRequest.getStudentCourse() == null) {
            message = "Input invalid.";
            return false;
        }

        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findById(quizRecordRequest.getStudentCourse().getId());
        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else if (quizRecordRequest.getScore() == null ||
                quizRecordRequest.getScore() < 0 ||
                quizRecordRequest.getScore() > 100) {
            message = "Input invalid.";
            return false;
        } else {
            quizRecordRepository.save(quizRecordRequest);
            message = "Quiz added successfully.";
            quizRecordRequest.setStudentCourse(studentCourseOptional.get());
            return true;
        }
    }

    /**
     * Mengembalikan daftar nilai.
     *
     * @return      Daftar nilai.
     */
    public List<QuizRecord> quizRecordList() {
        return quizRecordRepository.findAllSorted();
    }

    /**
     * Mengembalikan daftar nilai berdasarkan ID kuis.
     *
     * @param id    ID kuis.
     * @return      Daftar nilai jika tersedia, jika tidak tersedia kembalikan null.
     */
    public QuizRecord getQuizRecordById(long id) {
        Optional<QuizRecord> quizRecordOptional = quizRecordRepository.findById(id);

        if (quizRecordOptional.isPresent()) {
            message = "Quiz ID Found.";
            return quizRecordOptional.get();
        } else {
            message = "Quiz ID Not Found.";
            return null;
        }
    }
}