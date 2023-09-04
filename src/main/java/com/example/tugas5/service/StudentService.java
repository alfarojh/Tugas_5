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
    private Student currentStudent;
    private String message;

    public String getMessage() {
        return message;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public boolean add(Student student) {
        currentStudent = null;
        if (!majorInterface.existsById(student.getMajor().getId())) {
            message = "Student NPM Not Found.";
            return false;
        } else if (isNameNotValid(student.getName())) {
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

    public boolean updateData(String npm, Student student) {
        Optional<Student> studentOptional = studentInterface.findById(npm);
        currentStudent = null;

        if (!studentOptional.isPresent()) {
            message = "Student NPM Not Found.";
            return false;
        } else if (isNameNotValid(student.getName())) {
            message = "Update failed.";
            return false;
        } else {
            studentOptional.get().setName(student.getName());
            studentInterface.save(studentOptional.get());
            message = "Student with NPM `" + npm + "` updated successfully.";
            currentStudent = studentOptional.get();
            return true;
        }
    }

    public boolean updateStatus(String npm, Student student) {
        Optional<Student> studentOptional = studentInterface.findById(npm);
        currentStudent = null;

        if (!studentOptional.isPresent()) {
            message = "Student NPM Not Found.";
            return false;
        } else {
            studentOptional.get().setActive(student.isActive());
            studentInterface.save(studentOptional.get());
            currentStudent = studentOptional.get();
            if (student.isActive()) {
                message = "Student NPM `" + npm + "` is now active.";
            } else {
                message = "Student NPM `" + npm + "` is now inactive.";
            }
            return true;
        }
    }

    public boolean delete(String npm) {
        Optional<Student> studentOptional = studentInterface.findById(npm);
        currentStudent = null;

        if (!studentOptional.isPresent()) {
            message = "Student NPM Not Found.";
            return false;
        } else {
            studentOptional.get().delete();
            studentInterface.save(studentOptional.get());
            message = "Student with NPM `" + npm + "` deleted successfully.";
            currentStudent = studentOptional.get();
            return true;
        }
    }

    public List<Student> studentList() {
        return studentInterface.findAll().stream()
                .filter(Student::isExist)
                .collect(Collectors.toList());
    }

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

    private boolean isNameNotValid(String name) {
        return !name.matches("[a-zA-Z0-9\\s]+");
    }

}
