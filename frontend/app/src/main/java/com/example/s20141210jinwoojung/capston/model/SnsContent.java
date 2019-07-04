package com.example.s20141210jinwoojung.capston.model;

public class SnsContent {
    private int mImage;
    private String mSubject;
    private String content;

    public SnsContent(int mImage, String mSubject, String content) {
        this.mImage = mImage;
        this.mSubject = mSubject;
        this.content = content;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
