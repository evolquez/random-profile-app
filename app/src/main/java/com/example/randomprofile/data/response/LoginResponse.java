package com.example.randomprofile.data.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String uuid;
    private String username;
    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;

    public LoginResponse(String uuid, String username, String password, String salt, String md5, String sha1, String sha256) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.md5 = md5;
        this.sha1 = sha1;
        this.sha256 = sha256;
    }
}
