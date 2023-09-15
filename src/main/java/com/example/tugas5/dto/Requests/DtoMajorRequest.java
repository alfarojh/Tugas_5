package com.example.tugas5.dto.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoMajorRequest {
    @JsonProperty("id_major")
    private String idMajor;
    @JsonProperty("name")
    private String name;

    public String getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(String idMajor) {
        this.idMajor = idMajor.trim().length() == 1 ? "0" + idMajor.trim() : idMajor.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }
}
