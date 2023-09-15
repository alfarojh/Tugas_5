package com.example.tugas5.services;

import com.example.tugas5.Utilities.Validation;
import com.example.tugas5.dto.Requests.DtoStudentCourseRequest;
import com.example.tugas5.dto.Responses.DtoStudentCourseResponse;
import com.example.tugas5.models.Course;
import com.example.tugas5.models.QuizRecord;
import com.example.tugas5.models.Student;
import com.example.tugas5.models.StudentCourse;
import com.example.tugas5.repositories.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseRepository studentCourseRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * Menambahkan Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param studentCourseRequest  Relasi Mahasiswa dan Mata Kuliah yang akan ditambahkan.
     * @return                      True jika berhasil ditambahkan, dan false jika gagal.
     */
    public DtoStudentCourseResponse add(DtoStudentCourseRequest studentCourseRequest) {
        if (studentCourseRequest.getNpm() == null) {
            message = Validation.message("student_not_insert");
            return null;
        } else if (studentCourseRequest.getIdCourse() == null) {
            message = Validation.message("course_not_insert");
            return null;
        }

        Student student = studentService.getStudentByNpm(studentCourseRequest.getNpm());
        Course course = courseService.getCourseByIdCourse(studentCourseRequest.getIdCourse());

        if (student == null) {
            message = Validation.message("student_invalid");
            return null;
        } else if (course == null) {
            message = Validation.message("course_invalid");
            return null;
        } else {
            StudentCourse studentCourse = new StudentCourse();
            List<QuizRecord> quizRecordList = new ArrayList<>();

            for (Integer score : studentCourseRequest.getScore()) {
                if (score < 0 || score > 100) {
                    message = Validation.message("score_invalid");
                    return null;
                }
                quizRecordList.add(new QuizRecord(studentCourse, score));
            }
            studentCourse.setStudent(student);
            studentCourse.setCourse(course);
            studentCourse.setCredit(course.getCredit());
            studentCourse.setQuizRecordList(quizRecordList);
            studentCourseRepository.save(studentCourse);
            return new DtoStudentCourseResponse(studentCourse);
        }
    }


    public DtoStudentCourseResponse delete(Long id) {
        if (id == null) {
            message = Validation.message("student_course_not_insert");
            return null;
        }
        StudentCourse studentCourse = getStudentCourseById(id);

        if (studentCourse == null) {
            message = Validation.message("student_course_invalid");
            return null;
        } else {
            studentCourse.setDeleted(true);
            studentCourse.setDeletedAt(new Date());
            studentCourseRepository.save(studentCourse);
            return new DtoStudentCourseResponse(studentCourse);
        }
    }
    /**
     * Mengembalikan daftar Relasi Mahasiswa dan Mata Kuliah.
     *
     * @return      Daftar Relasi Mahasiswa dan Mata Kuliah.
     */
    public List<StudentCourse> studentCourseList() {
        return studentCourseRepository.findAllByIsDeletedIsFalseOrderByCreatedAtDesc();
    }

    public Page<DtoStudentCourseResponse> studentCourseResponseList(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<StudentCourse> result = studentCourseRepository.findAllByIsDeletedIsFalseOrderByCreatedAtDesc(pageable);
        List<DtoStudentCourseResponse> resultDto = new ArrayList<>();

        for (StudentCourse studentCourse : result.getContent()) {
            resultDto.add(new DtoStudentCourseResponse(studentCourse));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    /**
     * Mengembalikan informasi Relasi Mahasiswa dan Mata Kuliah berdasarkan ID Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param id    ID Relasi Mahasiswa dan Mata Kuliah.
     * @return      Relasi Mahasiswa dan Mata Kuliah jika tersedia, jika tidak tersedia kembalikan null.
     */
    public StudentCourse getStudentCourseById(Long id) {
        if (id == null) {
            message = Validation.message("student_course_not_insert");
            return null;
        }
        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findFirstByIdAndIsDeletedIsFalse(id);

        if (studentCourseOptional.isPresent()) {
            return studentCourseOptional.get();
        } else {
            message = Validation.message("student_course_invalid");
            return null;
        }
    }

    public DtoStudentCourseResponse getStudentCourseResponseById(Long id) {
        StudentCourse studentCourse = getStudentCourseById(id);

        if (studentCourse == null) return null;
        else return new DtoStudentCourseResponse(studentCourse);
    }
}
