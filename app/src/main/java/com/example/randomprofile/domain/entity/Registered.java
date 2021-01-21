package com.example.randomprofile.domain.entity;

import java.util.Date;

public class Registered {

    private Date date;
    private int age;

    public Registered(Date date, int age) {
        this.date = date;
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
