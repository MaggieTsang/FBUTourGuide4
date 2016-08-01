package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guidepaymentinfo);
        dataRef = FirebaseDatabase.getInstance().getReference();

        etPaymentMethod = (EditText) findViewById(R.id.etPaymentMethod);
        etHourlyPay = (EditText) findViewById(R.id.etHourlyPay);
        etPackageDeals = (EditText) findViewById(R.id.etPackageDeals);
        etCurrencyType = (EditText) findViewById(R.id.etCurrencyType);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Guide").child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user value
                    GuideUser user = dataSnapshot.getValue(GuideUser.class);
                    if (user != null) {
                        etPaymentMethod.setText(user.method);
                        etHourlyPay.setText(user.timelyPay);
                        etPackageDeals.setText(user.packageDeals);
                        etCurrencyType.setText(user.currency);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(GuidePayment.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                    //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                }
            });
        }
    }


    public void launchViewProfile(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("method").setValue(etPaymentMethod.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("timelyPay").setValue(etHourlyPay.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("packageDeals").setValue(etPackageDeals.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("currency").setValue(etCurrencyType.getText().toString());

            if (etPaymentMethod.getText().toString().isEmpty()
                    || etHourlyPay.getText().toString().isEmpty()
                    || etCurrencyType.getText().toString().isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Please fill out required fields.", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(this, GuideViewProfile.class);
                startActivity(i);
                finish();
            }
        }
    }

    public void exit(View view) {
        Intent i = new Intent(GuidePayment.this, GuideViewProfile.class);
        startActivity(i);
        finish();
    }

    public void back(View view) {
        Intent i = new Intent(GuidePayment.this, GuideContact.class);
        startActivity(i);
        finish();
    }
}