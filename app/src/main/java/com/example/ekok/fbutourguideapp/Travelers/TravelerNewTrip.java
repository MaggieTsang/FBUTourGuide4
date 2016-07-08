package com.example.ekok.fbutourguideapp.Travelers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/5/16.
 */
public class TravelerNewTrip extends AppCompatActivity{
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelernew);
    }

    public void submitRequest(View view) {
//        String etPlace = findViewById(R.id.etPlace).toString();
//
//        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putString("etPlace", etPlace);
//        editor.commit();
//        finish();
        String mailID = findViewById(R.id.etPlace).toString();

        //Here define all your sharedpreferences code with key and value
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("etPlace", mailID );
        edit.commit();
        finish();
    }
}
