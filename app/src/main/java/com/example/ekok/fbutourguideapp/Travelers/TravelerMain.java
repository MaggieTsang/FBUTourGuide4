package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ekok.fbutourguideapp.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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

        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://<1:656827361402:android:0a8baf8cc4e28fcdP>.firebaseio.com/");
        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
        myFirebaseRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }
            @Override public void onCancelled(FirebaseError error) { }
        });
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
