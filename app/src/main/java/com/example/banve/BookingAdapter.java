package com.example.banve;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingAdapter extends ArrayAdapter<Booking> {

    private Context mContext;
    private int mResource;

    public BookingAdapter(Context context, int resource, ArrayList<Booking> bookings) {
        super(context, resource, bookings);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Booking booking = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }
        TextView tvFlightNumber = convertView.findViewById(R.id.tvFlightNumber);
        TextView tvDeparture = convertView.findViewById(R.id.tvDeparture);
        TextView tvArrival = convertView.findViewById(R.id.tvArrival);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        if (booking != null) {
            tvFlightNumber.setText("Mã chuyến bay: " + booking.getFlight().getFlightNumber());
            tvDeparture.setText("Điểm khởi hành: " + booking.getFlight().getDeparture());
            tvArrival.setText("Điểm đến: " + booking.getFlight().getArrival());
            tvDate.setText("Ngày: " + booking.getFlight().getDate());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookingDetailActivity.class);
                intent.putExtra("booking", booking);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}

