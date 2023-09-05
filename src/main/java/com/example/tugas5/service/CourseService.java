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
    private Course currentCourse;
    private String message;

    public String getMessage() {
        return message;
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

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

    public boolean updateData(Long id, Course course) {
        Optional<Course> courseOptional = courseInterface.findById(id);
        currentCourse = null;

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
            currentCourse = courseOptional.get();
            return true;
        }
    }

    public boolean updateStatus(long id, Course course) {
        Optional<Course> courseOptional = courseInterface.findById(id);
        currentCourse = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            courseOptional.get().setActive(course.isActive());
            courseInterface.save(courseOptional.get());
            currentCourse = courseOptional.get();
            if (course.isActive()) {
                message = "Course ID `" + id + "` is now active.";
            } else {
                message = "Course ID `" + id + "` is now inactive.";
            }
            return true;
        }
    }

    public boolean delete(Long id) {
        Optional<Course> courseOptional = courseInterface.findById(id);
        currentCourse = null;

        if (!courseOptional.isPresent() || !courseOptional.get().isExist()) {
            message = "Course ID Not Found.";
            return false;
        } else {
            courseOptional.get().delete();
            courseInterface.save(courseOptional.get());
            message = "Course with ID `" + id + "` deleted successfully.";
            currentCourse = courseOptional.get();
            return true;
        }
    }

    public List<Course> courseList() {
        return courseInterface.findAll().stream()
                .filter(Course::isExist)
                .collect(Collectors.toList());
    }

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

    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z0-9\\s]+");
    }
}
