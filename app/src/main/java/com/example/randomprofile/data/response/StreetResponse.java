package com.example.randomprofile.data.response;

import java.io.Serializable;

public class StreetResponse implements Serializable {

    private int number;
    private String name;

    public StreetResponse(int number, String name){

        this.number = number;
        this.name   = name;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
