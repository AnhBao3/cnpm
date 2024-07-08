package com.example.banve;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingDetailActivity extends AppCompatActivity {

    private TextView tvFlightNumber, tvDeparture, tvArrival, tvDate, tvPaymentStatus, tvUsername;
    private Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        // Initialize views
        tvFlightNumber = findViewById(R.id.tvFlightNumber);
        tvDeparture = findViewById(R.id.tvDeparture);
        tvArrival = findViewById(R.id.tvArrival);
        tvDate = findViewById(R.id.tvDate);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);
        tvUsername = findViewById(R.id.tvUsername); // Uncomment this line

        // Get the booking object from intent
        booking = getIntent().getParcelableExtra("booking");

        // Display booking details
        if (booking != null) {
            Flight flight = booking.getFlight();
            tvFlightNumber.setText("Mã chuyến bay: " + flight.getFlightNumber());
            tvDeparture.setText("Điểm khởi hành: " + flight.getDeparture());
            tvArrival.setText("Điểm đến: " + flight.getArrival());
            tvDate.setText("Ngày: " + flight.getDate());
            tvUsername.setText("Người dùng: " + booking.getUsername());
            tvPaymentStatus.setText("Trạng thái thanh toán: " + booking.getPaymentStatus());
        }
    }
}
