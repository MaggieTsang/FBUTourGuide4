package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/5/16.
 */
public class GuideMain extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidenew);
    }

    public void saveProfile(View v) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuideViewRequests.class);
        startActivity(i); // brings up the second activity
    }

}
