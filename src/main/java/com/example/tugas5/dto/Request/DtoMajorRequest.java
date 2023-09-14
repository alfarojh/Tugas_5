package com.example.tugas5.dto.Request;

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
        this.idMajor = idMajor.length() == 1 ? "0" + idMajor : idMajor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }
}
