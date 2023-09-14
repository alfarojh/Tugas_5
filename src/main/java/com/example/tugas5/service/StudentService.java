package com.example.tugas5.service;

import com.example.tugas5.Utility.Validation;
import com.example.tugas5.dto.Request.DtoStudentRequest;
import com.example.tugas5.dto.Response.DtoStudentResponse;
import com.example.tugas5.model.Major;
import com.example.tugas5.model.Student;
import com.example.tugas5.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MajorService majorService;
    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * Menambahkan Mahasiswa.
     *
     * @param studentRequest    Mahasiswa yang akan ditambahkan.
     * @return                  True jika berhasil ditambahkan, dan false jika gagal.
     */
    public DtoStudentResponse add(DtoStudentRequest studentRequest) {
        if (studentRequest.getNpm() == null) {
            message = Validation.message("student_not_insert");
            return null;
        } else if (studentRequest.getIdMajor() == null) {
            message = Validation.message("major_not_insert");
            return null;
        } else if (Validation.isNameNotValid(studentRequest.getName())) {
            message = Validation.message("name_invalid");
            return null;
        } else {
            Major major = majorService.getMajorById(studentRequest.getIdMajor());

            if (major == null) {
                message = Validation.message("major_invalid");
                return null;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            int year = Integer.parseInt(format.format(new Timestamp(System.currentTimeMillis())));
            Student student = new Student();

            student.setNpm(getNewNPM(year, major.getIdMajor()));
            student.setMajor(major);
            student.setYear(year);
            student.setName(studentRequest.getName());
            studentRepository.save(student);
            return new DtoStudentResponse(student);
        }
    }

    /**
     * Memperbarui informasi Mahasiswa yang ada berdasarkan NPM Mahasiswa.
     *
     * @param studentRequest    Informasi Mahasiswa yang ingin diperbarui.
     * @return                  True jika berhasil diperbarui, dan false jika gagal.
     */
    public DtoStudentResponse updateData(DtoStudentRequest studentRequest) {
        if (studentRequest.getNpm() == null) {
            message = Validation.message("student_not_insert");
            return null;
        }
        Student student = getStudentByNpm(studentRequest.getNpm());

        if (student == null) {
            message = Validation.message("student_invalid");
            return null;
        } else if (Validation.isNameNotValid(studentRequest.getName())) {
            message = Validation.message("name_invalid");
            return null;
        } else {
            student.setName(studentRequest.getName());
            studentRepository.save(student);
            return new DtoStudentResponse(student);
        }
    }

    /**
     * Memperbarui status Mahasiswa yang ada berdasarkan NPM Mahasiswa.
     *
     * @param studentRequest    Status Mahasiswa yang ingin diperbarui.
     * @return                  True jika berhasil diperbarui, dan false jika gagal.
     */
    public DtoStudentResponse updateStatus(DtoStudentRequest studentRequest) {
        if (studentRequest.getNpm() == null) {
            message = Validation.message("student_not_insert");
            return null;
        }
        Student student = getStudentByNpm(studentRequest.getNpm());

        if (student == null) {
            message = Validation.message("student_invalid");
            return null;
        } else if (studentRequest.getIsActive() == null) {
            message = Validation.message("active_invalid");
            return null;
        } else {
            student.setActive(studentRequest.getIsActive());
            studentRepository.save(student);
            return new DtoStudentResponse(student);
        }
    }

    /**
     * Menghapus Mahasiswa dari dalam daftar.
     *
     * @param npm   NPM Mahasiswa yang akan dihapus.
     * @return      True jika berhasil dihapus, dan false jika gagal.
     */
    public DtoStudentResponse delete(String npm) {
        if (npm == null) {
            message = Validation.message("student_not_insert");
            return null;
        }
        Student student = getStudentByNpm(npm);

        if (student == null) {
            message = Validation.message("student_invalid");
            return null;
        } else {
            student.setDeleted(true);
            student.setDeletedAt(new Date());
            studentRepository.save(student);
            return new DtoStudentResponse(student);
        }
    }

    /**
     * Mengembalikan daftar Mahasiswa.
     *
     * @return      Daftar Mahasiswa.
     */
    public List<Student> studentList() {
        return studentRepository.findAllByIsDeletedIsFalseOrderByNpmAsc();
    }

    public List<DtoStudentResponse> studentResponseList() {
        List<DtoStudentResponse> studentResponses = new ArrayList<>();

        for (Student student : studentList()) {
            studentResponses.add(new DtoStudentResponse(student));
        }
        return studentResponses;
    }

    /**
     * Mengembalikan informasi Mahasiswa berdasarkan NPM.
     *
     * @param npm   NPM Mahasiswa.
     * @return      Mahasiswa jika tersedia, jika tidak tersedia kembalikan null.
     */
    public Student getStudentByNpm(String npm) {
        Optional<Student> studentOptional = studentRepository.findFirstByNpmAndIsDeletedIsFalse(npm);

        if (studentOptional.isPresent()) {
            return studentOptional.get();
        } else {
            message = Validation.message("student_invalid");
            return null;
        }
    }

    public DtoStudentResponse getStudentResponseByNpm(String npm) {
        Student student = getStudentByNpm(npm);

        if (student == null) return null;
        else return new DtoStudentResponse(getStudentByNpm(npm));
    }

    /**
     * Membuat NPM baru berdasarkan tahun, id Jurusan, dan jumlah.
     *
     * @return      NPM Mahasiswa baru.
     */
    private String getNewNPM(int year, String idMajor) {
        Optional<Student> studentOptional = studentRepository.findFirstByNpmContainingOrderByNpmDesc(year + idMajor);

        int count = 1;
        if (studentOptional.isPresent()) {
            int lengthNpm = studentOptional.get().getNpm().length();
            count = Integer.parseInt(studentOptional.get().getNpm().substring(lengthNpm - 4, lengthNpm)) + 1;
        }
        return String.format("%d%s%04d", year, idMajor, count);
    }
}
