package com.example.randomprofile.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class Dob implements Serializable {
    private Date date;
    private int age;

    public Dob(Date date, int age) {
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
