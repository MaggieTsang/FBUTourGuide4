package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ekok on 7/5/16.
 */
public class TravelerViewTrips extends AppCompatActivity{
    private final int REQUEST_CODE = 20;
    private static final String TAG = "Firebase";

    ArrayList<String> trips;
    ArrayList<String> requestIDs;
    ArrayAdapter<String> tripsAdapter;
    ListView lvTrips;

    TravelerRequestModel requestInfo;
    DatabaseReference dataRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelermain);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo_app);

        dataRef = FirebaseDatabase.getInstance().getReference();

        lvTrips = (ListView) findViewById(R.id.lvTrips);
        trips = new ArrayList<>();
        requestIDs = new ArrayList<>();
        tripsAdapter = new ArrayAdapter<>(TravelerViewTrips.this, android.R.layout.simple_list_item_1, trips);

        getTripInfo();
    }

    public void getTripInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Traveler").child("trips_current").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    requestInfo = dataSnapshot.getValue(TravelerRequestModel.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelerViewTrips.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void fillRequestList(){
        if (user != null) {
            final String uid = user.getUid();
            dataRef.child("users").child(uid).child("Traveler").child("trips_current").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    for (final DataSnapshot child: dataSnapshot.getChildren()) {
                        final String place = child.child("place").getValue().toString();
                        String startDate = child.child("startDate").getValue().toString();
                        String endDate = child.child("endDate").getValue().toString();

                        trips.add(place + "\n" + startDate + " - " + endDate);

                        requestIDs.add(child.getKey());

                        lvTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                Intent intent = new Intent(TravelerViewTrips.this, TravelerViewTripInfo.class);
                                intent.putExtra("requestIDs", requestIDs.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                    lvTrips.setAdapter(tripsAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelerViewTrips.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_traveler, menu);
        return true;
    }


    public void makeNewRequest(MenuItem item) {
        Intent i = new Intent(this, TravelerNewRequest.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void viewAcceptedReqs(MenuItem item) {
        Intent i = new Intent(this, TravelerPending.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        trips.clear();
        requestIDs.clear();
        fillRequestList();
        tripsAdapter.notifyDataSetChanged();
    }
}
