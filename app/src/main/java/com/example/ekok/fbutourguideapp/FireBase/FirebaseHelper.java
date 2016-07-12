package com.example.ekok.fbutourguideapp.FireBase;

import com.example.ekok.fbutourguideapp.Models.TravelerModel;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by ekok on 7/12/16.
 */
public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved = null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public Boolean save(TravelerModel traveler) {
        if (traveler == null) {
            saved = false;
        }
        else {
            try {
                db.child("Travelers").setValue(traveler);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }
}
