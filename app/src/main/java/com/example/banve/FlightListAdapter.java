package com.example.banve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FlightListAdapter extends ArrayAdapter<Flight> {

    private Context mContext;
    private ArrayList<Flight> flights;

    public FlightListAdapter(Context context, ArrayList<Flight> list) {
        super(context, 0, list);
        mContext = context;
        flights = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_flight_search, parent, false);
        }

        Flight currentFlight = flights.get(position);

        TextView tvFlightNumber = listItem.findViewById(R.id.tvFlightNumber);
        TextView tvDeparture = listItem.findViewById(R.id.tvDeparture);
        TextView tvArrival = listItem.findViewById(R.id.tvArrival);

        tvFlightNumber.setText(currentFlight.getFlightNumber());
        tvDeparture.setText(currentFlight.getDeparture());
        tvArrival.setText(currentFlight.getArrival());

        return listItem;
    }
}

