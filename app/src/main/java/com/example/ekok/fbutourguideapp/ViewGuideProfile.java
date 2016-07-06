package com.example.ekok.fbutourguideapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mbytsang on 7/6/16.
 */
public class ViewGuideProfile extends AppCompatActivity{


    //Traveler's view: can request/message
    //Guide's view: just a preview, cannot request or message themself
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_viewguideprofile);
    }

}
