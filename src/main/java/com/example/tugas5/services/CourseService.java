package com.example.tugas5.services;

import com.example.tugas5.utilities.Utility;
import com.example.tugas5.dto.Requests.DtoCourseRequest;
import com.example.tugas5.dto.Responses.DtoCourseResponse;
import com.example.tugas5.models.Course;
import com.example.tugas5.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * Menambahkan Mata Kuliah baru.
     *
     * @param courseRequest Mata Kuliah yang akan ditambahkan.
     * @return True jika berhasil ditambahkan, dan false jika gagal.
     */
    public DtoCourseResponse add(DtoCourseRequest courseRequest) {
        Course course = new Course();
        System.out.println(Utility.isNameNotValid(courseRequest.getName()));

        if (courseRequest.getIdCourse() != null && !isCourseIdValid(courseRequest.getIdCourse())) {
            message = Utility.message("course_invalid");
            return null;
        } else if (Utility.isNameNotValid(courseRequest.getName())) {
            message = Utility.message("name_invalid");
            return null;
        } else if (isCreditNotValid(courseRequest.getCredit())) {
            message = Utility.message("credit_invalid");
            return null;
        } else {
            if (courseRequest.getIdCourse() == null) course.setIdCourse(getNewIdCourse(courseRequest.getName()));
            else course.setIdCourse(courseRequest.getIdCourse());

            course.setName(courseRequest.getName());
            course.setCredit(courseRequest.getCredit());
            courseRepository.save(course);
            return new DtoCourseResponse(course);
        }
    }

    private String getNewIdCourse(String name) {
        String[] split = name.split(" ");
        String code;
        if (split.length > 1) {
            code =  String.valueOf(split[0].charAt(0)).toUpperCase() +
                    String.valueOf(split[1].charAt(0)).toUpperCase();
        } else if (split[0].length() > 2) {
            code = split[0].substring(0, 2).toUpperCase();
        } else {
            code = "__";
        }

        Optional<Course> courseOptional = courseRepository.findFirstByIdCourseContainingIgnoreCaseOrderByIdCourseDesc(code);

        int id = 0;
        if (courseOptional.isPresent()) {
            id = Integer.parseInt(courseOptional.get().getIdCourse().substring(2, 5)) + 1;
        }
        return String.format("%s%03d", code, id);
    }

    /**
     * Memperbarui informasi Mata Kuliah yang ada berdasarkan ID Mata Kuliah.
     *
     * @param courseRequest Informasi Mata Kuliah yang ingin diperbarui.
     * @return              True jika berhasil diperbarui, dan false jika gagal.
     */
    public DtoCourseResponse updateData(DtoCourseRequest courseRequest) {
        if (courseRequest.getIdCourse() == null) {
            message = Utility.message("course_not_insert");
            return null;
        }
        Course course = getCourseByIdCourse(courseRequest.getIdCourse());

        if (course == null) {
            message = Utility.message("course_invalid");
            return null;
        } else if (isCreditNotValid(courseRequest.getCredit())) {
            message = Utility.message("credit_invalid");
            return null;
        } else {
            course.setCredit(courseRequest.getCredit());
            courseRepository.save(course);
            return new DtoCourseResponse(course);
        }
    }

    public DtoCourseResponse updateStatus(String idCourse, Boolean isActive) {
        if (idCourse == null) {
            message = Utility.message("course_not_insert");
            return null;
        }
        Course course = getCourseByIdCourse(idCourse);

        if (course == null) {
            message = Utility.message("course_invalid");
            return null;
        } else if (isActive == null) {
            message = Utility.message("active_invalid");
            return null;
        } else {
            course.setActive(isActive);
            courseRepository.save(course);
            return new DtoCourseResponse(course);
        }
    }

    /**
     * Menghapus Mata Kuliah.
     *
     * @param idCourse  ID Mata Kuliah yang akan dihapus.
     * @return          True jika objek berhasil dihapus, dan false jika gagal.
     */
    public DtoCourseResponse delete(String idCourse) {
        if (idCourse == null) {
            message = Utility.message("course_not_insert");
            return null;
        }
        Course course = getCourseByIdCourse(idCourse);

        if (course == null) {
            message = Utility.message("course_invalid");
            return null;
        } else {
            course.setDeleted(true);
            course.setDeletedAt(new Date());
            courseRepository.save(course);
            return new DtoCourseResponse(course);
        }
    }

    public Page<DtoCourseResponse> courseResponseList(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Course> result = courseRepository.findAllByIsDeletedIsFalseOrderByNameAsc(pageable);
        List<DtoCourseResponse> resultDto = new ArrayList<>();

        for (Course course : result.getContent()) {
            resultDto.add(new DtoCourseResponse(course));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    public Page<DtoCourseResponse> courseResponseListByName(String name, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Course> result = courseRepository.findAllByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByNameAsc(name, pageable);
        List<DtoCourseResponse> resultDto = new ArrayList<>();

        for (Course course : result.getContent()) {
            resultDto.add(new DtoCourseResponse(course));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    public Page<DtoCourseResponse> courseResponseListByActive(Boolean isActive, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Course> result = courseRepository.findAllByIsActiveAndIsDeletedIsFalseOrderByNameAsc(isActive, pageable);
        List<DtoCourseResponse> resultDto = new ArrayList<>();

        for (Course course : result.getContent()) {
            resultDto.add(new DtoCourseResponse(course));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    /**
     * Mengembalikan informasi Mata Kuliah berdasarkan ID Mata Kuliah.
     *
     * @param idCourse  ID Mata Kuliah.
     * @return          Mata Kuliah jika tersedia, jika tidak tersedia kembalikan null.
     */
    public Course getCourseByIdCourse(String idCourse) {
        Optional<Course> courseOptional = courseRepository.findFirstByIdCourseAndIsDeletedIsFalse(idCourse);

        if (courseOptional.isPresent()) {
            return courseOptional.get();
        } else {
            message = Utility.message("course_invalid");
            return null;
        }
    }

    public DtoCourseResponse getCourseResponseByIdCourse(String idCourse) {
        Course course = getCourseByIdCourse(idCourse);

        if (course == null) return null;
        else return new DtoCourseResponse(getCourseByIdCourse(idCourse));
    }

    private boolean isCourseIdValid(String idCourse) {
        return idCourse.matches("[a-zA-Z0-9]{5}");
    }

    private boolean isCreditNotValid(Integer credit) {
        return credit == null || credit <= 0;
    }
}
