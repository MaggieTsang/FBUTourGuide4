package com.example.ekok.fbutourguideapp.Travelers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TravelerViewTripInfo extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Traveler").child("trips_current");

    TextView tvPlace;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvGroupSize;
    TextView tvLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelerviewtrip);

        tvPlace = (TextView) findViewById(R.id.tvPlaceRequest);
        tvStartDate = (TextView) findViewById(R.id.tvStartDateRequest);
        tvEndDate = (TextView) findViewById(R.id.tvEndDateRequest);
        tvGroupSize = (TextView) findViewById(R.id.tvGroupSizeRequest);
        tvLanguages = (TextView) findViewById(R.id.tvLanguagesRequest);

        final String trip_key = getIntent().getStringExtra("trip_id");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tvPlace.setText(trip_key);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void editRequest(View view) {

    }
}
