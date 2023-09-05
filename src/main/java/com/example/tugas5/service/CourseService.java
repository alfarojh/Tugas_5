package com.example.tugas5.service;

import com.example.tugas5.model.Course;
import com.example.tugas5.repository.CourseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseInterface courseInterface;
    private Course current;     // Untuk mengambil data terbaru saat melakukan transaksi.
    private String message;

    public String getMessage() {
        return message;
    }

    public Course getCurrent() {
        return current;
    }

    /**
     * Menambahkan Mata Kuliah baru.
     *
     * @param course    Mata Kuliah yang akan ditambahkan.
     * @return          True jika berhasil ditambahkan, dan false jika gagal.
     */
    public boolean add(Course course) {
        if (course.getName() != null && isNameValid(course.getName()) && course.getCredit() > 0) {
            courseInterface.save(course);
            message = "Course added successfully.";
            return true;
        } else {
            message = "Input invalid.";
            return false;
        }
    }

    /**
     * Memperbarui informasi Mata Kuliah yang ada berdasarkan ID Mata Kuliah.
     *
     * @param id        ID Mata Kuliah yang akan diperbarui.
     * @param course    Informasi Mata Kuliah yang ingin diperbarui.
     * @return          True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateData(Long id, Course course) {
        Optional<Course> courseOptional = courseInterface.findById(id);
        current = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else if (course.getName() == null || !isNameValid(course.getName()) || course.getCredit() <= 0) {
            message = "Input invalid.";
            return false;
        } else {
            courseOptional.get().setName(course.getName());
            courseOptional.get().setCredit(course.getCredit());
            courseInterface.save(courseOptional.get());
            message = "Course with ID `" + id + "` updated successfully.";
            current = courseOptional.get();
            return true;
        }
    }

    /**
     * Memperbarui status aktif Mata Kuliah berdasarkan ID Mata Kuliah.
     *
     * @param id        ID Mata Kuliah yang statusnya akan diperbarui.
     * @param course    Status Mata Kuliah yang ingin diperbarui.
     * @return          True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateStatus(long id, Course course) {
        Optional<Course> courseOptional = courseInterface.findById(id);
        current = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            courseOptional.get().setActive(course.isActive());
            courseInterface.save(courseOptional.get());
            current = courseOptional.get();
            if (course.isActive()) {
                message = "Course ID `" + id + "` is now active.";
            } else {
                message = "Course ID `" + id + "` is now inactive.";
            }
            return true;
        }
    }

    /**
     * Menghapus Mata Kuliah.
     *
     * @param id    ID Mata Kuliah yang akan dihapus.
     * @return      True jika objek berhasil dihapus, dan false jika gagal.
     */
    public boolean delete(Long id) {
        Optional<Course> courseOptional = courseInterface.findById(id);
        current = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            courseOptional.get().delete();
            courseInterface.save(courseOptional.get());
            message = "Course with ID `" + id + "` deleted successfully.";
            current = courseOptional.get();
            return true;
        }
    }

    /**
     * Mengembalikan daftar Mata Kuliah yang masih tersedia.
     *
     * @return      Daftar Jurusan yang masih tersedia.
     */
    public List<Course> courseList() {
        return courseInterface.findAll().stream()
                .filter(Course::isExist)
                .collect(Collectors.toList());
    }

    /**
     * Mengembalikan informasi Mata Kuliah berdasarkan ID Mata Kuliah.
     *
     * @param id    ID Mata Kuliah.
     * @return      Mata Kuliah jika tersedia, jika tidak tersedia kembalikan null.
     */
    public Course getCourseById(long id) {
        Optional<Course> courseOptional = courseInterface.findById(id);
        if (courseOptional.isPresent() && courseOptional.get().isExist()) {
            message = "Course ID Found.";
            return courseOptional.get();
        } else {
            message = "Course ID Not Found.";
            return null;
        }
    }

    /**
     * Memeriksa apakah sebuah nama valid.
     * Nama yang valid hanya mengandung huruf (a-z, A-Z), angka (0-9), dan spasi.
     *
     * @param name      Nama yang akan diperiksa.
     * @return true     Jika nama valid, false jika tidak valid.
     */
    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z0-9\\s]+");
    }
}
