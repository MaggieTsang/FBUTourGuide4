package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
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
 * Created by mbytsang on 7/5/16.
 */
public class TravelerViewTrips extends AppCompatActivity{
    private final int REQUEST_CODE = 20;
    private static final String TAG = "Firebase";

    ArrayList<String> trips;
    ArrayAdapter<String> tripsAdapter;
    ListView lvTrips;

    RequestModel requestInfo;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelermain);
        dataRef = FirebaseDatabase.getInstance().getReference();

        lvTrips = (ListView) findViewById(R.id.lvTrips);
        trips = new ArrayList<>();
        tripsAdapter = new ArrayAdapter<>(TravelerViewTrips.this, android.R.layout.simple_list_item_1, trips);

        getTripInfo();
        fillRequestList();
    }

    public void getTripInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Traveler").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    requestInfo = dataSnapshot.getValue(RequestModel.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelerViewTrips.this, "Error.", Toast.LENGTH_SHORT).show();
                    // Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                }
            });
        }
    }


    public void fillRequestList(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String uid = user.getUid();
            dataRef.child("users").child(uid).child("Traveler").child("trips_current").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    //GuideUser guide = dataSnapshot.getValue(GuideUser.class);
                    //String guideLocation= guide.location;
                    for (final DataSnapshot child: dataSnapshot.getChildren()) {
                        String currentReqs = child.getValue().toString();
                        final String trip_id = child.getKey();
                        trips.add(currentReqs + trip_id);


                        lvTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                String trip = trips.get(position);
                                Toast.makeText(getApplicationContext(), "pos: " + trip, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(TravelerViewTrips.this, TravelerViewTripInfo.class);
                                intent.putExtra("trip_id", trip);
                                startActivity(intent);
                            }
                        });
                    }
                    lvTrips.setAdapter(tripsAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelerViewTrips.this, "Error.", Toast.LENGTH_SHORT).show();
                    // Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                }
            });

        }
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

    public void viewAcceptedReqs(MenuItem item) {
        Intent i = new Intent(this, TravelerPending.class);
        startActivity(i);
    }
}
