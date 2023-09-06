package com.example.tugas5.service;

import com.example.tugas5.model.Major;
import com.example.tugas5.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MajorService {
    @Autowired
    private MajorRepository majorRepository;
    private Major current;      // Untuk mengambil data terbaru saat melakukan transaksi.
    private String message;

    public String getMessage() {
        return message;
    }

    public Major getCurrent() {
        return current;
    }

    /**
     * Menambahkan Jurusan baru.
     *
     * @param major Jurusan yang akan ditambahkan.
     * @return      True jika berhasil ditambahkan, dan false jika gagal.
     */
    public boolean add(Major major) {
        if (major.getName() != null && isNameValid(major.getName())) {
            majorRepository.save(major);
            message = "Major added successfully.";
            return true;
        } else {
            message = "Input invalid.";
            return false;
        }
    }

    /**
     * Memperbarui informasi Jurusan yang ada berdasarkan ID Jurusan.
     *
     * @param id    ID Jurusan yang akan diperbarui.
     * @param major Informasi Jurusan yang ingin diperbarui.
     * @return      True jika berhasil diperbarui, dan false jika gagal.
     */
    public boolean updateData(Long id, Major major) {
        Optional<Major> majorOptional = majorRepository.findById(id);
        current = null;

        if (!majorOptional.isPresent() || !majorOptional.get().isExist()) {
            message = "Major ID Not Found.";
            return false;
        } else if (major.getName() == null || !isNameValid(major.getName())) {
            message = "Input invalid.";
            return false;
        } else {
            majorOptional.get().setName(major.getName());
            majorRepository.save(majorOptional.get());
            message = "Major with ID `" + id + "` updated successfully.";
            current = majorOptional.get();
            return true;
        }
    }

    /**
     * Menghapus Jurusan.
     *
     * @param id    ID Jurusan yang akan dihapus.
     * @return      True jika berhasil dihapus, dan false jika gagal.
     */
    public boolean delete(Long id) {
        Optional<Major> majorOptional = majorRepository.findById(id);
        current = null;

        if (!majorOptional.isPresent() || !majorOptional.get().isExist()) {
            message = "Major ID Not Found.";
            return false;
        } else {
            majorOptional.get().delete();
            majorRepository.save(majorOptional.get());
            message = "Major with ID `" + id + "` deleted successfully.";
            current = majorOptional.get();
            return true;
        }
    }

    /**
     * Mengembalikan daftar Jurusan yang masih tersedia.
     *
     * @return      Daftar Jurusan yang masih tersedia.
     */
    public List<Major> majorList() {
        return majorRepository.findAll().stream()
                .filter(Major::isExist)
                .collect(Collectors.toList());
    }

    /**
     * Mengembalikan informasi Jurusan berdasarkan ID Jurusan.
     *
     * @param id    ID Jurusan.
     * @return      Jurusan jika tersedia, jika tidak tersedia kembalikan null.
     */
    public Major getMajorById(long id) {
        Optional<Major> majorOptional = majorRepository.findById(id);
        if (majorOptional.isPresent() && majorOptional.get().isExist()) {
            message = "Major ID Found.";
            return majorOptional.get();
        } else {
            message = "Major ID Not Found.";
            return null;
        }
    }

    /**
     * Memeriksa apakah sebuah nama valid.
     * Nama yang valid hanya mengandung huruf (a-z, A-Z), angka (0-9), dan spasi.
     *
     * @param name      Nama yang akan diperiksa.
     * @return true     Jika nama valid, false jika tidak valid.
     */
    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z0-9\\s]+");
    }
}
