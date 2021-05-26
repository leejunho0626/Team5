package com.example.a5teamproject;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserWrite {

    public String write;

    public UserWrite() {

    }

    public UserWrite(String write) {

        this.write = write;
    }
    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

}
