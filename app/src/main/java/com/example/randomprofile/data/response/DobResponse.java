package com.example.randomprofile.data.response;

import java.io.Serializable;
import java.util.Date;

public class DobResponse implements Serializable {
    private Date date;
    private int age;

    public DobResponse(Date date, int age) {
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
