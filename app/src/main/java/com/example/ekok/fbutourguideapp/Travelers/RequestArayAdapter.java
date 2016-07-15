package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ekok.fbutourguideapp.R;

import java.util.List;

/**
 * Created by ekok on 7/15/16.
 */
public class RequestArayAdapter extends ArrayAdapter<RequestModel> {
    private static class ViewHolder {
        TextView place;
        TextView dates;
    }

    public RequestArayAdapter(Context context, List<RequestModel> movies) {
        super(context, R.layout.item_trip, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int orientation = getContext().getResources().getConfiguration().orientation;

        // get data item for position
        RequestModel request = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        // check existing view being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_trip, parent, false);
            viewHolder.place = (TextView) convertView.findViewById(R.id.tvTripName);
            viewHolder.dates = (TextView) convertView.findViewById(R.id.tvDates);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TextView tvPlace = (TextView) convertView.findViewById(R.id.tvTripName);
        TextView tvDates = (TextView) convertView.findViewById(R.id.tvDates);

        //populate data
        viewHolder.place.setText(request.getPlace());
        viewHolder.dates.setText(request.getStartDate() + " - " + request.getEndDate());

        //return the view
        return convertView;
    }

}
