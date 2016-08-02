package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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

public class TravelerPending extends AppCompatActivity {
    private final int REQUEST_CODE = 20;

    DatabaseReference dataRef;
    ListView lvPending;
    ArrayList<String> pending;
    ArrayAdapter<String> pendingAdapter;
    ArrayList<String> reqBucket;
    ArrayList<String> reqGuide;
    ArrayList<String> locs;
    ArrayList<String> dates;
    ArrayList<String> groupSize;
    ArrayList<String> lang;

    ArrayList<String> reqID;
    ArrayList<String> travelerID;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelerpending);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.drawable.action_bar_logo);
        }

        dataRef = FirebaseDatabase.getInstance().getReference();

        lvPending = (ListView) findViewById(R.id.lvTravelerPending);
        pending = new ArrayList<>();
        reqBucket = new ArrayList<>();
        locs = new ArrayList<>();
        reqGuide = new ArrayList<>();
        reqID = new ArrayList<>();
        travelerID = new ArrayList<>();
        dates = new ArrayList<>();
        groupSize = new ArrayList<>();
        lang = new ArrayList<>();
        pendingAdapter = new ArrayAdapter<>(TravelerPending.this, android.R.layout.simple_list_item_1, pending);
    }

    private void showAcceptedList() {
        dataRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot acceptedReqs = dataSnapshot.child(user.getUid()).child("Traveler").child("Accepted");
                for (DataSnapshot requestId : acceptedReqs.getChildren()) {
                    String req_Id = requestId.getKey();
                    String guide_Id = requestId.child("guideID").getValue().toString();
                    String place = requestId.child("location").getValue().toString();
                    String date = requestId.child("dates").getValue().toString();
                    pending.add("\n" + "Accepted : " + "\n" + place + ", " + date + "\n");

                    ImageView noReq = (ImageView) findViewById(R.id.ivNoReq);

                    if (pending.size() == 0) {
                        noReq.setVisibility(View.VISIBLE);
                    }
                    else {
                        noReq.setVisibility(View.INVISIBLE);
                    }

                    reqID.add(req_Id);
                    locs.add(place);
                    reqBucket.add(null);
                    reqGuide.add(guide_Id);
                    dates.add(date);

                    addListviewListener();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TravelerPending.this, "Get Pending Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fillPendingList() {
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Traveler").child("Pending").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    for (final DataSnapshot child: dataSnapshot.getChildren()) {

                        String place = child.child("location").getValue().toString();
                        String date = child.child("dates").getValue().toString();
                        String guide = child.child("guideName").getValue().toString();

                        pending.add("\n" + "Pending: " + guide + "\n" + place + ", " + date + "\n");

                        ImageView noReq = (ImageView) findViewById(R.id.ivNoReq);

                        if (pending.size() == 0) {
                            noReq.setVisibility(View.VISIBLE);
                        }
                        else {
                            noReq.setVisibility(View.INVISIBLE);
                        }

                        reqBucket.add(child.getKey());
                        reqGuide.add(child.child("guideID").getValue().toString());
                        locs.add(child.child("location").getValue().toString());
                        reqID.add(child.child("requestId").getValue().toString());
                        dates.add(child.child("dates").getValue().toString());
                        groupSize.add(child.child("groupSize").getValue().toString());
                        lang.add(child.child("languages").getValue().toString());

                        addListviewListener();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(TravelerPending.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void addListviewListener() {
        lvPending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (pending.get(position).toString().charAt(0) == 'A'
                        && pending.get(position).toString().charAt(1) == 'c'
                        && pending.get(position).toString().charAt(2) == 'c'
                        && pending.get(position).toString().charAt(3) == 'e'
                        && pending.get(position).toString().charAt(4) == 'p'
                        && pending.get(position).toString().charAt(5) == 't') {
                    Intent intent = new Intent(TravelerPending.this, TravelerViewGuideProfile.class);
                    intent.putExtra("reqGuide", reqGuide.get(position));
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(TravelerPending.this, TravelerDecide.class);
                    intent.putExtra("reqGuide", reqGuide.get(position));
                    intent.putExtra("loc", locs.get(position));
                    intent.putExtra("groupSize", groupSize.get(position));
                    intent.putExtra("languages", lang.get(position));
                    if (reqBucket.get(position) != null) {
                        intent.putExtra("reqBucket", reqBucket.get(position));
                    }
                    intent.putExtra("reqID", reqID.get(position));
                    intent.putExtra("dates", dates.get(position));
                    startActivity(intent);
                }
            }
        });
        lvPending.setAdapter(pendingAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pending.clear();
        reqBucket.clear();
        reqGuide.clear();
        locs.clear();
        reqID.clear();
        travelerID.clear();
        showAcceptedList();
        fillPendingList();
        pendingAdapter.notifyDataSetChanged();
    }
}
