package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ekok.fbutourguideapp.Login.UserType;
import com.example.ekok.fbutourguideapp.R;

import java.util.ArrayList;

/**
 * Created by mbytsang on 7/5/16.
 */
public class GuideViewRequests extends AppCompatActivity{
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    ArrayList<String> requests;
    ArrayAdapter<String> requestsAdapter;
    ListView lvRequests;

    GuideUser guideUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiderequests);

        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");

        lvRequests = (ListView) findViewById(R.id.lvRequests);
        requests = new ArrayList<>();
        requestsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requests);

        lvRequests.setAdapter(requestsAdapter);
        requests.add("So many requests, Wa0w");



//        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        String restoredText = prefs.getString("etPlace", null);
//        if (restoredText != null) {
//            String etPlace = prefs.getString("etPlace", "No place defined");//"No name defined" is the default value.
//            requests.add(0, etPlace);
//            notify();
//        }

        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        String m = bb.getString("etPlace", "");
        requests.add(m);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    public void editProfile(MenuItem item) {
        //Intent i = new Intent(this, GuideMain.class);
        Intent i = new Intent(this, GuideViewProfile.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i);
    }

    public void goToCalender(MenuItem item) {
        Intent i = new Intent(this, GuideCalender.class);
        startActivity(i);
    }

    public void goToHome(MenuItem item) {
        Intent i = new Intent(this, UserType.class);
        startActivity(i);
    }
}
