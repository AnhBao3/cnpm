package com.example.banve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FlightAdapter extends ArrayAdapter<Flight> {

    private ArrayList<Flight> flights;
    private Context context;

    public FlightAdapter(Context context, ArrayList<Flight> flights) {
        super(context, 0, flights);
        this.flights = flights;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_flight, parent, false);
        }
        Flight currentFlight = flights.get(position);
        TextView flightNumber = listItemView.findViewById(R.id.text_flight_number);
        flightNumber.setText(currentFlight.getFlightNumber());
        TextView departureArrival = listItemView.findViewById(R.id.text_departure_arrival);
        departureArrival.setText(currentFlight.getDeparture() + " - " + currentFlight.getArrival());
        return listItemView;
    }
}
