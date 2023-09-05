package com.example.tugas5.service;

import com.example.tugas5.model.Student;
import com.example.tugas5.repository.MajorInterface;
import com.example.tugas5.repository.StudentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentInterface studentInterface;
    @Autowired
    private MajorInterface majorInterface;
    private Student current;    // Untuk mengambil data terbaru saat melakukan transaksi.
    private String message;

    public String getMessage() {
        return message;
    }

    public Student getCurrent() {
        return current;
    }

    /**
     * Menambahkan Mahasiswa.
     *
     * @param student   Mahasiswa yang akan ditambahkan.
     * @return          True jika berhasil ditambahkan, dan false jika gagal.
     */
    public boolean add(Student student) {
        if (student.getMajor() == null || !majorInterface.existsById(student.getMajor().getId())) {
            message = "Major ID Not Found.";
            return false;
        } else if (student.getName() == null || isNameNotValid(student.getName())) {
            message = "Input invalid.";
            return false;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            int year = Integer.parseInt(format.format(new Timestamp(System.currentTimeMillis())));

            student.setNpm(getNewNPM(year, student.getMajor().getId()));
            student.setYear(year);
            studentInterface.save(student);
            message = "Student added successfully.";
            student.setMajor(majorInterface.getReferenceById(student.getMajor().getId()));
            return true;
        }
    }

    /**
     * Memperbarui informasi Mahasiswa yang ada berdasarkan NPM Mahasiswa.
     *
     * @param npm       NPM Mahasiswa yang akan diperbarui.
     * @param student   Informasi Mahasiswa yang ingin diperbarui.
     * @return          True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateData(String npm, Student student) {
        Optional<Student> studentOptional = studentInterface.findById(npm);
        current = null;

        if (!studentOptional.isPresent() || !studentOptional.get().isExist()) {
            message = "Student NPM Not Found.";
            return false;
        } else if (student.getName() == null ||
                isNameNotValid(student.getName())) {
            message = "Input invalid.";
            return false;
        } else {
            studentOptional.get().setName(student.getName());
            studentInterface.save(studentOptional.get());
            message = "Student with NPM `" + npm + "` updated successfully.";
            current = studentOptional.get();
            return true;
        }
    }

    /**
     * Memperbarui status Mahasiswa yang ada berdasarkan NPM Mahasiswa.
     *
     * @param npm       NPM Mahasiswa yang akan diperbarui.
     * @param student   Status Mahasiswa yang ingin diperbarui.
     * @return          True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateStatus(String npm, Student student) {
        Optional<Student> studentOptional = studentInterface.findById(npm);
        current = null;

        if (!studentOptional.isPresent() || !studentOptional.get().isExist()) {
            message = "Student NPM Not Found.";
            return false;
        } else {
            studentOptional.get().setActive(student.isActive());
            studentInterface.save(studentOptional.get());
            current = studentOptional.get();
            if (student.isActive()) {
                message = "Student NPM `" + npm + "` is now active.";
            } else {
                message = "Student NPM `" + npm + "` is now inactive.";
            }
            return true;
        }
    }

    /**
     * Menghapus Mahasiswa dari dalam daftar.
     *
     * @param npm   NPM Mahasiswa yang akan dihapus.
     * @return      True jika berhasil dihapus, dan false jika gagal.
     */
    public boolean delete(String npm) {
        Optional<Student> studentOptional = studentInterface.findById(npm);
        current = null;

        if (!studentOptional.isPresent() || !studentOptional.get().isExist()) {
            message = "Student NPM Not Found.";
            return false;
        } else {
            studentOptional.get().delete();
            studentInterface.save(studentOptional.get());
            message = "Student with NPM `" + npm + "` deleted successfully.";
            current = studentOptional.get();
            return true;
        }
    }

    /**
     * Mengembalikan daftar Mahasiswa.
     *
     * @return      Daftar Mahasiswa.
     */
    public List<Student> studentList() {
        return studentInterface.findAll().stream()
                .filter(Student::isExist)
                .collect(Collectors.toList());
    }

    /**
     * Mengembalikan informasi Mahasiswa berdasarkan NPM.
     *
     * @param npm   NPM Mahasiswa.
     * @return      Mahasiswa jika tersedia, jika tidak tersedia kembalikan null.
     */
    public Student getStudentByNpm(String npm) {
        Optional<Student> studentOptional = studentInterface.findById(npm);
        if (studentOptional.isPresent() && studentOptional.get().isExist()) {
            message = "Student NPM Found.";
            return studentOptional.get();
        } else {
            message = "Student NPM Not Found.";
            return null;
        }
    }

    /**
     * Membuat NPM baru berdasarkan tahun, id Jurusan, dan jumlah.
     *
     * @return      NPM Mahasiswa baru.
     */
    private String getNewNPM(int year, long idMajor) {
        long count = 1;
        for (int index = 0; index < studentInterface.count(); index++) {
            if (getStudentByNpm(String.format("%d%02d%04d", year, idMajor, count)) != null) {
                count++;
            }
        }
        return String.format("%d%02d%04d", year, idMajor, count);
    }

    /**
     * Memeriksa apakah sebuah nama valid.
     * Nama yang valid hanya mengandung huruf (a-z, A-Z), angka (0-9), dan spasi.
     *
     * @param name      Nama yang akan diperiksa.
     * @return true     Jika nama valid, false jika tidak valid.
     */
    private boolean isNameNotValid(String name) {
        return !name.matches("[a-zA-Z0-9\\s]+");
    }
}
