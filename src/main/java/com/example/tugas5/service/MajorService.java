package com.example.tugas5.service;

import com.example.tugas5.Utility.Validation;
import com.example.tugas5.dto.Request.DtoMajorRequest;
import com.example.tugas5.dto.Response.DtoMajorResponse;
import com.example.tugas5.model.Major;
import com.example.tugas5.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param dtoMajorRequest   Jurusan yang akan ditambahkan.
     * @return                  True jika berhasil ditambahkan, dan false jika gagal.
     */
    public DtoMajorResponse add(DtoMajorRequest dtoMajorRequest) {
        Major major = new Major();

        if (getMajorById(dtoMajorRequest.getIdMajor()) != null) {
            message = Validation.message("major_invalid");
            return null;
        } else if (Validation.isNameNotValid(dtoMajorRequest.getName())) {
            message = Validation.message("name_invalid");
            return null;
        } else {
            major.setIdMajor(dtoMajorRequest.getIdMajor());
            major.setName(dtoMajorRequest.getName());
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
    public List<DtoMajorResponse> majorListResponse() {
        List<DtoMajorResponse> dtoMajorResponse = new ArrayList<>();

        for (Major major : majorList()) {
            dtoMajorResponse.add(new DtoMajorResponse(major));
        }
        return dtoMajorResponse;
    }

    public List<Major> majorList() {
        return majorRepository.findAllByIsDeletedIsFalseOrderByIdMajorAsc();
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
