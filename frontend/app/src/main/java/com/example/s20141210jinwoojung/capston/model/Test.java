package com.example.s20141210jinwoojung.capston.model;
//
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {
    @SerializedName("data")
    @Expose
    private TestData testdata;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public TestData getTestData() {
        return testdata;
    }

    public void setTestData(TestData testdata) {
        this.testdata = testdata;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
