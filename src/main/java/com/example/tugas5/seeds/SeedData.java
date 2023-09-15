package com.example.tugas5.seeds;

import com.example.tugas5.models.Course;
import com.example.tugas5.models.Major;
import com.example.tugas5.models.Student;
import com.example.tugas5.repositories.CourseRepository;
import com.example.tugas5.repositories.MajorRepository;
import com.example.tugas5.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeedData {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private StudentRepository studentRepository;

    public void seed() {
        if (majorRepository.count() == 0) {
            List<Major> majorList = new ArrayList<>();
            majorList.add(new Major("01", "Teknik Informatika"));
            majorList.add(new Major("02", "Sistem Informasi"));
            majorList.add(new Major("03", "Manajemen Bisnis"));

            List<Course> courseList = new ArrayList<>();
            courseList.add(new Course("MD001", "Matematika Diskrit", 2));
            courseList.add(new Course("LO001", "Logika", 2));
            courseList.add(new Course("PB001", "PBO", 4));
            courseList.add(new Course("DP001", "Dasar Pemrograman 1", 2));
            courseList.add(new Course("DP002", "Dasar Pemrograman 2", 2));

            List<Student> studentList = new ArrayList<>();
            studentList.add(new Student("2010020001", "Jimmy", "08231165486", "Bandung", 2010, majorList.get(1)));
            studentList.add(new Student("2011030001", "Louis", "0815954854", "Jakarta", 2011, majorList.get(2)));
            studentList.add(new Student("2012010001", "David", "0891569845", "Surabaya", 2012, majorList.get(0)));
            studentList.add(new Student("2013020001", "Frank", "0854528956", "Malang", 2013, majorList.get(1)));
            studentList.add(new Student("2014030001", "Emily", "08651257951", "Bekasi", 2014, majorList.get(2)));
            studentList.add(new Student("2015010001", "Liam", "08235987456", "Banten", 2015, majorList.get(0)));
            studentList.add(new Student("2016020001", "Jack", "0816529023", "Bandung", 2016, majorList.get(1)));
            studentList.add(new Student("2017030001", "George", "08954602135", "Jakarta", 2017, majorList.get(2)));
            studentList.add(new Student("2018010001", "William", "0856420123", "Surabaya", 2018, majorList.get(0)));
            studentList.add(new Student("2019020001", "Angelina", "08589548754", "Depok", 2019, majorList.get(1)));

            majorRepository.saveAll(majorList);
            courseRepository.saveAll(courseList);
            studentRepository.saveAll(studentList);
        }
    }
}
