package com.example.ekok.fbutourguideapp;

/**
 * Created by ekok on 7/11/16.
 */
public class FirebaseStart extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        com.firebase.client.Firebase.setAndroidContext(this);
    }
}
