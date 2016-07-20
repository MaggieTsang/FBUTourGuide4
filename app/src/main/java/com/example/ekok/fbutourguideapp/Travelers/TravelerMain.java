package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mbytsang on 7/5/16.
 */
public class TravelerMain extends AppCompatActivity{
    private final int REQUEST_CODE = 20;
    private static final String TAG = "Firebase";

    ArrayList<String> trips;
    ListView lvTrips;
    private DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users").child("Traveler").child("trips_current");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelermain);

        lvTrips = (ListView) findViewById(R.id.lvTrips);
        trips = new ArrayList<String>();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataRef : dataSnapshot.getChildren()) {
                    Log.d("trips_current", dataRef.getKey());
                    String ref = dataRef.child("trips_current").getValue().toString();
                    trips.add(ref);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(TravelerMain.this, android.R.layout.simple_list_item_1, trips);
                lvTrips.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lvTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "clicked!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_traveler, menu);
        return true;
    }


    public void makeNewRequest(MenuItem item) {
        Intent i = new Intent(this, NewRequest.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract place value from result extras
            RequestModel requestModel = new RequestModel();
            String place = data.getExtras().getString("place");
        }
    }
}
