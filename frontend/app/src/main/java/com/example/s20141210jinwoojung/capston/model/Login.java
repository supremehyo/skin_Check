package com.example.s20141210jinwoojung.capston.model;

public class Login {
    public String email;
    public String password;
//
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
