package com.example.randomprofile.data.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileResponse implements Serializable {
    private String gender;
    private Name name;
    @SerializedName("location")
    private LocationResponse locationResponse;
    private String email;
    @SerializedName("login")
    private LoginResponse loginResponse;
    @SerializedName("dob")
    private DobResponse dobResponse;
    @SerializedName("registered")
    private RegisteredResponse registeredResponse;
    private String phone;
    private String cell;
    private Id id;
    @SerializedName("picture")
    private PictureResponse pictureResponse;
    private String nat;

    public ProfileResponse(String gender, Name name, LocationResponse locationResponse, String email, LoginResponse loginResponse, DobResponse dobResponse,
                           RegisteredResponse registeredResponse, String phone, String cell, Id id, PictureResponse pictureResponse, String nat) {
        this.gender = gender;
        this.name = name;
        this.locationResponse = locationResponse;
        this.email = email;
        this.loginResponse = loginResponse;
        this.dobResponse = dobResponse;
        this.registeredResponse = registeredResponse;
        this.phone = phone;
        this.cell = cell;
        this.id = id;
        this.pictureResponse = pictureResponse;
        this.nat = nat;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public LocationResponse getLocationResponse() {
        return locationResponse;
    }

    public void setLocationResponse(LocationResponse locationResponse) {
        this.locationResponse = locationResponse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public DobResponse getDobResponse() {
        return dobResponse;
    }

    public void setDobResponse(DobResponse dobResponse) {
        this.dobResponse = dobResponse;
    }

    public RegisteredResponse getRegisteredResponse() {
        return registeredResponse;
    }

    public void setRegisteredResponse(RegisteredResponse registeredResponse) {
        this.registeredResponse = registeredResponse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public PictureResponse getPictureResponse() {
        return pictureResponse;
    }

    public void setPictureResponse(PictureResponse pictureResponse) {
        this.pictureResponse = pictureResponse;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }
}
