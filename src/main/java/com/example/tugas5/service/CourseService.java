package com.example.tugas5.service;

import com.example.tugas5.model.Course;
import com.example.tugas5.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
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
     * @param courseRequest Mata Kuliah yang akan ditambahkan.
     * @return              True jika berhasil ditambahkan, dan false jika gagal.
     */
    public boolean add(Course courseRequest) {
        if (courseRequest.getName() != null && isNameValid(courseRequest.getName()) && courseRequest.getCredit() > 0) {
            courseRepository.save(courseRequest);
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
     * @param courseRequest Informasi Mata Kuliah yang ingin diperbarui.
     * @return              True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateData(Long id, Course courseRequest) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        current = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else if (courseRequest.getName() == null || !isNameValid(courseRequest.getName()) || courseRequest.getCredit() <= 0) {
            message = "Input invalid.";
            return false;
        } else {
            courseOptional.get().setName(courseRequest.getName());
            courseOptional.get().setCredit(courseRequest.getCredit());
            courseRepository.save(courseOptional.get());
            message = "Course with ID `" + id + "` updated successfully.";
            current = courseOptional.get();
            return true;
        }
    }

    /**
     * Memperbarui status aktif Mata Kuliah berdasarkan ID Mata Kuliah.
     *
     * @param id        ID Mata Kuliah yang statusnya akan diperbarui.
     * @param courseRequest Status Mata Kuliah yang ingin diperbarui.
     * @return              True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateStatus(long id, Course courseRequest) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        current = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            courseOptional.get().setActive(courseRequest.isActive());
            courseRepository.save(courseOptional.get());
            current = courseOptional.get();
            if (courseRequest.isActive()) {
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
        Optional<Course> courseOptional = courseRepository.findById(id);
        current = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            courseOptional.get().delete();
            courseRepository.save(courseOptional.get());
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
        return courseRepository.findAll().stream()
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
        Optional<Course> courseOptional = courseRepository.findById(id);
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
