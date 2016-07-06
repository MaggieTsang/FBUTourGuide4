package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ekok.fbutourguideapp.R;

import java.util.ArrayList;

/**
 * Created by mbytsang on 7/5/16.
 */
public class TravelerMain extends AppCompatActivity{

    ArrayList<String> trips;
    ArrayAdapter<String> tripsAdapter;
    ListView lvTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelermain);



        lvTrips = (ListView) findViewById(R.id.lvTrips);
        trips = new ArrayList<>();
        tripsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, trips);
        lvTrips.setAdapter(tripsAdapter);
        for (int i = 0; i < 25; i++) {
            trips.add("Le Tripsss");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_traveler, menu);
        return true;
    }


    public void makeNewRequest(MenuItem item) {
        Intent i = new Intent(this, TravelerNewTrip.class);
        startActivity(i);
    }
}
