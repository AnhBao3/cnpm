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

        // Lấy username từ Intent
        loggedInUsername = getIntent().getStringExtra("username");

        // Load danh sách vé đã đặt
        loadBookingHistory();

        // Set click listener for ListView items
        lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the booking at the clicked position
                Booking booking = bookings.get(position);

                // Navigate to BookingDetailActivity
                Intent intent = new Intent(BookingListActivity.this, BookingDetailActivity.class);
                intent.putExtra("booking", booking);
                startActivity(intent);
            }
        });
    }

    private void loadBookingHistory() {
        // Gọi phương thức trong DBHelper để lấy danh sách các vé đã đặt
        bookings = dbHelper.getAllBookings(loggedInUsername);

        // Hiển thị danh sách vé đã đặt lên ListView bằng cách sử dụng BookingAdapter
        adapter = new BookingAdapter(this, R.layout.list_item_booking, bookings);
        lvBookings.setAdapter(adapter);
    }
}

