package com.example.banve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookingListActivity extends AppCompatActivity {

    private ListView lvBookings;
    private ArrayList<Booking> bookings;
    private BookingAdapter adapter;
    private DBHelper dbHelper;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        lvBookings = findViewById(R.id.lvBookings);
        bookings = new ArrayList<>();
        dbHelper = DBHelper.getInstance(this);

        loggedInUsername = getIntent().getStringExtra("username");

        loadBookingHistory();
        lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Booking booking = bookings.get(position);
                Intent intent = new Intent(BookingListActivity.this, BookingDetailActivity.class);
                intent.putExtra("booking", booking);
                startActivity(intent);
            }
        });
    }

    private void loadBookingHistory() {
        bookings = dbHelper.getAllBookings(loggedInUsername);
        adapter = new BookingAdapter(this, R.layout.list_item_booking, bookings);
        lvBookings.setAdapter(adapter);
    }
}

