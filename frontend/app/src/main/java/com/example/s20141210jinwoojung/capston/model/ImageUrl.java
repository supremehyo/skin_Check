package com.example.s20141210jinwoojung.capston.model;

import com.google.gson.annotations.SerializedName;

public class ImageUrl {
    @SerializedName("filename")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
