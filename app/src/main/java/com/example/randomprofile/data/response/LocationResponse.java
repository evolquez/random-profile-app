package com.example.randomprofile.data.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationResponse implements Serializable {

    @SerializedName("street")
    private StreetResponse streetResponse;
    private String city;
    private String state;
    private String country;
    private int postcode;
    @SerializedName("coordinates")
    private CoordinatesResponse coordinatesResponse;
    @SerializedName("timezone")
    private TimezoneResponse timezoneResponse;

    public LocationResponse(StreetResponse streetResponse, String city, String state, String country, int postcode, CoordinatesResponse coordinatesResponse, TimezoneResponse timezoneResponse) {
        this.streetResponse = streetResponse;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
        this.coordinatesResponse = coordinatesResponse;
        this.timezoneResponse = timezoneResponse;
    }

    public StreetResponse getStreetResponse() {
        return streetResponse;
    }

    public void setStreetResponse(StreetResponse streetResponse) {
        this.streetResponse = streetResponse;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public CoordinatesResponse getCoordinatesResponse() {
        return coordinatesResponse;
    }

    public void setCoordinatesResponse(CoordinatesResponse coordinatesResponse) {
        this.coordinatesResponse = coordinatesResponse;
    }

    public TimezoneResponse getTimezoneResponse() {
        return timezoneResponse;
    }

    public void setTimezoneResponse(TimezoneResponse timezoneResponse) {
        this.timezoneResponse = timezoneResponse;
    }
}
