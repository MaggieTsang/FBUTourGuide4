package com.example.ekok.fbutourguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.Login.UserLogin;
import com.example.ekok.fbutourguideapp.Login.UserType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(this, UserType.class);
            this.startActivity(i);
            this.finishActivity(0);
            Toast.makeText(getApplicationContext(), "Welcome back, " + user.getDisplayName() + "!", Toast.LENGTH_SHORT).show();
        } else {
            // No user is signed in
            Intent i = new Intent(this, UserLogin.class);
            this.startActivity(i);
            this.finishActivity(0);
        }
    }
}
