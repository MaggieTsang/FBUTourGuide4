package com.example.ekok.fbutourguideapp.Guides;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by mbytsang on 7/25/16.
 */
public class GuideAcceptedDetails extends AppCompatActivity{


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
        setContentView(R.layout.activity_guideacceptedreqdetails);
        dataRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        tvReqPlace = (TextView) findViewById(R.id.tvReqPlace);
        tvReqDate = (TextView) findViewById(R.id.tvReqDate);
        tvReqGroupNum = (TextView) findViewById(R.id.tvReqGroupNum);
        tvReqLanguages = (TextView) findViewById(R.id.tvReqLanguages);

        reqID = getIntent().getSerializableExtra("requestIDs").toString();
        travelerID = getIntent().getSerializableExtra("requestsTravelerID").toString();

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
                Toast.makeText(GuideAcceptedDetails.this, "Cannot find request data", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
    }

}
