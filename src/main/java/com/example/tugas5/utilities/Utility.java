package com.example.tugas5.utilities;

import java.util.Locale;
import java.util.ResourceBundle;

public class utility {

    public static String message(String key) {
        return ResourceBundle.getBundle("messages", Locale.getDefault()).getString(key);
    }

    /**
     * Memeriksa apakah sebuah nama valid.
     * Nama yang valid hanya mengandung huruf (a-z, A-Z), angka (0-9), dan spasi.
     *
     * @param name      Nama yang akan diperiksa.
     * @return true     Jika nama valid, false jika tidak valid.
     */
    public static boolean isNameNotValid(String name) {
        return (name == null) || !name.matches("[a-zA-Z0-9\\s]+");
    }
}
