package com.example.a5teamproject;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserWrite {

    public String write;
    public String publisher;
    public String date;

    public UserWrite(String write, String publisher, String date) {
        this.write = write;
        this.publisher = publisher;
        this.date = date;

    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
