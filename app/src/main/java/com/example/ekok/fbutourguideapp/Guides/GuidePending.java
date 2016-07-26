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
    String location;

    ListView lvPending;
    ArrayList<String> pending;
    ArrayAdapter<String> pendingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidepending);
        dataRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        location = getIntent().getSerializableExtra("location").toString();

        lvPending = (ListView) findViewById(R.id.lvGuidePending);
        pending = new ArrayList<>();
        pendingAdapter = new ArrayAdapter<>(GuidePending.this, android.R.layout.simple_list_item_1, pending);

        lvPending.setAdapter(pendingAdapter);
        pending.add("Add pending reqs 1");

        fillPendingList();
    }

    public void fillPendingList(){
        dataRef.child("users").child(user.getUid()).child("Guide").child("Accepted").child(location).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot reqBucketIds : dataSnapshot.getChildren()) {
                        //String reqName  = dataRef.child("requests").child(location).child(reqBucketIds.getKey()).child("displayName").getKey();
                        //String reqDates = dataRef.child("requests").child(location).child(reqBucketIds.getKey()).child("dates").toString();
                        //pending.add(reqName + " : " + reqDates);
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
                //intent.putExtra("requestIDs", requestIDs.get(position));
                //intent.putExtra("requestsTravelerID",requestsTravelerID.get(position));
                startActivity(intent);
            }
        });
    }
}
