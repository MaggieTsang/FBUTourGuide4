package com.example.ekok.fbutourguideapp.Travelers;

import com.firebase.client.Firebase;

/**
 * Created by ekok on 7/11/16.
 */
public class Traveller extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
