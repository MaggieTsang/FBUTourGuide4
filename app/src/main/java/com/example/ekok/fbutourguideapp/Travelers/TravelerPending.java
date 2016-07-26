package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.Guides.GuideUser;
import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TravelerPending extends AppCompatActivity {

    DatabaseReference dataRef;
    ListView lvPending;
    ArrayList<String> pending;
    ArrayAdapter<String> pendingAdapter;

    ArrayList<String> reqBucket;
    GuideUser guideInfo;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelerpending);
        dataRef = FirebaseDatabase.getInstance().getReference();

        lvPending = (ListView) findViewById(R.id.lvTravelerPending);
        pending = new ArrayList<>();
        reqBucket = new ArrayList<>();
        pendingAdapter = new ArrayAdapter<>(TravelerPending.this, android.R.layout.simple_list_item_1, pending);

        fillPendingList();
    }

    public void getGuideInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Guide").child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    guideInfo = dataSnapshot.getValue(GuideUser.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelerPending.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void fillPendingList(){
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Traveler").child("Pending").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //GuideUser guide = dataSnapshot.getValue(GuideUser.class);
                    //String guideLocation= guide.location;
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        //If guide location matches a folder

//                        String place = child.child("place").getValue().toString();
//                        String startDate = child.child("startDate").getValue().toString();
//                        String endDate = child.child("endDate").getValue().toString();
//
//                        pending.add(place + "\n" + startDate + " - " + endDate);
                        int n = 0;
                        reqBucket.add(child.getKey());
                        String guideId = reqBucket.get(n);
                        pending.add("guide id: " + guideId);
                        n++;

//                        pending.add("pending req id: " + child.getKey());

                        lvPending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                Intent intent = new Intent(TravelerPending.this, TravelerDecide.class);
                                intent.putExtra("reqGuide", reqBucket.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                    lvPending.setAdapter(pendingAdapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelerPending.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
