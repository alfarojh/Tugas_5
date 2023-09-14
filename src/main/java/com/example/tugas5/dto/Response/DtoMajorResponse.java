package com.example.tugas5.dto.Response;

import com.example.tugas5.model.Major;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoMajorResponse {
    private String idMajor;
    private String name;

    public DtoMajorResponse(Major major) {
        this.idMajor = major.getIdMajor();
        this.name = major.getName();
    }

    public String getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(String idMajor) {
        this.idMajor = idMajor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
