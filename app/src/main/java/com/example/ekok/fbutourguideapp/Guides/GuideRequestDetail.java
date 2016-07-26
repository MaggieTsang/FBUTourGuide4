package com.example.ekok.fbutourguideapp.Guides;

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

    private static final String TAG = "TAG";
    DatabaseReference dataRef;
    FirebaseUser user;
    String requestBucket;
    String requestID;
    String location;
    String travelerID;
    String dates;


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

        requestBucket = getIntent().getSerializableExtra("requestBucket").toString();
        travelerID = getIntent().getSerializableExtra("travelerID").toString();
        location = getIntent().getSerializableExtra("location").toString().toLowerCase();

        dataRef.child("requests").child(location).child(requestBucket).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvReqPlace.setText(location);

                //**********LOCATION CAPITALIA-sed-TION***********
                tvReqDate.setText(dataSnapshot.child("dates").getValue().toString());
                dates = dataSnapshot.child("dates").getValue().toString();
                tvReqGroupNum.setText(dataSnapshot.child("groupSize").getValue().toString());
                tvReqLanguages.setText(dataSnapshot.child("languages").getValue().toString());
                requestID = dataSnapshot.child("requestId").getValue().toString();
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
                dataRef.child("users").child(user.getUid()).child("Guide").child("Declined").child(location).child(requestBucket).setValue(requestID);
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
        Toast.makeText(GuideRequestDetail.this, "Request accepted - Waiting for Traveler", Toast.LENGTH_SHORT).show();

        dataRef.child("users").child(user.getUid()).child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //put in Guide
                dataRef.child("users").child(user.getUid()).child("Guide").child("Pending").child(location).child(requestBucket).setValue(requestID);
                //notify traveler, need to give traveler guideId for the path to be built
                dataRef.child("users").child(travelerID).child("Traveler").child("Pending").child(requestBucket).child("requestId").setValue(requestID);
                dataRef.child("users").child(travelerID).child("Traveler").child("Pending").child(requestBucket).child("dates").setValue(dates);
                dataRef.child("users").child(travelerID).child("Traveler").child("Pending").child(requestBucket).child("location").setValue(location);
                dataRef.child("users").child(travelerID).child("Traveler").child("Pending").child(requestBucket).child("guideName").setValue(user.getDisplayName());
                dataRef.child("users").child(travelerID).child("Traveler").child("Pending").child(requestBucket).child("guideID").setValue(user.getUid());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideRequestDetail.this, "Error: Did not accept request.", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });


        finish();
    }
}



