package com.example.ekok.fbutourguideapp.Guides;

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

/**
 * Created by mbytsang on 7/22/16.
 */
public class GuideRequestDetail extends AppCompatActivity{

    DatabaseReference dataRef;
    FirebaseUser user;
    String reqID;
    String travelerID;

    TextView tvReqPlace;
    TextView tvReqDate;
    TextView tvReqGroupNum;
    TextView tvReqLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiderequestdetails);
        dataRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        tvReqPlace = (TextView) findViewById(R.id.tvReqPlace);
        tvReqDate = (TextView) findViewById(R.id.tvReqDate);
        tvReqGroupNum = (TextView) findViewById(R.id.tvReqGroupNum);
        tvReqLanguages = (TextView) findViewById(R.id.tvReqLanguages);

        reqID = getIntent().getSerializableExtra("requestIDs").toString();
        travelerID = getIntent().getSerializableExtra("requestsTravelerID").toString();
        String travelerID = getIntent().getSerializableExtra("requestsTravelerID").toString();

        dataRef.child("users").child(travelerID).child("Traveler").child("trips_current").child(reqID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String start = dataSnapshot.child("startDate").getValue().toString();
                String end = dataSnapshot.child("endDate").getValue().toString();
                String dates = start + " - " + end;

                tvReqPlace.setText(dataSnapshot.child("place").getValue().toString());
                tvReqDate.setText(dates);
                tvReqGroupNum.setText(dataSnapshot.child("groupSize").getValue().toString());
                tvReqLanguages.setText(dataSnapshot.child("languages").getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideRequestDetail.this, "Cannot find request data", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
    }

    //Adds the declined request id to the database
    public void declineRequest(View view) {
        Toast.makeText(GuideRequestDetail.this, "Request declined.", Toast.LENGTH_SHORT).show();
        dataRef.child("users").child(user.getUid()).child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String location = dataSnapshot.child("Profile").child("location").getValue().toString();
                dataRef.child("users").child(user.getUid()).child("Guide").child("Declined").child(location).child(reqID).setValue("Declined " + reqID);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideRequestDetail.this, "Error: Did not decline request.", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
        finish();
    }

    //Adds the accepted request id to the database, puts in
    public void acceptRequest(View view) {
        Toast.makeText(GuideRequestDetail.this, "Request accepted!", Toast.LENGTH_SHORT).show();
        dataRef.child("users").child(user.getUid()).child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String location = dataSnapshot.child("Profile").child("location").getValue().toString();
                dataRef.child("users").child(user.getUid()).child("Guide").child("Accepted").child(location).child(travelerID).setValue(reqID);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideRequestDetail.this, "Error: Did not accept request.", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });


        Intent i = new Intent(this, GuidePending.class);
        startActivity(i);
    }
}



