package com.example.randomprofile.domain.entity;

import java.io.Serializable;

public class Street implements Serializable {

    private int number;
    private String name;

    public Street(int number, String name){

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
