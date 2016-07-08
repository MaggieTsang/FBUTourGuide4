package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/7/16.
 */
public class GuideContact extends AppCompatActivity {

    GuideUser guideUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidecontactinfo);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");

    }

    public void launchPayment(View v) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuidePayment.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }
}
