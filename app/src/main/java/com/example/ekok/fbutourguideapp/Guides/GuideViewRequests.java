package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.Login.UserType;
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
 * Created by mbytsang on 7/5/16.
 */
public class GuideViewRequests extends AppCompatActivity{
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    DatabaseReference dataRef;

    ArrayList<String> requests;
    ArrayAdapter<String> requestsAdapter;
    ListView lvRequests;
    GuideUser guideInfo;

    ArrayList<String> requestBucket;

    //ArrayList<String> requestIDs;
    ArrayList<String> requestsTravelerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiderequests);
        dataRef = FirebaseDatabase.getInstance().getReference();

        lvRequests = (ListView) findViewById(R.id.lvRequests);
        requests = new ArrayList<>();
        requestBucket = new ArrayList<>();
        requestsTravelerID = new ArrayList<>();

        requestsAdapter = new ArrayAdapter<>(GuideViewRequests.this, android.R.layout.simple_list_item_1, requests);

        getGuideInfo();
        fillRequestList();
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
                    Toast.makeText(GuideViewRequests.this, "Error.", Toast.LENGTH_SHORT).show();
                    // Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                }
            });
        }
    }


    public void fillRequestList(){
        dataRef.child("requests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //GuideUser guide = dataSnapshot.getValue(GuideUser.class);
                //String guideLocation= guide.location;
                for (DataSnapshot places: dataSnapshot.getChildren()) {
                    //If guide location matches a folder
                    //String guideLocation = guideInfo.location;
                    if (places.getKey().equalsIgnoreCase(guideInfo.location)){
                        for (DataSnapshot availRequests: places.getChildren()){
                            String name = availRequests.child("displayName").getValue().toString();
                            String dates = availRequests.child("dates").getValue().toString();
                            requests.add(name + ": " + dates);

                            requestBucket.add(availRequests.getKey());

                            //requestIDs.add(availRequests.child("requestId").getValue().toString());
                            requestsTravelerID.add(availRequests.child("traveler_uid").getValue().toString());
                        }
                    }
                }
                lvRequests.setAdapter(requestsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideViewRequests.this, "Error.", Toast.LENGTH_SHORT).show();
                // Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        lvRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getApplicationContext(), "Open request info at " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GuideViewRequests.this, GuideRequestDetail.class);
                intent.putExtra("location", guideInfo.location);
                intent.putExtra("requestBucket",requestBucket.get(position));
                intent.putExtra("travelerID", requestsTravelerID.get(position));
                startActivity(intent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    public void editProfile(MenuItem item) {
        Intent i = new Intent(this, GuideViewProfile.class);
        startActivity(i);
    }

    /*
    public void goToCalender(MenuItem item) {
        Intent i = new Intent(this, GuideCalender.class);
        startActivity(i);
    }
    */

    public void goToHome(MenuItem item) {
        Intent i = new Intent(this, UserType.class);
        startActivity(i);
    }

    public void goToPending(MenuItem item) {
        Intent i = new Intent(this, GuidePending.class);
        i.putExtra("location", guideInfo.location);
        startActivity(i);
    }
}
