package com.example.webonise.mvpmvvmdemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * User Model class to get set name ,email and body
 */
public class UserModel {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("body")
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserModel(String name, String email, String body) {
        this.name = name;
        this.email = email;
        this.body = body;
    }
}
