package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/6/16.
 */
public class GuideViewProfile extends AppCompatActivity{

    GuideUser guideUser;

    //Traveler's view: can request/message
    //Guide's view: just a preview, cannot request or message themself
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideprofilepreview);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");

    }

    public void goBackToBasic(View view) {
        Intent i = new Intent(this, GuideBasic.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i);
    }

    public void saveProfile(View view) {
        Intent i = new Intent(this, GuideViewRequests.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i);
    }
}
