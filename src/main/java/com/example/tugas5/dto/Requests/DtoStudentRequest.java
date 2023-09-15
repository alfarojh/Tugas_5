package com.example.tugas5.dto.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoStudentRequest {
    @JsonProperty("npm")
    private String npm;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("id_major")
    private String idMajor;
    @JsonProperty("is_active")
    private Boolean isActive;

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(String idMajor) {
        this.idMajor = idMajor.length() == 1 ? "0" + idMajor : idMajor;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
