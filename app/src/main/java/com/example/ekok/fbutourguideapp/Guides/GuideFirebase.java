package com.example.ekok.fbutourguideapp.Guides;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by mbytsang on 7/12/16.
 */
public class GuideFirebase {
    DatabaseReference dataBaseRef;
    Boolean saved = null;

    public GuideFirebase(DatabaseReference dataBaseRef) {
        this.dataBaseRef = dataBaseRef;
    }





    public Boolean saveToGuide(GuideUser guide) {
        if (guide == null) {
            saved = false;
        }
        else {
            try {
                dataBaseRef.child("Guide").setValue(guide);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }
}
