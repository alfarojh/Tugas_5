package com.example.tugas5.service;

import com.example.tugas5.Utility.Validation;
import com.example.tugas5.dto.Request.DtoMajorRequest;
import com.example.tugas5.dto.Response.DtoMajorResponse;
import com.example.tugas5.model.Major;
import com.example.tugas5.repository.MajorRepository;
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
public class MajorService {
    @Autowired
    private MajorRepository majorRepository;
    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * Menambahkan Jurusan baru.
     *
     * @param majorRequest  Jurusan yang akan ditambahkan.
     * @return              True jika berhasil ditambahkan, dan false jika gagal.
     */
    public DtoMajorResponse add(DtoMajorRequest majorRequest) {
        Major major = new Major();

        if (majorRequest.getIdMajor() == null || getMajorById(majorRequest.getIdMajor()) != null) {
            message = Validation.message("major_invalid");
            return null;
        } else if (Validation.isNameNotValid(majorRequest.getName())) {
            message = Validation.message("name_invalid");
            return null;
        } else {
            major.setIdMajor(majorRequest.getIdMajor());
            major.setName(majorRequest.getName());
            majorRepository.save(major);
            return new DtoMajorResponse(major);
        }
    }

    /**
     * Memperbarui informasi Jurusan yang ada berdasarkan ID Jurusan.
     *
     * @param majorRequest  Informasi Jurusan yang ingin diperbarui.
     * @return              True jika berhasil diperbarui, dan false jika gagal.
     */
    public DtoMajorResponse updateData(DtoMajorRequest majorRequest) {
        if (majorRequest.getIdMajor() == null) {
            message = Validation.message("major_not_insert");
            return null;
        }
        Major major = getMajorById(majorRequest.getIdMajor());

        if (major == null) {
            message = Validation.message("major_invalid");
            return null;
        } else if (Validation.isNameNotValid(majorRequest.getName())) {
            message = Validation.message("name_invalid");
            return null;
        } else {
            major.setName(majorRequest.getName());
            majorRepository.save(major);
            return new DtoMajorResponse(major);
        }
    }

    /**
     * Menghapus Jurusan.
     *
     * @param idMajor   ID Jurusan yang akan dihapus.
     * @return          True jika berhasil dihapus, dan false jika gagal.
     */
    public DtoMajorResponse delete(String idMajor) {
        if (idMajor == null) {
            message = Validation.message("major_not_insert");
            return null;
        }
        Major major = getMajorById(idMajor);

        if (major == null) {
            message = Validation.message("major_invalid");
            return null;
        } else {
            major.setDeleted(true);
            major.setDeletedAt(new Date());
            majorRepository.save(major);
            return new DtoMajorResponse(major);
        }
    }

    /**
     * Mengembalikan daftar Jurusan yang masih tersedia.
     *
     * @return Daftar Jurusan yang masih tersedia.
     */
    public List<Major> majorList() {
        return majorRepository.findAllByIsDeletedIsFalseOrderByIdMajorAsc();
    }

    public Page<DtoMajorResponse> majorListResponse(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Major> result = majorRepository.findAllByIsDeletedIsFalseOrderByIdMajorAsc(pageable);
        List<DtoMajorResponse> resultDto = new ArrayList<>();

        for (Major major : result.getContent()) {
            resultDto.add(new DtoMajorResponse(major));
        }
        return new PageImpl<>(resultDto, pageable, result.getTotalElements());
    }

    /**
     * Mengembalikan informasi Jurusan berdasarkan ID Jurusan.
     *
     * @param idMajor   ID Jurusan.
     * @return          Jurusan jika tersedia, jika tidak tersedia kembalikan null.
     */
    public Major getMajorById(String idMajor) {
        Optional<Major> majorExist = majorRepository.findFirstByIdMajorAndIsDeletedIsFalse(idMajor);
        if (majorExist.isPresent()) {
            return majorExist.get();
        } else {
            message = Validation.message("major_invalid");
            return null;
        }
    }

    public DtoMajorResponse getMajorResponseByIdMajor(String idMajor) {
        Major major = getMajorById(idMajor);

        if (major == null) return null;
        else return new DtoMajorResponse(getMajorById(idMajor));
    }
}
