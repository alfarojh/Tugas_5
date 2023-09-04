package com.example.tugas5.model;

public class ApiResponse {
    private final String message;   // Pesan yang akan ditampilkan dalam bentuk JSON
    private Object data;          // Objek yang akan ditampilkan dalam bentuk JSON

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
