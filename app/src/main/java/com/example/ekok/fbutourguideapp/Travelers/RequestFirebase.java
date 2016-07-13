package com.example.ekok.fbutourguideapp.Travelers;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by ekok on 7/12/16.
 */
public class RequestFirebase {

    DatabaseReference db;
    Boolean saved = null;

    public RequestFirebase(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public Boolean save(RequestModel traveler) {
        if (traveler == null) {
            saved = false;
        }
        else {
            try {
                db.child("Traveler").setValue(traveler);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
}
