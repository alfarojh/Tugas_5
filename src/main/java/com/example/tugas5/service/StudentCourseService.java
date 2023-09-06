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
     * @param studentCourseRequest  Relasi Mahasiswa dan Mata Kuliah yang akan ditambahkan.
     * @return                      True jika berhasil ditambahkan, dan false jika gagal.
     */
    public boolean add(StudentCourse studentCourseRequest) {
        if (studentCourseRequest.getStudent() == null) {
            message = "Please insert student NPM.";
            return false;
        } else if (studentCourseRequest.getCourse() == null) {
            message = "Please insert course ID.";
            return false;
        }

        Optional<Student> studentOptional = studentRepository.findById(studentCourseRequest.getStudent().getNpm());
        Optional<Course> courseOptional = courseRepository.findById(studentCourseRequest.getCourse().getId());

        if (!studentOptional.isPresent()) {
            message = "Student NPM Not Found.";
            return false;
        } else if (!courseOptional.isPresent()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            studentCourseRequest.setCredit(courseOptional.get().getCredit());
            studentCourseRepository.save(studentCourseRequest);
            studentCourseRequest.setStudent(studentOptional.get());
            studentCourseRequest.setCourse(courseOptional.get());
            message = "StudentCourse added successfully.";
            return true;
        }
    }

    /**
     * Memperbarui informasi Relasi Mahasiswa dan Mata Kuliah yang ada berdasarkan ID Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param id                    ID Relasi Mahasiswa dan Mata Kuliah yang akan diperbarui.
     * @param studentCourseRequest  Informasi Relasi Mahasiswa dan Mata Kuliah yang ingin diperbarui.
     * @return                      True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateData(long id, StudentCourse studentCourseRequest) {
        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findById(id);
        current = null;

        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else if (studentCourseRequest.getExam1() != null &&
                (studentCourseRequest.getExam1() < 0 || studentCourseRequest.getExam1() > 100)) {
            message = "Input Invalid.";
            return false;
        } else if (studentCourseRequest.getExam2() != null &&
                (studentCourseRequest.getExam2() < 0 || studentCourseRequest.getExam2() > 100)) {
            message = "Input Invalid.";
            return false;
        }
        else {
            boolean isChange = false;
            if (studentCourseOptional.get().getExam1() == null &&
                    studentCourseRequest.getExam1() != null) {
                studentCourseOptional.get().setExam1(studentCourseRequest.getExam1());
                isChange = true;
            }
            if (studentCourseOptional.get().getExam2() == null &&
                    studentCourseRequest.getExam2() != null) {
                studentCourseOptional.get().setExam2(studentCourseRequest.getExam2());
                isChange = true;
            }

            current = studentCourseOptional.get();
            if (isChange) {
                studentCourseRepository.save(studentCourseOptional.get());
                message = "StudentCourse with ID `" + id + "` updated successfully.";
                return true;
            } else {
                message = "StudentCourse with ID `" + id + "` cannot updated because value not change.";
                return false;
            }
        }
    }

    /**
     * Memperbarui status Relasi Mahasiswa dan Mata Kuliah yang ada berdasarkan ID Relasi Mahasiswa dan Mata Kuliah.
     *
     * @param id                    ID Relasi Mahasiswa dan Mata Kuliah yang akan diperbarui.
     * @param studentCourseRequest  Status Relasi Mahasiswa dan Mata Kuliah yang ingin diperbarui.
     * @return                      True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateStatus(long id, StudentCourse studentCourseRequest) {
        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findById(id);
        current = null;

        if (!studentCourseOptional.isPresent()) {
            message = "StudentCourse ID Not Found.";
            return false;
        } else {
            studentCourseOptional.get().setActive(studentCourseRequest.isActive());
            studentCourseRepository.save(studentCourseOptional.get());
            current = studentCourseOptional.get();
            if (studentCourseRequest.isActive()) {
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
        return studentCourseRepository.findAllSorted();
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
