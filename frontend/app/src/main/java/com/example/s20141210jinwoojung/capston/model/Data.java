package com.example.s20141210jinwoojung.capston.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//
public class Data {
    @SerializedName("token")
    @Expose
    private String token;
    private int status_code;

    public Data(String token, int status_code) {
        this.token = token;
        this.status_code = status_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
