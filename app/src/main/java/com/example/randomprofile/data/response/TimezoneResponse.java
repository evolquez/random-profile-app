package com.example.randomprofile.data.response;

import java.io.Serializable;

public class TimezoneResponse implements Serializable {
    private String offset;
    private String description;

    public TimezoneResponse(String offset, String description) {
        this.offset = offset;
        this.description = description;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
