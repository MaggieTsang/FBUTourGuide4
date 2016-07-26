package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    String reqIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelerviewtrip);

        tvPlace = (TextView) findViewById(R.id.tvPlaceRequest);
        tvStartDate = (TextView) findViewById(R.id.tvStartDateRequest);
        tvEndDate = (TextView) findViewById(R.id.tvEndDateRequest);
        tvGroupSize = (TextView) findViewById(R.id.tvGroupSizeRequest);
        tvLanguages = (TextView) findViewById(R.id.tvLanguagesRequest);

        reqIDs = getIntent().getSerializableExtra("requestIDs").toString();

        dataRef.child(reqIDs).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvPlace.setText(dataSnapshot.child("place").getValue().toString());
                tvStartDate.setText(dataSnapshot.child("startDate").getValue().toString());
                tvEndDate.setText(dataSnapshot.child("endDate").getValue().toString());
                tvGroupSize.setText(dataSnapshot.child("groupSize").getValue().toString());
                tvLanguages.setText(dataSnapshot.child("languages").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cancelled.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editRequest(View view) {
        Intent i = new Intent(TravelerViewTripInfo.this, NewRequest.class);
        startActivity(i);
    }
}
