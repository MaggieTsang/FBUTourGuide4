package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/5/16.
 */
public class GuideBasic extends AppCompatActivity{

    GuideUser guideUser;
    EditText etName;
    EditText etLocation;
    EditText etBasicAdditional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidebasic);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");

        etName = (EditText) findViewById(R.id.etName);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etBasicAdditional = (EditText) findViewById(R.id.etBasicAdditional);

        String name = guideUser.legalName;
        String location = guideUser.location;
        String additional = guideUser.description;

        etName.setText(name);
        etLocation.setText(location);
        etBasicAdditional.setText(additional);
    }

    public void launchContact(View v) {
        guideUser.legalName = etName.getText().toString();
        guideUser.location = etLocation.getText().toString();
        guideUser.description = etBasicAdditional.getText().toString();

        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuideContact.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }

}
