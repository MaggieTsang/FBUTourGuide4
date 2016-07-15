package com.example.ekok.fbutourguideapp;

/**
 * Created by ekok on 7/15/16.
 */
public class User {
    String Uid;
    String displayName;
    String email;

    public User() {}

    public User (String Uid, String displayName, String email) {
        this.Uid = Uid;
        this.displayName = displayName;
        this.email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
