package com.example.tugas5.service;

import com.example.tugas5.model.Course;
import com.example.tugas5.model.Student;
import com.example.tugas5.model.StudentCourse;
import com.example.tugas5.repository.CourseInterface;
import com.example.tugas5.repository.StudentCourseInterface;
import com.example.tugas5.repository.StudentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseInterface studentCourseInterface;
    @Autowired
    private StudentInterface studentInterface;
    @Autowired
    private CourseInterface courseInterface;
    private StudentCourse current;
    private String message;

    public StudentCourse getCurrent() {
        return current;
    }

    public String getMessage() {
        return message;
    }

    public boolean add(StudentCourse studentCourse) {
        Optional<Student> studentOptional = studentInterface.findById(studentCourse.getStudent().getNpm());
        Optional<Course> courseOptional = courseInterface.findById(studentCourse.getCourse().getId());

        if (!studentOptional.isPresent()) {
            message = "Student NPM Not Found.";
            return false;
        } else if (!courseOptional.isPresent()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            studentCourse.setCredit(courseInterface.getReferenceById(studentCourse.getCourse().getId()).getCredit());
            studentCourseInterface.save(studentCourse);
            studentCourse.setStudent(studentOptional.get());
            studentCourse.setCourse(courseOptional.get());
            message = "StudentCourse added successfully.";
            return true;
        }
    }

    public boolean updateData(long id, StudentCourse studentCourse) {
        Optional<StudentCourse> studentCourseOptional = studentCourseInterface.findById(id);
        current = null;

        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else {
//            studentCourseOptional.get().setQuiz(studentCourse.getQuiz());
            studentCourseOptional.get().setExam1(studentCourse.getExam1());
            studentCourseOptional.get().setExam2(studentCourse.getExam2());

            studentCourseInterface.save(studentCourseOptional.get());
            message = "StudentCourse with ID `" + id + "` updated successfully.";
            current = studentCourseOptional.get();
            return true;
        }
    }

    public boolean updateStatus(long id, StudentCourse studentCourse) {
        Optional<StudentCourse> studentCourseOptional = studentCourseInterface.findById(id);
        current = null;

        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else {
            studentCourseOptional.get().setActive(studentCourse.isActive());
            studentCourseInterface.save(studentCourseOptional.get());
            current = studentCourseOptional.get();
            if (studentCourse.isActive()) {
                message = "StudentCourse ID `" + id + "` is now active.";
            } else {
                message = "StudentCourse ID `" + id + "` is now inactive.";
            }
            return true;
        }
    }

    public List<StudentCourse> studentCourseList() {
        return studentCourseInterface.findAll();
    }

    public StudentCourse getStudentCourseById(long id) {
        Optional<StudentCourse> studentCourseOptional = studentCourseInterface.findById(id);
        if (studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Found.";
            return studentCourseOptional.get();
        } else {
            message = "StudentCourse ID Not Found.";
            return null;
        }
    }
}
