package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by mbytsang on 7/25/16.
 */
public class GuidePending extends AppCompatActivity{

    DatabaseReference dataRef;
    FirebaseUser user;

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

        requestIDs = new ArrayList<>();
        requestsTravelerID = new ArrayList<>();

        lvPending = (ListView) findViewById(R.id.lvGuidePending);
        pending = new ArrayList<>();
        pendingAdapter = new ArrayAdapter<>(GuidePending.this, android.R.layout.simple_list_item_1, pending);

        lvPending.setAdapter(pendingAdapter);
        pending.add("Add pending reqs 1");
        pending.add("Add pending reqs 2");

        fillPendingList();
    }

    public void fillPendingList(){
        dataRef.child("users").child(user.getUid()).child("Guide").child("Accepted").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String guideLocation = dataRef.child("users").child(user.getUid()).child("Guide").child("Profile").child("location").toString();
                    for (DataSnapshot places : dataSnapshot.getChildren()) {
                        //If guide location matches a folder
                        if (places.getKey().equalsIgnoreCase(guideLocation)) {
                            for (DataSnapshot travelers : places.getChildren()) {
                                //Show all in accepted branch. Gets req ID, show name and date
                                String travelerID = travelers.toString();
                                for (DataSnapshot reqs : travelers.getChildren()) {
                                    String reqID = reqs.toString();


                                    String name = dataRef.child("users").child(travelerID).child("displayName").toString();
                                    String start = dataRef.child("users").child(travelerID).child("Traveler").child("trips_current").child(reqID).child("startDate").toString();
                                    String end = dataRef.child("users").child(travelerID).child("Traveler").child("trips_current").child(reqID).child("endDate").toString();
                                    String dates = start + " - " + end;
                                    pending.add(name + ": " + dates);

                                    requestIDs.add(reqID);
                                    requestsTravelerID.add(travelerID);

                                }


                            }

                    }
            }
                lvPending.setAdapter(pendingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuidePending.this, "Error.", Toast.LENGTH_SHORT).show();
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
                startActivity(intent);
            }
        });
    }
}
