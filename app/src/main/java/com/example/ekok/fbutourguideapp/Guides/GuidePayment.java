package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/7/16.
 */
public class GuidePayment extends AppCompatActivity {

    GuideUser guideUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidepaymentinfo);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");

    }

    public void launchViewProfile(View v) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuideViewProfile.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }
}