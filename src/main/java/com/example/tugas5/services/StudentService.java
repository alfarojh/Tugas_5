package com.example.tugas5.services;

import com.example.tugas5.utilities.Utility;
import com.example.tugas5.dto.Requests.DtoStudentRequest;
import com.example.tugas5.dto.Responses.DtoStudentResponse;
import com.example.tugas5.models.Major;
import com.example.tugas5.models.Student;
import com.example.tugas5.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if (studentRequest.getIdMajor() == null) {
            message = Utility.message("major_not_insert");
            return null;
        }

        Major major = majorService.getMajorById(studentRequest.getIdMajor());

        if (major == null) {
            message = Utility.message("major_invalid");
            return null;
        } else if (Utility.isNameNotValid(studentRequest.getName())) {
            message = Utility.message("name_invalid");
            return null;
        } else {
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
            message = Utility.message("student_not_insert");
            return null;
        }
        Student student = getStudentByNpm(studentRequest.getNpm());

        if (student == null) {
            message = Utility.message("student_invalid");
            return null;
        } else if (Utility.isNameNotValid(studentRequest.getName())) {
            message = Utility.message("name_invalid");
            return null;
        } else {
            student.setName(studentRequest.getName());
            studentRepository.save(student);
            return new DtoStudentResponse(student);
        }
    }

    public DtoStudentResponse updateStatus(String npm, Boolean isActive) {
        if (npm == null) {
            message = Utility.message("student_not_insert");
            return null;
        }
        Student student = getStudentByNpm(npm);

        if (student == null) {
            message = Utility.message("student_invalid");
            return null;
        } else if (isActive == null) {
            message = Utility.message("active_invalid");
            return null;
        } else {
            student.setActive(isActive);
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
            message = Utility.message("student_not_insert");
            return null;
        }
        Student student = getStudentByNpm(npm);

        if (student == null) {
            message = Utility.message("student_invalid");
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

    public Page<DtoStudentResponse> studentResponseList(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> result = studentRepository.findAllByIsDeletedIsFalseOrderByNpmAsc(pageable);
        List<DtoStudentResponse> resultDto = new ArrayList<>();

        for (Student student : result.getContent()) {
            resultDto.add(new DtoStudentResponse(student));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    public Page<DtoStudentResponse> studentResponseListByName(String name, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> result = studentRepository.findAllByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByNpmAsc(name, pageable);
        List<DtoStudentResponse> resultDto = new ArrayList<>();

        for (Student student : result.getContent()) {
            resultDto.add(new DtoStudentResponse(student));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    public Page<DtoStudentResponse> studentResponseListByYear(Integer startYear, Integer endYear, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> result = studentRepository.findAllByYearBetweenAndIsDeletedIsFalseOrderByNpmAsc(startYear, endYear, pageable);
        List<DtoStudentResponse> resultDto = new ArrayList<>();

        for (Student student : result.getContent()) {
            resultDto.add(new DtoStudentResponse(student));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    public Page<DtoStudentResponse> studentResponseListByActive(Boolean isActive, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> result = studentRepository.findAllByIsActiveAndIsDeletedIsFalseOrderByNpmAsc(isActive, pageable);
        List<DtoStudentResponse> resultDto = new ArrayList<>();

        for (Student student : result.getContent()) {
            resultDto.add(new DtoStudentResponse(student));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    public Page<DtoStudentResponse> studentResponseListByIdMajor(String idMajor, int page, int limit) {
        if (idMajor.length() == 1) idMajor = "0" + idMajor;
        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> result = studentRepository.findAllByMajor_IdMajorAndIsDeletedIsFalseOrderByNpmAsc(idMajor, pageable);
        List<DtoStudentResponse> resultDto = new ArrayList<>();

        for (Student student : result.getContent()) {
            resultDto.add(new DtoStudentResponse(student));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
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
            message = Utility.message("student_invalid");
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
