package com.example.ekok.fbutourguideapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ekok.fbutourguideapp.Guides.GuideBasic;
import com.example.ekok.fbutourguideapp.Guides.GuideUser;
import com.example.ekok.fbutourguideapp.R;
import com.example.ekok.fbutourguideapp.Travelers.TravelerMain;

/**
 * Created by mbytsang on 7/5/16.
 */
public class UserType extends AppCompatActivity{

    GuideUser guideUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);
    }

    // From user type to either traveler or guide
    public void launchTraveler(View v) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, TravelerMain.class);
        startActivity(i); // brings up the second activity
    }

    public void launchGuide(View v) {
        // first parameter is the context, second is the class of the activity to launch
        guideUser = new GuideUser();
        Intent i = new Intent(this, GuideBasic.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }

}
