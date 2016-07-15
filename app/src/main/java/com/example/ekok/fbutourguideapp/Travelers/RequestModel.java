package com.example.ekok.fbutourguideapp.Travelers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ekok on 7/11/16.
 */
public class RequestModel {

    public String place;
    String startDate;
    String endDate;
    int groupSize;
    String languages;

    public RequestModel(JSONObject jsonObject) throws JSONException {
        this.place = jsonObject.getString("place");
        this.startDate = jsonObject.getString("startDate");
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public static ArrayList<RequestModel> fromJSONArray(JSONArray array) {
        ArrayList<RequestModel> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new RequestModel(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
