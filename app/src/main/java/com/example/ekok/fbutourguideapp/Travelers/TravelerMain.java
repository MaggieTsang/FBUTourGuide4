package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by mbytsang on 7/5/16.
 */
public class TravelerMain extends AppCompatActivity{
    private final int REQUEST_CODE = 20;
    private static final String TAG = "Firebase";

    ArrayList<RequestModel> trips;
    RequestAdapter adapter;
    ListView lvTrips;
    private DatabaseReference dataRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelermain);

        lvTrips = (ListView) findViewById(R.id.lvTrips);
        trips = new ArrayList<RequestModel>();
//        tripsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, trips);
        adapter = new RequestAdapter(this, trips);
        lvTrips.setAdapter(adapter);
//        RequestModel requestModel = new RequestModel();
//        adapter.add(requestModel);
//        adapter.notifyDataSetChanged();

//        for (int i = 0; i < 25; i++) {
//            trips.add("Le Tripsss");
//        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                RequestModel request = dataSnapshot.getValue(RequestModel.class);
                trips.add(request);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                NewRequest request = dataSnapshot.getValue(NewRequest.class);
                String requestKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String requestKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                NewRequest request = dataSnapshot.getValue(NewRequest.class);
                String requestKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(), "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        dataRef.addChildEventListener(childEventListener);


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
            adapter.add(requestModel);
            adapter.notifyAll();

        }
    }
}
