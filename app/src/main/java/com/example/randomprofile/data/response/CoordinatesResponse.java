package com.example.randomprofile.data.response;

import java.io.Serializable;

public class CoordinatesResponse implements Serializable {

    private String latitude;
    private String longitude;

    public CoordinatesResponse(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
