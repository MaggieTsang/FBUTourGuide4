package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ekok.fbutourguideapp.R;

import java.util.ArrayList;

/**
 * Created by ekok on 7/18/16.
 */
public class RequestAdapter extends ArrayAdapter<RequestModel> {
    public RequestAdapter(Context context, ArrayList<RequestModel> requests) {
        super(context, 0, requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RequestModel request = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trip, parent, false);
        }

        // Lookup view for data population
        TextView tvTripName = (TextView) convertView.findViewById(R.id.tvTripName);

        // Populate the data into the template view using the data object
        tvTripName.setText(request.place.toString());

        return convertView;
    }
}
