package com.example.ekok.fbutourguideapp.Travelers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelerpending);
        dataRef = FirebaseDatabase.getInstance().getReference();

        lvPending = (ListView) findViewById(R.id.lvTravelerPending);
        pending = new ArrayList<>();
        pendingAdapter = new ArrayAdapter<>(TravelerPending.this, android.R.layout.simple_list_item_1, pending);

        lvPending.setAdapter(pendingAdapter);
        pending.add("Add accepted reqs by guides 1");
        pending.add("Add accepted reqs by guides 2");

        //fillPendingList();
    }

    public void fillPendingList(){
        dataRef.child("requests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //GuideUser guide = dataSnapshot.getValue(GuideUser.class);
                //String guideLocation= guide.location;
                for (DataSnapshot places: dataSnapshot.getChildren()) {
                    //If guide location matches a folder

                    /*
                    String guideLocation = guideInfo.location;
                    if (places.getKey().equalsIgnoreCase(guideLocation)){
                        for (DataSnapshot availRequests: places.getChildren()){
                            String name = availRequests.child("displayName").getValue().toString();
                            String dates = availRequests.child("dates").getValue().toString();
                            requests.add(name + ": " + dates);
                            requestIDs.add(availRequests.child("requestId").getValue().toString());
                            requestsTravelerID.add(availRequests.child("traveler_uid").getValue().toString());
                        }
                    }
                    */



                }
                lvPending.setAdapter(pendingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TravelerPending.this, "Error.", Toast.LENGTH_SHORT).show();
                // Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        lvPending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getApplicationContext(), "Open request info at " + position, Toast.LENGTH_SHORT).show();

                /*
                Intent intent = new Intent(GuideViewRequests.this, GuideRequestDetail.class);
                intent.putExtra("requestIDs", requestIDs.get(position));
                intent.putExtra("requestsTravelerID",requestsTravelerID.get(position));
                startActivity(intent);
                */
            }
        });
    }
}
