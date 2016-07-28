package com.example.ekok.fbutourguideapp.Travelers;

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

public class TravelerPending extends AppCompatActivity {

    DatabaseReference dataRef;
    ListView lvPending;
    ArrayList<String> pending;
    ArrayAdapter<String> pendingAdapter;
    ArrayList<String> reqBucket;
    ArrayList<String> reqGuide;
    ArrayList<String> locs;

    ArrayList<String> reqID;
    ArrayList<String> travelerID;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelerpending);
        dataRef = FirebaseDatabase.getInstance().getReference();

        lvPending = (ListView) findViewById(R.id.lvTravelerPending);
        pending = new ArrayList<>();
        reqBucket = new ArrayList<>();
        locs = new ArrayList<>();
        reqGuide = new ArrayList<>();
        reqID = new ArrayList<>();
        travelerID = new ArrayList<>();
        pendingAdapter = new ArrayAdapter<>(TravelerPending.this, android.R.layout.simple_list_item_1, pending);

        fillPendingList();
    }

    public void fillPendingList(){
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Traveler").child("Pending").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    for (final DataSnapshot child: dataSnapshot.getChildren()) {

                        String place = child.child("location").getValue().toString();
                        String date = child.child("dates").getValue().toString();
                        String guide = child.child("guideName").getValue().toString();

                        pending.add(place + "\n" + date + "\n" + "Guide " + guide);

                        reqBucket.add(child.getKey());
                        reqGuide.add(child.child("guideID").getValue().toString());
                        locs.add(child.child("location").getValue().toString());
                        reqID.add(child.child("requestId").getValue().toString());


                        lvPending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                Intent intent = new Intent(TravelerPending.this, TravelerDecide.class);
                                intent.putExtra("reqGuide", reqGuide.get(position));
                                intent.putExtra("loc", locs.get(position));
                                intent.putExtra("reqBucket", reqBucket.get(position));
                                intent.putExtra("reqID", reqID.get(position));
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

    @Override
    protected void onResume() {
        super.onResume();
        pendingAdapter.notifyDataSetChanged();
    }
}
