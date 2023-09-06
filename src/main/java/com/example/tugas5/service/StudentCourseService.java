package com.example.tugas5.service;

import com.example.tugas5.model.Course;
import com.example.tugas5.model.Student;
import com.example.tugas5.model.StudentCourse;
import com.example.tugas5.repository.CourseRepository;
import com.example.tugas5.repository.StudentCourseRepository;
import com.example.tugas5.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseRepository studentCourseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    private StudentCourse current;      // Untuk mengambil data terbaru saat melakukan transaksi.
    private String message;

    public StudentCourse getCurrent() {
        return current;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Menambahkan Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param studentCourse Relasi Mahasiswa dan Mata Kuliah yang akan ditambahkan.
     * @return              True jika berhasil ditambahkan, dan false jika gagal.
     */
    public boolean add(StudentCourse studentCourse) {
        if (studentCourse.getStudent() == null) {
            message = "Student NPM Not Found.";
            return false;
        } else if (studentCourse.getCourse() == null) {
            message = "Course ID Not Found.";
            return false;
        }

        Optional<Student> studentOptional = studentRepository.findById(studentCourse.getStudent().getNpm());
        Optional<Course> courseOptional = courseRepository.findById(studentCourse.getCourse().getId());

        if (!studentOptional.isPresent()) {
            message = "Student NPM Not Found.";
            return false;
        } else if (!courseOptional.isPresent()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            studentCourse.setCredit(courseRepository.getReferenceById(studentCourse.getCourse().getId()).getCredit());
            studentCourseRepository.save(studentCourse);
            studentCourse.setStudent(studentOptional.get());
            studentCourse.setCourse(courseOptional.get());
            message = "StudentCourse added successfully.";
            return true;
        }
    }

    /**
     * Memperbarui informasi Relasi Mahasiswa dan Mata Kuliah yang ada berdasarkan ID Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param id            ID Relasi Mahasiswa dan Mata Kuliah yang akan diperbarui.
     * @param studentCourse Informasi Relasi Mahasiswa dan Mata Kuliah yang ingin diperbarui.
     * @return              True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateData(long id, StudentCourse studentCourse) {
        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findById(id);
        current = null;

        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else if (studentCourse.getExam1() != null &&
                (studentCourse.getExam1() < 0 || studentCourse.getExam1() > 100)) {
            message = "Input Invalid.";
            return false;
        } else if (studentCourse.getExam2() != null &&
                (studentCourse.getExam2() < 0 || studentCourse.getExam2() > 100)) {
            message = "Input Invalid.";
            return false;
        }
        else {
            if (studentCourseOptional.get().getExam1() == null) {
                studentCourseOptional.get().setExam1(studentCourse.getExam1());
            }
            if (studentCourseOptional.get().getExam2() == null) {
                studentCourseOptional.get().setExam2(studentCourse.getExam2());
            }

            studentCourseRepository.save(studentCourseOptional.get());
            message = "StudentCourse with ID `" + id + "` updated successfully.";
            current = studentCourseOptional.get();
            return true;
        }
    }

    /**
     * Memperbarui status Relasi Mahasiswa dan Mata Kuliah yang ada berdasarkan ID Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param id            ID Relasi Mahasiswa dan Mata Kuliah yang akan diperbarui.
     * @param studentCourse Status Relasi Mahasiswa dan Mata Kuliah yang ingin diperbarui.
     * @return              True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateStatus(long id, StudentCourse studentCourse) {
        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findById(id);
        current = null;

        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else {
            studentCourseOptional.get().setActive(studentCourse.isActive());
            studentCourseRepository.save(studentCourseOptional.get());
            current = studentCourseOptional.get();
            if (studentCourse.isActive()) {
                message = "StudentCourse ID `" + id + "` is now active.";
            } else {
                message = "StudentCourse ID `" + id + "` is now inactive.";
            }
            return true;
        }
    }

    /**
     * Mengembalikan daftar Relasi Mahasiswa dan Mata Kuliah.
     *
     * @return      Daftar Relasi Mahasiswa dan Mata Kuliah.
     */
    public List<StudentCourse> studentCourseList() {
        return studentCourseRepository.findAll();
    }

    /**
     * Mengembalikan informasi Relasi Mahasiswa dan Mata Kuliah berdasarkan ID Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param id    ID Relasi Mahasiswa dan Mata Kuliah.
     * @return      Relasi Mahasiswa dan Mata Kuliah jika tersedia, jika tidak tersedia kembalikan null.
     */
    public StudentCourse getStudentCourseById(long id) {
        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findById(id);
        if (studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Found.";
            return studentCourseOptional.get();
        } else {
            message = "StudentCourse ID Not Found.";
            return null;
        }
    }
}
