package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/7/16.
 */
public class GuideContact extends AppCompatActivity {

    GuideUser guideUser;
    EditText etPhonePrimary;
    EditText etPhoneSecondary;
    EditText etEmail;
    EditText etContactAdditional;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidecontactinfo);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");

        etPhonePrimary = (EditText) findViewById(R.id.etPhonePrimary);
        etPhoneSecondary = (EditText) findViewById(R.id.etPhoneSecondary);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContactAdditional = (EditText) findViewById(R.id.etContactAdditional);

        String phone1 = guideUser.phonePrimary;
        String phone2 = guideUser.phoneSecondary;
        String email = guideUser.email;
        String additional = guideUser.contactAdditional;

        etPhonePrimary.setText(phone1);
        etPhoneSecondary.setText(phone2);
        etEmail.setText(email);
        etContactAdditional.setText(additional);
    }

    public void launchPayment(View v) {
        guideUser.phonePrimary = etPhonePrimary.getText().toString();
        guideUser.phoneSecondary = etPhoneSecondary.getText().toString();
        guideUser.email = etEmail.getText().toString();
        guideUser.contactAdditional = etContactAdditional.getText().toString();

        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuidePayment.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }
}
