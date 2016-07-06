package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ekok.fbutourguideapp.R;
import com.example.ekok.fbutourguideapp.ViewGuideProfile;

import java.util.ArrayList;

/**
 * Created by mbytsang on 7/5/16.
 */
public class GuideViewRequests extends AppCompatActivity{

    ArrayList<String> requests;
    ArrayAdapter<String> requestsAdapter;
    ListView lvRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiderequests);

        lvRequests = (ListView) findViewById(R.id.lvRequests);
        requests = new ArrayList<>();
        requestsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requests);
        lvRequests.setAdapter(requestsAdapter);

        for (int i = 0; i < 25; i++) {
            requests.add("So many requests, Wa0w");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    public void editProfile(MenuItem item) {
        //Intent i = new Intent(this, GuideMain.class);
        Intent i = new Intent(this, ViewGuideProfile.class);
        startActivity(i);
    }

    public void goToCalender(MenuItem item) {
        Intent i = new Intent(this, GuideCalender.class);
        startActivity(i);
    }
}
