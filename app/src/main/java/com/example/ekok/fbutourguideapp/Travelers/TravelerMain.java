package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    TextView mTextFieldCondition;
    Button mButtonSunny;
    Button mButtonFoggy;
    Firebase mRef;

//    private Firebase mRef;

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

//        Firebase.setAndroidContext(this);
//        Firebase myFirebaseRef = new Firebase("https://fbutourguide.firebaseio.com/");
//        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
//        myFirebaseRef.child("message").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
//            }
//            @Override public void onCancelled(FirebaseError error) { }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextFieldCondition = (TextView) findViewById(R.id.textViewCondition);
        mButtonSunny = (Button) findViewById(R.id.buttonSunny);
        mButtonFoggy = (Button) findViewById(R.id.buttonFoggy);
        mRef = new Firebase("https://fbutourguide.firebaseio.com/condition");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mTextFieldCondition.setText(text);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mButtonFoggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.setValue("Foggy");
            }
        });
        mButtonSunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.setValue("Sunny");
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
        Intent i = new Intent(this, TravelerNewTrip.class);
        startActivity(i);
    }
}
