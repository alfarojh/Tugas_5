package com.example.tugas5.service;

import com.example.tugas5.model.Major;
import com.example.tugas5.repository.MajorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MajorService {
    @Autowired
    private MajorInterface majorInterface;
    private Major currentMajor;
    private String message;

    public String getMessage() {
        return message;
    }

    public Major getCurrentMajor() {
        return currentMajor;
    }

    public boolean add(Major major) {
        if (major.getName() != null && isNameValid(major.getName())) {
            majorInterface.save(major);
            message = "Major added successfully.";
            return true;
        } else {
            message = "Input invalid.";
            return false;
        }
    }

    public boolean updateData(Long id, Major major) {
        Optional<Major> majorOptional = majorInterface.findById(id);
        currentMajor = null;

        if (!majorOptional.isPresent() || !majorOptional.get().isExist()) {
            message = "Major ID Not Found.";
            return false;
        } else if (major.getName() == null || !isNameValid(major.getName())) {
            message = "Input invalid.";
            return false;
        } else {
            majorOptional.get().setName(major.getName());
            majorInterface.save(majorOptional.get());
            message = "Major with ID `" + id + "` updated successfully.";
            currentMajor = majorOptional.get();
            return true;
        }
    }

    public boolean delete(Long id) {
        Optional<Major> majorOptional = majorInterface.findById(id);
        currentMajor = null;

        if (!majorOptional.isPresent() || !majorOptional.get().isExist()) {
            message = "Major ID Not Found.";
            return false;
        } else {
            majorOptional.get().delete();
            majorInterface.save(majorOptional.get());
            message = "Major with ID `" + id + "` deleted successfully.";
            currentMajor = majorOptional.get();
            return true;
        }
    }

    public List<Major> majorList() {
        return majorInterface.findAll().stream()
                .filter(Major::isExist)
                .collect(Collectors.toList());
    }

    public Major getMajorById(long id) {
        Optional<Major> majorOptional = majorInterface.findById(id);
        if (majorOptional.isPresent() && majorOptional.get().isExist()) {
            message = "Major ID Found.";
            return majorOptional.get();
        } else {
            message = "Major ID Not Found.";
            return null;
        }
    }

    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z0-9\\s]+");
    }
}
