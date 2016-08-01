package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
 * Created by mbytsang on 7/25/16.
 */
public class GuidePending extends AppCompatActivity{

    DatabaseReference dataRef;
    FirebaseUser user;
    String location;

    ArrayList<String> requestIDs;
    ArrayList<String> requestsTravelerID;


    ListView lvPending;
    ArrayList<String> pending;
    ArrayAdapter<String> pendingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidepending);
        dataRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        location = getIntent().getSerializableExtra("location").toString().toLowerCase();

        requestIDs = new ArrayList<>();
        requestsTravelerID = new ArrayList<>();

        lvPending = (ListView) findViewById(R.id.lvGuidePending);
        pending = new ArrayList<>();
        pendingAdapter = new ArrayAdapter<>(GuidePending.this, android.R.layout.simple_list_item_1, pending);

        lvPending.setAdapter(pendingAdapter);

        showAcceptedList();
        fillPendingList();
    }

    private void showAcceptedList() {
        dataRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot acceptedReqs = dataSnapshot.child(user.getUid()).child("Guide").child("Accepted").child(location);
                for (DataSnapshot requestId : acceptedReqs.getChildren()) {
                    String reqId = requestId.getKey();
                    String travelerId = requestId.getValue().toString();
                    DataSnapshot reqInfo = dataSnapshot.child(travelerId);
                    String name = reqInfo.child("displayName").getValue().toString();
                    String dates = reqInfo.child("Traveler").child("Accepted").child(reqId).child("dates").getValue().toString();
                    pending.add("Accepted : " + name + ", " + dates);
                    requestIDs.add(reqId);
                    requestsTravelerID.add(travelerId);
                }
                lvPending.setAdapter(pendingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuidePending.this, "Get Pending Error.", Toast.LENGTH_SHORT).show();
                // Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
    }


    public void fillPendingList(){
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot reqBuckets = dataSnapshot.child("users").child(user.getUid()).child("Guide").child("Pending").child(location);
                for (DataSnapshot reqBucketIds : reqBuckets.getChildren()) {
                    String reqIdBucket = reqBucketIds.getKey();
                    DataSnapshot reqInfo = dataSnapshot.child("requests").child(location).child(reqIdBucket);
                    String name = reqInfo.child("displayName").getValue().toString();
                    String dates = reqInfo.child("dates").getValue().toString();
                    pending.add("Pending : " + name + ", " + dates);

                    ImageView noReq = (ImageView) findViewById(R.id.ivNoReq);

                    if (pending.size() == 0) {
                        noReq.setVisibility(View.VISIBLE);
                    }
                    else {
                        noReq.setVisibility(View.INVISIBLE);
                    }

                    requestIDs.add(reqBucketIds.getKey());
                    requestsTravelerID.add(reqInfo.child("traveler_uid").getValue().toString());
                }
                lvPending.setAdapter(pendingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuidePending.this, "Get Pending Error.", Toast.LENGTH_SHORT).show();
                // Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        lvPending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getApplicationContext(), "Open request info at " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GuidePending.this, GuideAcceptedDetails.class);
                intent.putExtra("requestIDs", requestIDs.get(position));
                intent.putExtra("requestsTravelerID",requestsTravelerID.get(position));
                intent.putExtra("pending", pending.get(position));
                startActivity(intent);
            }
        });

    }

}
