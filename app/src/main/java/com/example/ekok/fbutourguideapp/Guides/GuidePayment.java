package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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
 * Created by mbytsang on 7/7/16.
 */
public class GuidePayment extends AppCompatActivity {

    private DatabaseReference dataRef;

    EditText etPaymentMethod;
    EditText etHourlyPay;
    EditText etPackageDeals;
    EditText etCurrencyType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidepaymentinfo);
        dataRef = FirebaseDatabase.getInstance().getReference();

        etPaymentMethod = (EditText) findViewById(R.id.etPaymentMethod);
        etHourlyPay = (EditText) findViewById(R.id.etHourlyPay);
        etPackageDeals = (EditText) findViewById(R.id.etPackageDeals);
        etCurrencyType = (EditText) findViewById(R.id.etCurrencyType);

        dataRef.child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                GuideUser user = dataSnapshot.getValue(GuideUser.class);
                etPaymentMethod.setText(user.method);
                etHourlyPay.setText(user.timelyPay);
                etPackageDeals.setText(user.packageDeals);
                etCurrencyType.setText(user.currency);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuidePayment.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

    }

    public void launchViewProfile(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // The user's ID, unique to the Firebase project.
            String uid = user.getUid();

            dataRef.child("users").child(uid).child("Guide").child("method").setValue(etPaymentMethod.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("timelyPay").setValue(etHourlyPay.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("packageDeals").setValue(etPackageDeals.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("currency").setValue(etCurrencyType.getText().toString());
            Intent i = new Intent(this, GuideViewProfile.class);
            startActivity(i);
        }
    }
}