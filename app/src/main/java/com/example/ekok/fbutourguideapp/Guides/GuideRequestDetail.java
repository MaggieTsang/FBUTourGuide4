package com.example.ekok.fbutourguideapp.Guides;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
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

    TextView tvReqPlace;
    TextView tvReqDate;
    TextView tvReqGroupNum;
    TextView tvReqLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiderequestdetails);
        dataRef = FirebaseDatabase.getInstance().getReference();

        tvReqPlace = (TextView) findViewById(R.id.tvReqPlace);
        tvReqDate = (TextView) findViewById(R.id.tvReqDate);
        tvReqGroupNum = (TextView) findViewById(R.id.tvReqGroupNum);
        tvReqLanguages = (TextView) findViewById(R.id.tvReqLanguages);

        String reqID = getIntent().getSerializableExtra("requestIDs").toString();
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

    public void declineRequest(View view) {
        Toast.makeText(GuideRequestDetail.this, "Request declined.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void acceptRequest(View view) {
        Toast.makeText(GuideRequestDetail.this, "Request accepted!", Toast.LENGTH_SHORT).show();
        finish();
    }
}



